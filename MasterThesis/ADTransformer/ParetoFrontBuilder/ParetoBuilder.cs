using System;
using System.Diagnostics;
using System.IO;

namespace ParetoFrontBuilder
{
    public class ParetoBuilder
    {
        public static void RunParetoScript(string csvPath, bool defenderWon)
        {
            string baseDir = AppDomain.CurrentDomain.BaseDirectory;
            string solutionDir = Path.GetFullPath(Path.Combine(baseDir, "..", "..", ".."));
            string pythonExe = Path.Combine(solutionDir, "ParetoFrontBuilder", "myenv", "Scripts" ,"python.exe");

            string scriptPath = Path.Combine(solutionDir, "ParetoFrontBuilder", "Scripts", "paretoBuilder.py");

            // Приводим к полным путям
            pythonExe = Path.GetFullPath(pythonExe);
            scriptPath = Path.GetFullPath(scriptPath);

            var psi = new ProcessStartInfo
            {
                FileName = pythonExe,
                Arguments = $"\"{scriptPath}\" \"{csvPath}\" {defenderWon.ToString().ToLower()}",
                RedirectStandardOutput = true,
                RedirectStandardError = true,
                UseShellExecute = false,
                CreateNoWindow = true,
                WorkingDirectory = Path.GetDirectoryName(scriptPath) // чтобы скрипт запускался из своей папки
            };
            using var process = new Process { StartInfo = psi };
            process.Start();

            string output = process.StandardOutput.ReadToEnd();
            string errors = process.StandardError.ReadToEnd();
            process.WaitForExit();
            string imagePath = Path.Combine(Path.GetDirectoryName(scriptPath), "pareto_front.png");
            if (File.Exists(imagePath))
            {
                Process.Start(new ProcessStartInfo(imagePath) { UseShellExecute = true });
            }

            Console.WriteLine("Python Output:");
            Console.WriteLine(output);
            if (!string.IsNullOrWhiteSpace(errors))
            {
                Console.WriteLine("Python Errors:");
                Console.WriteLine(errors);
            }
        }
    }
}