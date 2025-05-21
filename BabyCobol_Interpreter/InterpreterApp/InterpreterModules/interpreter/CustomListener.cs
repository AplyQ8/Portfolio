using Antlr4.Runtime;
using Antlr4.Runtime.Misc;
using Antlr4.Runtime.Tree;

public class CustomListener:cobolBaseListener{
    private Dictionary<string, IParseTree> procedures = new Dictionary<string, IParseTree>();

    public Dictionary<string, IParseTree> getProcedures(){
        return procedures;
    }

    public override void ExitSentence([NotNull] cobolParser.SentenceContext context)
    {
        if (context.proc() != null){
            procedures.Add(context.proc().GetText(), context);
        }
    }

    public override void ExitCopy([NotNull] cobolParser.CopyContext context)
    {
        List<string> fileContent = new List<string>();
    try
    {
        //File has to be in ConsoleLaunch directory
        string filePath = "..\\..\\..\\" + context.LITERAL().GetText().Trim('"');
        using (StreamReader reader = new StreamReader(filePath))
        {
            string line;
            while ((line = reader.ReadLine()) != null)
            {
                fileContent.Add(line);
            }
        }
    }
    catch (FileNotFoundException e)
    {
        throw new Exception("An error occurred when opening the file.", e);
    }

    foreach (var s in fileContent)
    {
        string output = s;
        if (context.replacing() != null)
        {
            foreach (var r in context.replacing().replacements())
            {
                string toBeReplaced = r.COPY_LITERAL(0).GetText().Replace("=", "");
                string toReplaceWith = r.COPY_LITERAL(1).GetText().Replace("=", "");
                output = output.Replace(toBeReplaced, toReplaceWith);
            }
        }
        // Print instead of adding to the target file.
        Console.WriteLine(output);
    }
    }
}