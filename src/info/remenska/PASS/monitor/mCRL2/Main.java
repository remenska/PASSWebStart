package info.remenska.PASS.monitor.mCRL2;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.io.IOUtils;
//import org.antlr.runtime.ANTLRInputStream;
//import org.antlr.runtime.CharStream;
import java.util.logging.Logger;

public class Main {
	private final static Logger LOGGER = Logger.getLogger("info.remenska.PASS"); 
	public String generateMonitor(String args[])
			throws java.io.FileNotFoundException, NotMonitorableException,
			java.lang.NullPointerException, Exception {
		String result = new String();
		if (args.length < 3) {
			LOGGER.warning("Your arguments: " + args.toString());
			LOGGER.warning("Usage: java info.remenska.PASS.monitor.mCRL2.Main <mCRL2ModelFile> <muCalculusFile> <targetmCRL2File> <humanReadable>");
			LOGGER.warning("\t\t<humanReadable> is optional boolean switch, and if set to true, \n\t\tyields to process names that are not parsable by mCRL2. ");
			LOGGER.warning("\t\tIt should be used this way only for inspecting the translation. Default value is false.");
			return result;
		}
		try {
			String actionSort = new String();
			String actionFormula = new String();
			String mappings = new String();
			StringBuffer outputModel = new StringBuffer();

			InputStream ismcrl2 = new FileInputStream(args[0]);
			// full mCRL2 grammar
			String initialStringmcrl2 = IOUtils.toString(ismcrl2);
			String[] splitModel = initialStringmcrl2.split("init ");

			mcrl2Lexer lexermcrl2 = new mcrl2Lexer(
					(CharStream) new ANTLRInputStream(initialStringmcrl2));
			CommonTokenStream tokensmcrl2 = new CommonTokenStream(lexermcrl2);
			mcrl2Parser parsermcrl2 = new mcrl2Parser(tokensmcrl2);
			parsermcrl2.setErrorHandler(new BailErrorStrategy());
			ParseTree treemcrl2 = parsermcrl2.start();

			// we're using this visitor just to collect action && argument types
			Mymcrl2Visitor visitormcrl2 = new Mymcrl2Visitor(tokensmcrl2);
			visitormcrl2.visit(treemcrl2);
			
			// remove those actions not in allowed (but only if there is allowed(...) segment
			if(Mymcrl2Visitor.allowedActionsList!=null){
				Enumeration<String> actEn = Mymcrl2Visitor.actionsDict.keys();
				while(actEn.hasMoreElements()){
					String key = actEn.nextElement();
					if(!Mymcrl2Visitor.allowedActionsList.contains(key))
						Mymcrl2Visitor.actionsDict.remove(key);
				}
			}
			
			actionSort = createActionSort(visitormcrl2);
			actionFormula = createActionFormulaSort();
			mappings = createMappings(visitormcrl2);

			// END we're using this visitor just to collect action && argument
			// types

			String finalString = args[1];
			outputModel.append("% Original formula:" + finalString + "\n");

			LOGGER.fine("----------------------------------------");
			LOGGER.fine("Original formula : " + finalString);
			mucalculusLexer lexer = new mucalculusLexer(
					(CharStream) new ANTLRInputStream(finalString));
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			mucalculusParser parser = new mucalculusParser(tokens);
			parser.setErrorHandler(new BailErrorStrategy());

			ParseTree tree = parser.start();

			MyMuCalculusVisitorSilent visitor = new MyMuCalculusVisitorSilent(
					tokens);
			visitor.visit(tree);

			finalString = preprocess(MyMuCalculusVisitorSilent.rewriter
					.getText());
			String currentFormula = finalString;
			String prevFormula = "";
			while(!currentFormula.equals(prevFormula)){
				prevFormula = currentFormula;
				lexer = new mucalculusLexer((CharStream) new ANTLRInputStream(
						currentFormula));
				tokens = new CommonTokenStream(lexer);
				parser = new mucalculusParser(tokens);
				parser.setErrorHandler(new BailErrorStrategy());
				tree = parser.start();
				MyMuCalculusVisitorTransform visitor2 = new MyMuCalculusVisitorTransform(tokens);
				 visitor2.visit(tree);
				currentFormula = preprocess(MyMuCalculusVisitorTransform.rewriter.getText());
			}

			lexer = new mucalculusLexer((CharStream) new ANTLRInputStream(
					currentFormula));
			tokens = new CommonTokenStream(lexer);
			parser = new mucalculusParser(tokens);
			parser.setErrorHandler(new BailErrorStrategy());
			tree = parser.start();
			visitor = new MyMuCalculusVisitorSilent(tokens);
			visitor.visit(tree);
			String finalStringModified = preprocess(MyMuCalculusVisitorSilent.rewriter
					.getText());
			lexer = new mucalculusLexer((CharStream) new ANTLRInputStream(
					finalStringModified));
			tokens = new CommonTokenStream(lexer);
			parser = new mucalculusParser(tokens);
			parser.setErrorHandler(new BailErrorStrategy());

			tree = parser.start();
			MyMuCalculusVisitor visitor1 = null;
			if (args.length == 4)
				visitor1 = new MyMuCalculusVisitor(tokens,
						Mymcrl2Visitor.actionsDict,
						Boolean.parseBoolean(args[3]));
			else
				visitor1 = new MyMuCalculusVisitor(tokens,
						Mymcrl2Visitor.actionsDict, false);

			visitor1.visit(tree);
			outputModel.append("% ============================\n");
			outputModel.append("% Modified formula:" + finalStringModified
					+ "\n");
			outputModel.append("% ============================\n");

			outputModel.append(actionSort);
			outputModel.append(actionFormula);
			outputModel.append(mappings);
			outputModel.append(MyMuCalculusVisitor.finalResult.toString());
			outputModel.append(createInit());

			BufferedOutputStream os = new BufferedOutputStream(
					new FileOutputStream(args[2] + "_mod.mcrl2"));
			for (int i = 0; i < (splitModel.length - 1); i++)
				os.write(splitModel[i].getBytes());
			os.flush();
			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
			String generated = "\n% created: " + currentTimestamp + "\n"+ "\n%====== MONITOR PART GENERATED ==============\n";
			os.write(generated.getBytes());
			os.write(outputModel.toString().getBytes());
			os.flush();
			result = new String(args[2] + "_mod.mcrl2");
		} catch (java.io.FileNotFoundException e) {
			LOGGER.severe("PROBLEM! File does not exist or permission denied:"
							+ e.getMessage());
			throw e;
		}

		catch (java.lang.NullPointerException e) {
			LOGGER.severe("Mu-calculus formula or mCRL2 is not well formed. ");
			throw e;

		} catch (RuntimeException e) {
			LOGGER.severe("Something went terribly wrong. Check for syntax errors in model or formula");
			throw e;

		} catch (Exception e) {
			LOGGER.severe("Something went terribly wrong. ");
			throw e;
		}
		return result;

	}


