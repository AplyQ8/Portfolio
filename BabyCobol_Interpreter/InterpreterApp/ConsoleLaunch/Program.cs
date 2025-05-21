using System.Text;
using Antlr4.Runtime;

namespace ConsoleLaunch;

static class ConsoleApplication
{
    private static readonly string ProgramPath = "..\\..\\..\\CobolProgram.txt";
    public static void Main()
    {
        StringBuilder builtString = FileReader.LineByLineRead(ProgramPath);
        
        ICharStream input = CharStreams.fromString(builtString.ToString());
        cobolLexer lexer = new cobolLexer(input);
        CustomListener listener = new CustomListener();
        cobolParser parser = new cobolParser(new CommonTokenStream(lexer));
        parser.AddParseListener(listener);
        cobolParser.ProgramContext program = parser.program();

        CustomVisitor visitor = new CustomVisitor(listener.getProcedures());
        visitor.Visit(program);
    }
}