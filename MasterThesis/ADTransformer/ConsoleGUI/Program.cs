using System;
using System.IO;
using System.Threading.Tasks;
using ADTransformer;
using NodeAdapters;
using ParetoFrontBuilder;
using PrismCodeGenerator;
using PrismCodeGenerator.Models;
using PrismFileExporter;
using PrismRunner;
using Utilities;
using System.Diagnostics;

namespace ConsoleGUI
{
    internal class Program
    {
        public static async Task Main(string[] args)
        {
            var parser = new AdtParser();
            //var treeRoot = parser.Parse("E:/PRISM/ADTool_outputs/simpleAdt_test.xml");
            //var treeRoot = parser.Parse("E:/PRISM/ADTool_outputs/simpleAdt_extended.xml");
            //var treeRoot = parser.Parse("E:/PRISM/ADTool_outputs/simple_oneDefender.xml");
            //var treeRoot = parser.Parse("E:/PRISM/ADTool_outputs/complexAdt_v2.xml");
            //var treeRoot = parser.Parse("E:/PRISM/ADTool_outputs/complexAdt_v2_modified.xml");
            //var treeRoot = parser.Parse("E:/PRISM/ADTool_outputs/complexAdt_v2_modifiedv2.xml");
            var treeRoot = parser.Parse("E:/PRISM/ADTool_outputs/dansADT.xml");
            //var treeRoot = parser.Parse("E:/PRISM/ADTool_outputs/complexAdt_v3.xml");
            //var treeRoot = parser.Parse("E:/PRISM/ADTool_outputs/superComplexADT.xml");
            //var treeRoot = parser.Parse("E:/PRISM/ADTool_outputs/reuseNodeTest.xml");
            
            
            SaveSingleModel(treeRoot);
            CreateParetoFrontPropertyList(treeRoot);
            var stopwatch = Stopwatch.StartNew();
            var defenderWon = await RunPrism(treeRoot);
            //var defenderWon = RunPrismNonAsync(treeRoot);
            stopwatch.Stop();
            BuildPareto(defenderWon);
            Console.WriteLine($"Total time: {stopwatch.Elapsed.TotalSeconds:F2} seconds");
        }
        
        static void SaveSingleModel(AdtNode adtRoot)
        {
            var adapter = new AdtToPrismNodeAdapter();
            Node prismRoot = adapter.Convert(adtRoot);

            // теперь можно сгенерировать код
            var composer = new PrismCodeComposer();
            string code = composer.Compose(new[] { prismRoot });
            
            PrismExporter.Instance.SaveSingle(code, "generatedSample", @"D:/GitHub Repos/ADTransformer/ADTransformer/GeneratedPrismFiles");
        }
        
        static void CreateParetoFrontPropertyList(AdtNode adtRoot)
        {
            var adapter = new AdtToPrismNodeAdapter();
            Node prismRoot = adapter.Convert(adtRoot);
            var generator = new PropertyGenerator();
            string propertyList = generator.GeneratePropertyList(new[] { prismRoot });
            PrismPropertyExporter.Instance.GenerateParetoFrontProperties(propertyList);
        }

        static async Task<bool> RunPrism(AdtNode adtRoot)
        {
            var adapter = new AdtToPrismNodeAdapter();
            Node prismRoot = adapter.Convert(adtRoot);
            string baseDir = AppDomain.CurrentDomain.BaseDirectory;
            string solutionDir = Path.GetFullPath(Path.Combine(baseDir, "..", "..", ".."));
            string solutionRoot = SolutionRootFinder.Instance.GetSolutionRoot();
            string propsPath = Path.Combine(solutionRoot, "ParetoFrontProps", "paretoFrontProperty.props");
            string modelPath = Path.Combine(solutionDir, "GeneratedPrismFiles", "generatedSample.prism");
            var prismRunner = new PrismRunner.PrismRunner();
            //prismRunner.RunSingleModel(modelPath, propsPath);
            //prismRunner.RunMultipleModels(propsPath);
            return await prismRunner.RunModelWithVaryingBudgets(modelPath, propsPath, new[] { prismRoot });
        }
        
        static bool RunPrismNonAsync(AdtNode adtRoot)
        {
            var adapter = new AdtToPrismNodeAdapter();
            Node prismRoot = adapter.Convert(adtRoot);
            string baseDir = AppDomain.CurrentDomain.BaseDirectory;
            string solutionDir = Path.GetFullPath(Path.Combine(baseDir, "..", "..", ".."));
            string solutionRoot = SolutionRootFinder.Instance.GetSolutionRoot();
            string propsPath = Path.Combine(solutionRoot, "ParetoFrontProps", "paretoFrontProperty.props");
            string modelPath = Path.Combine(solutionDir, "GeneratedPrismFiles", "generatedSample.prism");
            var prismRunner = new PrismRunner.PrismRunner();
            //prismRunner.RunSingleModel(modelPath, propsPath);
            //prismRunner.RunMultipleModels(propsPath);
            return prismRunner.RunModelWithVaryingBudgetsNonAsync(modelPath, propsPath, new[] { prismRoot });
        }

        static void BuildPareto(bool defenderWon)
        {
            string baseDir = AppDomain.CurrentDomain.BaseDirectory;
            string solutionDir = Path.GetFullPath(Path.Combine(baseDir, "..", "..", ".."));
            string solutionRoot = SolutionRootFinder.Instance.GetSolutionRoot();
            string csvPath = Path.Combine(solutionRoot, "CSVFiles", "output.csv");
            ParetoBuilder.RunParetoScript(csvPath, defenderWon);
        }
    }
}