using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using PrismCodeGenerator;
using Utilities;
using PrismCodeGenerator.Models;

namespace PrismRunner
{
    public class PrismRunner
    {
        private readonly string prismBatPath;
        private readonly string prismWorkingDir;
        
        public PrismRunner()
        {
            string baseDir = AppDomain.CurrentDomain.BaseDirectory;
            string solutionDir = Path.GetFullPath(Path.Combine(baseDir, "..", "..", ".."));
            prismBatPath = Path.Combine(solutionDir, "PrismRunner", "prism-games-3.2.1", "bin", "prism.bat");
            prismWorkingDir = Path.GetDirectoryName(prismBatPath) ?? "";

            if (!File.Exists(prismBatPath))
            {
                throw new FileNotFoundException("Файл prism.bat не найден: " + prismBatPath);
            }
        }
        
        public void RunSingleModel(string modelPath, string propsPath)
        {
            ConsolePrismOutput(modelPath, propsPath);
        }
        
        public async Task<bool> RunModelWithVaryingBudgets(string modelPath, string propsPath, IEnumerable<Node> nodes)
        {
            var validResults = new List<PrismOutputResult>();
            int? minFailedAttackerBudget = null;
            var locker = new object();
            
            var attackSums = AttackerCostSummarizer.AllSubsetSums(nodes);
            var defenderSums = DefenderCostSummarizer.AllSubsetSums(nodes);
            var maxDefenderBudget = DefenderCostSummarizer.AllSubsetSums(nodes).Last();
            var maxAttackerBudget = attackSums.Last();
            
            int maxConcurrency = Environment.ProcessorCount;
            var semaphore = new SemaphoreSlim(maxConcurrency);
            var tasks = new List<Task>();
            
            var cts = new CancellationTokenSource();
            var token = cts.Token;

            var resultsLock = new object();
            bool encounteredError = false;

            
            
            foreach (int attackerBudget in attackSums)
            {
                await semaphore.WaitAsync(token);

                tasks.Add(Task.Run(() =>
                {
                    try
                    {
                        if (token.IsCancellationRequested) return;

                        bool foundValid = false;

                        foreach (var defenderSum in defenderSums)
                        {
                            if (token.IsCancellationRequested) return;

                            // запускаешь процесс с attackerBudget, defenderSum
                            string output = "", errors = "";
                            var startInfo = CreatePrismProcessWithConstants(modelPath, propsPath, defenderSum, attackerBudget);
                            RunPrismProcess(startInfo, ref output, ref errors);

                            int result;
                            try
                            {
                                result = PrismOutputParser.ResultReturner(output);
                            }
                            catch (NoAppropriateResult)
                            {
                                continue;
                            }

                            if (result == 0)
                            {
                                foundValid = true;
                                lock (locker)
                                {
                                    validResults.Add(new PrismOutputResult(attackerBudget, defenderSum));
                                }
                                break;
                            }
                        }

                        if (!foundValid)
                        {
                            lock (locker)
                            {
                                if (minFailedAttackerBudget == null || attackerBudget < minFailedAttackerBudget)
                                {
                                    minFailedAttackerBudget = attackerBudget;
                                }
                            }
                        }
                    }
                    catch (Exception ex) when (!(ex is NoAppropriateResult))
                    {
                        lock (locker)
                        {
                            if (minFailedAttackerBudget == null || attackerBudget < minFailedAttackerBudget)
                            {
                                minFailedAttackerBudget = attackerBudget;
                            }
                        }
                    }
                    finally
                    {
                        semaphore.Release();
                    }
                }, token));
            }

            await Task.WhenAll(tasks);

            if (minFailedAttackerBudget != null)
            {
                var filteredValidResults = validResults
                    .Where(r => r.AttackerCost <= minFailedAttackerBudget.Value)
                    .ToList();
                
                filteredValidResults.Add(new PrismOutputResult(minFailedAttackerBudget.Value, defenderSums.Last()));

                FileSaver.PrismOutputToCsv(filteredValidResults);
                return false;
            }
            else
            {
                FileSaver.PrismOutputToCsv(validResults);
                return true;
            }
            
        }

