﻿using System.Collections.Generic;
using System.Text;
using PrismCodeGenerator.Models;
using PrismCodeGenerator.Utils;

namespace PrismCodeGenerator.SectionGenerators;

public class BudgetSectionGenerator: ICodeSectionGenerator
{
    private readonly int _attackerBudget;
    private readonly int _defenderBudget;

    public BudgetSectionGenerator(int attackerBudget, int defenderBudget)
    {
        _attackerBudget = attackerBudget;
        _defenderBudget = defenderBudget;
    }
    public string Generate(IEnumerable<Node> nodes)
    {
        var sb = new StringBuilder();
        
        GenerateAttackerBudget(ref sb);
        GenerateDefenderBudget(ref sb);

        return sb.ToString();
    }

    private void GenerateAttackerBudget(ref StringBuilder sb)
    {
        sb.AppendLine(
            $"const int {StaticGlobalVariableHolder.InitialAttackerBudgetName};");
        sb.AppendLine(
            $"global {NameFormatter.GetAttackerBudgetName()}: [0..{BudgetManager.Instance.MaxAttackerBudget}] init {StaticGlobalVariableHolder.InitialAttackerBudgetName};");
    }

    private void GenerateDefenderBudget(ref StringBuilder sb)
    {
        sb.AppendLine(
            $"const int {StaticGlobalVariableHolder.InitialDefenderBudgetName};");
        sb.AppendLine(
            $"global {NameFormatter.GetDefenderBudgetName()}: [0..{BudgetManager.Instance.MaxDefenderBudget}] init {StaticGlobalVariableHolder.InitialDefenderBudgetName};");
    }
}