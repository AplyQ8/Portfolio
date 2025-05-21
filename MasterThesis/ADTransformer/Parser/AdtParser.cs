using System;
using System.Xml.Linq;

namespace ADTransformer;

public class AdtParser
{
    public AdtNode Parse(string filePath)
    {
        var doc = XDocument.Load(filePath);
        var rootElement = doc.Root?.Element("node");
        if (rootElement == null)
            throw new Exception("Invalid ADTree XML format");

        return ParseNode(rootElement, null, NodeType.Attacker);
    }
    
    private AdtNode ParseNode(XElement xmlNode, AdtNode? parent, NodeType inheritedRole)
    {
        var label = xmlNode.Element("label")?.Value.Replace(" ", "") ?? "unknown";
        var refinement = xmlNode.Attribute("refinement")?.Value;
        var switchRole = xmlNode.Attribute("switchRole")?.Value == "yes";
        var comment = xmlNode.Element("comment")?.Value ?? "";

        var node = new AdtNode
        {
            Label = label,
            Parent = parent,
            Role = switchRole ? SwitchRole(inheritedRole) : inheritedRole,
            Refinement = ParseRefinement(refinement),
            IsCountermeasure = switchRole,
            Price = ParsePrice(comment)
        };

        foreach (var childXml in xmlNode.Elements("node"))
        {
            var child = ParseNode(childXml, node, node.Role);
            node.Children.Add(child);
        }

        return node;
    }
    
    private RefinementType ParseRefinement(string? value)
    {
        return value == "disjunctive" ? RefinementType.Disjunctive : RefinementType.Conjunctive;
    }

    private NodeType SwitchRole(NodeType role)
    {
        return role == NodeType.Attacker ? NodeType.Defender : NodeType.Attacker;
    }
    
    private double? ParsePrice(string comment)
    {
        if (comment.Contains("p:"))
        {
            var pValue = comment
                .Split(new[] { "p:" }, StringSplitOptions.None)[1]
                .Split(new[] { '_', ' ', '\n' }, StringSplitOptions.RemoveEmptyEntries)[0];

            if (double.TryParse(pValue, System.Globalization.NumberStyles.Any, System.Globalization.CultureInfo.InvariantCulture, out double price))
                return price;
        }
        return null;
    }
}