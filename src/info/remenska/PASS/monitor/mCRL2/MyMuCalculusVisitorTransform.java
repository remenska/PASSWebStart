package info.remenska.PASS.monitor.mCRL2;

// Generated from mucalculus.g4 by ANTLR 4.1
import info.remenska.PASS.monitor.mCRL2.mucalculusParser.RegFrmContext;

import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.misc.NotNull;

public class MyMuCalculusVisitorTransform extends mucalculusBaseVisitor<String> {
	public static BufferedTokenStream tokens;
	public static TokenStreamRewriter rewriter;

	public MyMuCalculusVisitorTransform(BufferedTokenStream tokens) {
		MyMuCalculusVisitorTransform.tokens = tokens;
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
			StringBuffer result = new StringBuffer();
			mucalculusParser.RegFrmContext child1 = ctx.regFrm();
			mucalculusParser.RegFrmContext child1modified = (RegFrmContext) ctx
					.regFrm().getChild(0);
			Token t01 = (Token) ctx.start;
			Token t02 = (Token) ctx.stop;
			Token t1 = (Token) child1.start;
			Token t2 = (Token) child1.stop;
//			System.out.println(">>>>>>>> " + ctx.getText());
//			System.out.println(">>>>>>" + t01.getTokenIndex()+1 + " " + t02.getTokenIndex());
			result.append("([" + child1modified.getText() + "+]" + ctx.stateFrm().getText() + " && " + ctx.stateFrm().getText()+")");
			rewriter.insertAfter(t02.getTokenIndex(), "([" + child1modified.getText() + "+]" + ctx.stateFrm().getText() + " && " + ctx.stateFrm().getText()+")");
			rewriter.delete(t01.getTokenIndex(), t02.getTokenIndex());
			return result.toString();
		} else
		return visitChildren(ctx);

	}

//	@Override
//	public String visitBracketsActionFrm(
//			@NotNull mucalculusParser.BracketsActionFrmContext ctx) {
//		String result = new String("" + visit(ctx.actFrm()) + "");
//		return result;
//
//	}

}
