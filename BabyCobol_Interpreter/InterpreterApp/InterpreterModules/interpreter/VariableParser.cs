using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

public class VariableParser
{
    public string ParseSingleVar(cobolParser.SinglevarContext singlevar)
    {
        return singlevar.identifiers().Select(i => i.GetText()).Aggregate((acc, val) => acc + val);
    }

    public List<string> ParseMultiVar(List<cobolParser.IdentifiersContext> multivar, ICollection<string> declaredNames)
    {
        var matches = new Dictionary<int, List<int>> { { 0, new List<int>() } };
        var counts = new Dictionary<int, int> { { 0, 1 } };

        var combined = new StringBuilder();
        int currentIdx = 0;

        List<string> varNames = multivar.Select(i => i.GetText()).ToList();

        foreach (var name in varNames)
        {
            combined.Append(name);
            currentIdx += name.Length;

            var currentMatches = new List<int>();
            matches[currentIdx] = currentMatches;

            foreach (var entry in counts.Keys.ToList())
            {
                int precedingIdx = entry;
                int precedingCount = counts[entry];
                if (precedingCount > 0 && declaredNames.Contains(combined.ToString(precedingIdx, currentIdx - precedingIdx)))
                {
                    currentMatches.Add(precedingIdx);
                    if (counts.ContainsKey(currentIdx))
                        counts[currentIdx] += precedingCount;
                    else
                        counts[currentIdx] = precedingCount;
                }
            }
        }

        if (counts.TryGetValue(combined.Length, out int finalCount) && finalCount == 1)
        {
            return SingleMatch(matches, combined.ToString());
        }
        else if (finalCount > 1 && declaredNames.All(declaredNames.Contains))
        {
            return varNames;
        }

        return new List<string>();
    }

    private List<string> SingleMatch(Dictionary<int, List<int>> matches, string combined)
    {
        List<string> result = new List<string>();
        int current = combined.Length;

        while (matches[current].Any())
        {
            List<int> precedingPositions = matches[current];
            if (precedingPositions.Count != 1)
                throw new InvalidOperationException("Expected exactly one preceding position.");

            int previous = precedingPositions[0];

            result.Add(combined.Substring(previous, current - previous));

            current = previous;
        }

        result.Reverse();

        return result;
    }
}
