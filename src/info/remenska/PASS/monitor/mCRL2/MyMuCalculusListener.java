package info.remenska.PASS.monitor.mCRL2;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;


public class MyMuCalculusListener extends mucalculusBaseListener{
	private final static Logger LOGGER = Logger.getLogger("info.remenska.PASS"); 
	
	@Override public void enterTrueStateFrm(@NotNull mucalculusParser.TrueStateFrmContext ctx) {
	  LOGGER.finest("Entered TRUE \0/ ");
	}
	/**
	 * {@inheritDoc}
	 * <p/>
	 * The default implementation does nothing.
	 */
	@Override public void exitTrueStateFrm(@NotNull mucalculusParser.TrueStateFrmContext ctx) { 
	  LOGGER.finest("Exited TRUE \0/ ");
	}
}