        public bool RunModelWithVaryingBudgetsNonAsync(string modelPath, string propsPath, IEnumerable<Node> nodes)
        {
            List<PrismOutputResult> allResults = new List<PrismOutputResult>();
            var attackSums = AttackerCostSummarizer.AllSubsetSums(nodes);
            var defenderSums = DefenderCostSummarizer.AllSubsetSums(nodes);
            var maxDefenderBudget = DefenderCostSummarizer.AllSubsetSums(nodes).Last();
            var maxAttackerBudget = attackSums.Last();
            
            for (int attackerBudget = 0; attackerBudget <= maxAttackerBudget; attackerBudget++)
            {
                string output = "";
                string errors = "";
            
                RunPrismProcess(CreatePrismProcessWithConstants(modelPath, propsPath, maxDefenderBudget, attackerBudget), ref output, ref errors);
                var results = PrismOutputParser.ParseModelResults(output);
                int defenderParetoPoint;
                try
                {
                    defenderParetoPoint = ParetoPointFinder.FindPoint(results, defenderSums);
                }
                catch (NoAppropriateResult)
                {
                    FileSaver.PrismOutputToCsv(allResults);
                    return false;
                }
            
                var outputResult = new PrismOutputResult(attackerBudget, defenderParetoPoint);
                allResults.Add(outputResult);
            }
            
            FileSaver.PrismOutputToCsv(allResults);
            return true;
        }
        
        private void ConsolePrismOutput(string modelPath, string propsPath)
        {
            string output = "";
            string errors = "";
            RunPrismProcess(CreatePrismProcess(modelPath, propsPath), ref output, ref errors);
            Console.WriteLine("Output:\n" + output);
            Console.WriteLine("Errors:\n" + errors);
        }

        // private PrismOutputResult? StorePrismOutput(string budgetInfo, string modelPath, string propsPath)
        // {
        //     string output = "";
        //     string errors = "";
        //     RunPrismProcess(CreatePrismProcess(modelPath, propsPath), ref output, ref errors);
        //     var results = PrismOutputParser.ParseModelResults(budgetInfo, output);
        //
        //     // foreach (var result in results)
        //     // {
        //     //     Console.WriteLine($"AttackerCost = {result.AttackerCost}, DefenderCost = {result.DefenderCost}");
        //     // }
        //
        //     return results.Count > 0 ? results[0] : null;
        // }

        private ProcessStartInfo CreatePrismProcess(string modelPath, string propsPath)
        {
            var startInfo = new ProcessStartInfo
            {
                FileName = "cmd.exe",
                Arguments = $"/c \"\"{prismBatPath}\" \"{Path.GetFullPath(modelPath)}\" \"{Path.GetFullPath(propsPath)}\"\"",
                RedirectStandardOutput = true,
                RedirectStandardError = true,
                UseShellExecute = false,
                CreateNoWindow = true,
                WorkingDirectory = prismWorkingDir
            };
            return startInfo;
        }
        private ProcessStartInfo CreatePrismProcessWithConstants(string modelPath, string propsPath, int defenderBudget, int attackerBudget)
        {
            string constants = $"-const INIT_DEFENDER_BUDGET={defenderBudget},INIT_ATTACKER_BUDGET={attackerBudget}";
    
            var startInfo = new ProcessStartInfo
            {
                FileName = "cmd.exe",
                Arguments = $"/c \"\"{prismBatPath}\" \"{Path.GetFullPath(modelPath)}\" \"{Path.GetFullPath(propsPath)}\" {constants}\"",
                RedirectStandardOutput = true,
                RedirectStandardError = true,
                UseShellExecute = false,
                CreateNoWindow = true,
                WorkingDirectory = prismWorkingDir
            };
            return startInfo;
        }


        private void RunPrismProcess(ProcessStartInfo startInfo, ref string output, ref string errors)
        {
            try
            {
                using var process = Process.Start(startInfo);
                output = process.StandardOutput.ReadToEnd();
                errors = process.StandardError.ReadToEnd();

                process.WaitForExit();
            }
            catch (Exception ex)
            {
                Console.WriteLine("Ошибка запуска PRISM: " + ex.Message);
            }
        }
        
    }


}