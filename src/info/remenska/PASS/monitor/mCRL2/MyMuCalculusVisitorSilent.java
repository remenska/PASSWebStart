package info.remenska.PASS.monitor.mCRL2;

// Generated from mucalculus.g4 by ANTLR 4.1
import info.remenska.PASS.monitor.mCRL2.mucalculusParser.RegFrmContext;

import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.misc.NotNull;

public class MyMuCalculusVisitorSilent extends mucalculusBaseVisitor<String> {
	public static BufferedTokenStream tokens;
	public static TokenStreamRewriter rewriter;

	public MyMuCalculusVisitorSilent(BufferedTokenStream tokens) {
		MyMuCalculusVisitorSilent.tokens = tokens;
		rewriter = new TokenStreamRewriter(tokens);
	}

	@Override
	public String visitActionFormulaRegForm(
			@NotNull mucalculusParser.ActionFormulaRegFormContext ctx) {
		String result = new String("" + visit(ctx.actFrm()) + "");
		return result;
	}

	@Override
	public String visitSequentialCompositionRegForm(
			@NotNull mucalculusParser.SequentialCompositionRegFormContext ctx) {
		Token t2 = (Token) ctx.regFrm(0).stop;

		if (tokens.get(t2.getTokenIndex() + 1).getText().equals(".")) {
			// transformation
			rewriter.delete(t2.getTokenIndex() + 1);
			rewriter.insertAfter(t2.getTokenIndex(), "][");
			// we're ready
		}
		return visitChildren(ctx);
	}

	@Override
	public String visitBoxModalityStateFrm(
			@NotNull mucalculusParser.BoxModalityStateFrmContext ctx) {
		if (ctx.regFrm() instanceof mucalculusParser.IterationRegFormContext) {
			mucalculusParser.RegFrmContext child1 = ctx.regFrm();
			mucalculusParser.RegFrmContext child1modified = (RegFrmContext) ctx
					.regFrm().getChild(0);
			Token t1 = (Token) child1.start;
			Token t2 = (Token) child1.stop;
			rewriter.insertAfter(t2.getTokenIndex(), "(nil" + " + "
					+ child1modified.getText() + "+" + ")");
			rewriter.delete(t1.getTokenIndex(), t2.getTokenIndex());
		}
		return visitChildren(ctx);

	}

	@Override
	public String visitBracketsActionFrm(
			@NotNull mucalculusParser.BracketsActionFrmContext ctx) {
		String result = new String("" + visit(ctx.actFrm()) + "");
		return result;

	}

}