	public static String preprocess(String input) {
		StringBuffer buffer = new StringBuffer(input);

		buffer = new StringBuffer(buffer.toString().replaceAll("forall",
				"forall "));
		buffer = new StringBuffer(buffer.toString().replaceAll("exists",
				"exists "));
		buffer = new StringBuffer(buffer.toString().replaceAll("mu", ",mu "));
		buffer = new StringBuffer(buffer.toString().replaceAll("nu", "nu "));
		buffer = new StringBuffer(buffer.toString().replaceAll("lambda",
				"lambda "));
		buffer = new StringBuffer(buffer.toString().replaceAll("sort", "sort "));
		buffer = new StringBuffer(buffer.toString().replaceAll("struct",
				"struct "));
		buffer = new StringBuffer(buffer.toString().replaceAll("false",
				"false "));
		buffer = new StringBuffer(buffer.toString().replaceAll("nil", "nil "));
		buffer = new StringBuffer(buffer.toString().replaceAll("!", "! "));
		buffer = new StringBuffer(buffer.toString().replaceAll("&&", "&& "));

		return buffer.toString();
	}

	public static String createActionSort(Mymcrl2Visitor visitor) {
		StringBuffer result = new StringBuffer();
		result.append("act error ;\n");
		result.append("sort Action = struct ");
		StringBuffer actions = new StringBuffer();
		Hashtable<String, ArrayList<String>> actionsDict = visitor.actionsDict;

			Enumeration<String> keys = actionsDict.keys();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				ArrayList<String> dataTypes = actionsDict.get(key);
				result.append(key + "_mon");
				actions.append("act " + key + "_mon," + key + "_found");
				if (dataTypes.size() > 0) {
					result.append("(");
					actions.append(":");
					int counter = 1;
					Iterator<String> iter = dataTypes.iterator();
					while (iter.hasNext()) {
						String dt = iter.next();
						result.append(key + "_" + "arg" + counter++ + ":" + dt);
						actions.append(dt);
						if (iter.hasNext()) {
							result.append(",");
							actions.append("#");
						}
					}
					result.append(")");
				}
				if (keys.hasMoreElements()) {
					result.append("\n | ");
				}
				actions.append(";\n");
			}	

