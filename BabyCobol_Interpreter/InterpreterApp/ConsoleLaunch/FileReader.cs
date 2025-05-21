using System.Text;

namespace ConsoleLaunch;

public class FileReader
{
    
    /// <summary>
    /// Reads file and writes content in StringBuilder
    /// </summary>
    /// <param name="path">File path</param>
    /// <returns>String builder object</returns>
    public static StringBuilder LineByLineRead(string path)
    {
        var stringBuilder = new StringBuilder();

        using (StreamReader reader = new StreamReader(path))
        {
            string? line;
            while ((line = reader.ReadLine()) != null)
            {
                stringBuilder.AppendLine(line);
            }
        }

        return stringBuilder;
    }
    /// <summary>
    /// Reads file and writes in StringBuilder object
    /// </summary>
    /// <param name="path">File path</param>
    /// <param name="stringBuilder">out param. The stringBuilder in which content of file will be written</param>
    public static void LineByLineRead(string path, out StringBuilder? stringBuilder)
    {
        stringBuilder = null;
        using (StreamReader reader = new StreamReader(path))
        {
            string? line;
            while ((line = reader.ReadLine()) != null)
            {
                stringBuilder?.AppendLine(line);
            }
        }
    }
}