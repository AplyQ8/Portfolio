// Generated from c:/Users/baran/SoftWarEvolution/InterpreterApp/InterpreterModules/antlr/cobol.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link cobolParser}.
 */
public interface cobolListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link cobolParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(cobolParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link cobolParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(cobolParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link cobolParser#identification_division}.
	 * @param ctx the parse tree
	 */
	void enterIdentification_division(cobolParser.Identification_divisionContext ctx);
	/**
	 * Exit a parse tree produced by {@link cobolParser#identification_division}.
	 * @param ctx the parse tree
	 */
	void exitIdentification_division(cobolParser.Identification_divisionContext ctx);
	/**
	 * Enter a parse tree produced by {@link cobolParser#data_division}.
	 * @param ctx the parse tree
	 */
	void enterData_division(cobolParser.Data_divisionContext ctx);
	/**
	 * Exit a parse tree produced by {@link cobolParser#data_division}.
	 * @param ctx the parse tree
	 */
	void exitData_division(cobolParser.Data_divisionContext ctx);
	/**
	 * Enter a parse tree produced by {@link cobolParser#picture}.
	 * @param ctx the parse tree
	 */
	void enterPicture(cobolParser.PictureContext ctx);
	/**
	 * Exit a parse tree produced by {@link cobolParser#picture}.
	 * @param ctx the parse tree
	 */
	void exitPicture(cobolParser.PictureContext ctx);
	/**
	 * Enter a parse tree produced by {@link cobolParser#like}.
	 * @param ctx the parse tree
	 */
	void enterLike(cobolParser.LikeContext ctx);
	/**
	 * Exit a parse tree produced by {@link cobolParser#like}.
	 * @param ctx the parse tree
	 */
	void exitLike(cobolParser.LikeContext ctx);
	/**
	 * Enter a parse tree produced by {@link cobolParser#procedure_division}.
	 * @param ctx the parse tree
	 */
	void enterProcedure_division(cobolParser.Procedure_divisionContext ctx);
	/**
	 * Exit a parse tree produced by {@link cobolParser#procedure_division}.
	 * @param ctx the parse tree
	 */
	void exitProcedure_division(cobolParser.Procedure_divisionContext ctx);
	/**
	 * Enter a parse tree produced by {@link cobolParser#using}.
	 * @param ctx the parse tree
	 */
	void enterUsing(cobolParser.UsingContext ctx);
	/**
	 * Exit a parse tree produced by {@link cobolParser#using}.
	 * @param ctx the parse tree
	 */
	void exitUsing(cobolParser.UsingContext ctx);
	/**
	 * Enter a parse tree produced by {@link cobolParser#use}.
	 * @param ctx the parse tree
	 */
	void enterUse(cobolParser.UseContext ctx);
	/**
	 * Exit a parse tree produced by {@link cobolParser#use}.
	 * @param ctx the parse tree
	 */
	void exitUse(cobolParser.UseContext ctx);
	/**
	 * Enter a parse tree produced by {@link cobolParser#sentence}.
	 * @param ctx the parse tree
	 */
	void enterSentence(cobolParser.SentenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link cobolParser#sentence}.
	 * @param ctx the parse tree
	 */
	void exitSentence(cobolParser.SentenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link cobolParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(cobolParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link cobolParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(cobolParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link cobolParser#display}.
	 * @param ctx the parse tree
	 */
	void enterDisplay(cobolParser.DisplayContext ctx);
	/**
	 * Exit a parse tree produced by {@link cobolParser#display}.
	 * @param ctx the parse tree
	 */
	void exitDisplay(cobolParser.DisplayContext ctx);
	/**
	 * Enter a parse tree produced by {@link cobolParser#add}.
	 * @param ctx the parse tree
	 */
	void enterAdd(cobolParser.AddContext ctx);
	/**
	 * Exit a parse tree produced by {@link cobolParser#add}.
	 * @param ctx the parse tree
	 */
	void exitAdd(cobolParser.AddContext ctx);
	/**
	 * Enter a parse tree produced by {@link cobolParser#withnoadvancing}.
	 * @param ctx the parse tree
	 */
	void enterWithnoadvancing(cobolParser.WithnoadvancingContext ctx);
	/**
	 * Exit a parse tree produced by {@link cobolParser#withnoadvancing}.
	 * @param ctx the parse tree
	 */
	void exitWithnoadvancing(cobolParser.WithnoadvancingContext ctx);
	/**
	 * Enter a parse tree produced by {@link cobolParser#atomic}.
	 * @param ctx the parse tree
	 */
	void enterAtomic(cobolParser.AtomicContext ctx);
	/**
	 * Exit a parse tree produced by {@link cobolParser#atomic}.
	 * @param ctx the parse tree
	 */
	void exitAtomic(cobolParser.AtomicContext ctx);
	/**
	 * Enter a parse tree produced by {@link cobolParser#giving}.
	 * @param ctx the parse tree
	 */
	void enterGiving(cobolParser.GivingContext ctx);
	/**
	 * Exit a parse tree produced by {@link cobolParser#giving}.
	 * @param ctx the parse tree
	 */
	void exitGiving(cobolParser.GivingContext ctx);
	/**
	 * Enter a parse tree produced by {@link cobolParser#identifiers}.
	 * @param ctx the parse tree
	 */
	void enterIdentifiers(cobolParser.IdentifiersContext ctx);
	/**
	 * Exit a parse tree produced by {@link cobolParser#identifiers}.
	 * @param ctx the parse tree
	 */
	void exitIdentifiers(cobolParser.IdentifiersContext ctx);
}