		result.append(";\n");
		result.append("\n" + actions);
		return result.toString();
	}

	public static String createMappings(Mymcrl2Visitor visitor) {
		StringBuffer result = new StringBuffer();
		result.append("map satisfy: Action # ActionFormula -> Bool;\n");
		result.append("var c1,c2:Action;\n");
		result.append("f,g:ActionFormula;\n");
		result.append("eqn\n");
		result.append("\t satisfy(c1,action(c2)) = (c1 == c2) ;");
		result.append("\t satisfy(c1,and(f,g)) = satisfy(c1,f) && satisfy(c1,g);\n");
		result.append("\t satisfy(c1,not(f)) = !satisfy(c1,f);\n");
		result.append("\t satisfy(c1,or(f,g)) = satisfy(c1,f) || satisfy(c1,g);\n");
		result.append("\t satisfy(c1,True) = true;\n");
		result.append("\t satisfy(c1,False) = false;\n");

		result.append("\n");
		return result.toString();
	}

	public static String createActionFormulaSort() {
		String result = new String(
				"\nsort ActionFormula = struct action(act1:Action)\n"
						+ "| and(phi1:ActionFormula,phi2:ActionFormula) \n"
						+ "| or(phi1:ActionFormula,phi2:ActionFormula)  \n"
						+ "| not(ActionFormula) \n" + "| True \n"
						+ "| False ; \n\n");

		return result;
	}

	public static String createInit() {
		StringBuffer result = new StringBuffer();
		result.append("init allow({error,");
			Enumeration<String> actions = Mymcrl2Visitor.actionsDict.keys();
			while (actions.hasMoreElements()) {
				result.append(actions.nextElement() + "_found");
				if (actions.hasMoreElements())
					result.append(",\n");
			}
			result.append("}, \n comm({");
			actions = Mymcrl2Visitor.actionsDict.keys();
			while (actions.hasMoreElements()) {
				String action = actions.nextElement();
				result.append("\t " + action + " | " + action + "_mon -> " + action
						+ "_found\n");
				if (actions.hasMoreElements())
					result.append(",\n");
			}
			result.append("\n},\n Monitor ");

			result.append(" || " + Mymcrl2Visitor.afterInit);
			result.append("));");

		return result.toString();
	}
	
	public String generateMonitorVisual(String args[])
			throws java.io.FileNotFoundException, NotMonitorableException,
			java.lang.NullPointerException, Exception {
		String result = new String();
		if (args.length < 3) {
			LOGGER.warning("Your arguments: " + args.toString());
			LOGGER.warning("Usage: java info.remenska.PASS.monitor.mCRL2.Main <mCRL2ModelFile> <muCalculusFile> <targetmCRL2File> <humanReadable>");
			LOGGER.warning("\t\t<humanReadable> is optional boolean switch, and if set to true, \n\t\tyields to process names that are not parsable by mCRL2. ");
			LOGGER.warning("\t\tIt should be used this way only for inspecting the translation. Default value is false.");
			return result;
		}
		try {
			String actionSort = new String();
			String actionFormula = new String();
			String mappings = new String();
			StringBuffer outputModel = new StringBuffer();

			InputStream ismcrl2 = new FileInputStream(args[0]);
			// full mCRL2 grammar
			String initialStringmcrl2 = IOUtils.toString(ismcrl2);
//			String[] splitModel = initialStringmcrl2.split("init ");

			mcrl2Lexer lexermcrl2 = new mcrl2Lexer(
					(CharStream) new ANTLRInputStream(initialStringmcrl2));
			CommonTokenStream tokensmcrl2 = new CommonTokenStream(lexermcrl2);
			mcrl2Parser parsermcrl2 = new mcrl2Parser(tokensmcrl2);
			parsermcrl2.setErrorHandler(new BailErrorStrategy());
			ParseTree treemcrl2 = parsermcrl2.start();

			// we're using this visitor just to collect action && argument types
			Mymcrl2Visitor visitormcrl2 = new Mymcrl2Visitor(tokensmcrl2);
			visitormcrl2.visit(treemcrl2);
			
			// remove those actions not in allowed (but only if there is allowed(...) segment
			if(Mymcrl2Visitor.allowedActionsList!=null){
				Enumeration<String> actEn = Mymcrl2Visitor.actionsDict.keys();
				while(actEn.hasMoreElements()){
					String key = actEn.nextElement();
					if(!Mymcrl2Visitor.allowedActionsList.contains(key))
						Mymcrl2Visitor.actionsDict.remove(key);
				}
			}
			
			actionSort = createActionSortVisual(visitormcrl2);
			actionFormula = createActionFormulaSort();
			mappings = createMappings(visitormcrl2);

			// END we're using this visitor just to collect action && argument
			// types

			String finalString = args[1];
			outputModel.append("% Original formula:" + finalString + "\n");

			LOGGER.fine("----------------------------------------");
			LOGGER.fine("Original formula : " + finalString);
			mucalculusLexer lexer = new mucalculusLexer(
					(CharStream) new ANTLRInputStream(finalString));
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			mucalculusParser parser = new mucalculusParser(tokens);
			parser.setErrorHandler(new BailErrorStrategy());

			ParseTree tree = parser.start();

			MyMuCalculusVisitorSilent visitor = new MyMuCalculusVisitorSilent(
					tokens);
			visitor.visit(tree);

			finalString = preprocess(MyMuCalculusVisitorSilent.rewriter
					.getText());
			String currentFormula = finalString;
			String prevFormula = "";
			while(!currentFormula.equals(prevFormula)){
				prevFormula = currentFormula;
				lexer = new mucalculusLexer((CharStream) new ANTLRInputStream(
						currentFormula));
				tokens = new CommonTokenStream(lexer);
				parser = new mucalculusParser(tokens);
				parser.setErrorHandler(new BailErrorStrategy());
				tree = parser.start();
				MyMuCalculusVisitorTransform visitor2 = new MyMuCalculusVisitorTransform(tokens);
				 visitor2.visit(tree);
				currentFormula = preprocess(MyMuCalculusVisitorTransform.rewriter.getText());
			}

			lexer = new mucalculusLexer((CharStream) new ANTLRInputStream(
					currentFormula));
			tokens = new CommonTokenStream(lexer);
			parser = new mucalculusParser(tokens);
			parser.setErrorHandler(new BailErrorStrategy());
			tree = parser.start();
			visitor = new MyMuCalculusVisitorSilent(tokens);
			visitor.visit(tree);
			String finalStringModified = preprocess(MyMuCalculusVisitorSilent.rewriter
					.getText());
			lexer = new mucalculusLexer((CharStream) new ANTLRInputStream(
					finalStringModified));
			tokens = new CommonTokenStream(lexer);
			parser = new mucalculusParser(tokens);
			parser.setErrorHandler(new BailErrorStrategy());

			tree = parser.start();
			MyMuCalculusVisitorVisual visitor1 = null;
			if (args.length == 4)
				visitor1 = new MyMuCalculusVisitorVisual(tokens,
						Mymcrl2Visitor.actionsDict,
						Boolean.parseBoolean(args[3]));
			else
				visitor1 = new MyMuCalculusVisitorVisual(tokens,
						Mymcrl2Visitor.actionsDict, false);

			visitor1.visit(tree);
			outputModel.append("% ============================\n");
			outputModel.append("% Modified formula:" + finalStringModified
					+ "\n");
			outputModel.append("% ============================\n");

			outputModel.append(actionSort);
			outputModel.append(actionFormula);
			outputModel.append(mappings);
			outputModel.append(MyMuCalculusVisitorVisual.finalResult.toString());
			outputModel.append(createInitVisual());

			BufferedOutputStream os = new BufferedOutputStream(
					new FileOutputStream(args[2] + "_modVisual.mcrl2"));
//			for (int i = 0; i < (splitModel.length - 1); i++)
//				os.write(splitModel[i].getBytes());
//			os.flush();
			String generated = "\n%====== MONITOR PART GENERATED ==============\n";
			os.write(generated.getBytes());
			os.write(outputModel.toString().getBytes());
			os.flush();
			result = new String(args[2] + "_modVisual.mcrl2");
		} catch (java.io.FileNotFoundException e) {
			LOGGER.severe("PROBLEM! File does not exist or permission denied:"
							+ e.getMessage());
			throw e;
		}

		catch (java.lang.NullPointerException e) {
			LOGGER.severe("Mu-calculus formula or mCRL2 is not well formed. ");
			throw e;

		} catch (RuntimeException e) {
			LOGGER.severe("Something went terribly wrong. Check for syntax errors in model or formula");
			throw e;

		} catch (Exception e) {
			LOGGER.severe("Something went terribly wrong. ");
			throw e;
		}
		return result;

	}
	
	public static String createActionSortVisual(Mymcrl2Visitor visitor) {
		StringBuffer result = new StringBuffer();
		result.append("act error ;\n");
		result.append("sort Action = struct ");
		StringBuffer actions = new StringBuffer();
		Hashtable<String, ArrayList<String>> actionsDict = visitor.actionsDict;

			Enumeration<String> keys = actionsDict.keys();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				ArrayList<String> dataTypes = actionsDict.get(key);
				result.append(key);
				actions.append("act " + key );
				if (dataTypes.size() > 0) {
					result.append("(");
					actions.append(":");
					int counter = 1;
					Iterator<String> iter = dataTypes.iterator();
					while (iter.hasNext()) {
						String dt = iter.next();
						result.append(key + "_" + "arg" + counter++ + ":" + dt);
						actions.append(dt);
						if (iter.hasNext()) {
							result.append(",");
							actions.append("#");
						}
					}
					result.append(")");
				}
				if (keys.hasMoreElements()) {
					result.append("\n | ");
				}
				actions.append(";\n");
			}	

		result.append(";\n");
		result.append("\n" + actions);
		return result.toString();
	}

	public static String createInitVisual() {
		StringBuffer result = new StringBuffer();
		result.append("init Monitor;");

		return result.toString();
	}
	
}