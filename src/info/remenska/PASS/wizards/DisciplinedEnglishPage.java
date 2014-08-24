package info.remenska.PASS.wizards;

import info.remenska.PASS.monitor.mCRL2.Main;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import java.util.logging.Logger;


public class DisciplinedEnglishPage  extends WizardPage  {
	private final static Logger LOGGER = Logger.getLogger("info.remenska.PASS"); 

	protected Composite aman;
	protected TreeMap<String,String> scope;
	protected TreeMap<String, String> behavior;
	static String eventA, eventB, eventC, eventX;
	static String startEvent, endEvent;
	public static StyledText styledText;
	public static StyledText textFormula;
	public static Text textDirectoryFormula;
	public static String pathTemp,fileTemp;
	protected DisciplinedEnglishPage(String pageName, String description) {
		super(pageName);
		setTitle(pageName);
		setDescription(description);
		setPageComplete(false);
	}
	
	public void fillScope(){
		scope = new TreeMap<String,String>();
		 startEvent = QuestionTreePage.textStartEvent.getText() ;
		 endEvent = QuestionTreePage.textEndEvent.getText();
		scope.put("Globally", "The required behavior must hold throughout the entire system execution, i.e., from the start until the end of any event sequence.");
		scope.put("After Q", "The behavior must hold in a restricted interval in the event sequence, and this interval has a starting delimiter " + "["+ startEvent + "]" + ". The behavior must hold from the first occurrence of " + "["+ startEvent + "]" + " until the end of the system execution. \nEven if " + "["+ startEvent + "]" + " occurs more than once before the end of the event sequence, only the first occurrence begins the restricted interval; later occurrences of "+  "["+ startEvent + "]" + " do not have an effect." );
		scope.put("After Q variant", "The behavior must hold in a restricted interval in the event sequence, and this interval has a starting delimiter " + "["+ startEvent + "]" + ". The behavior must hold from the last occurrence of " + "["+ startEvent + "]" + " until the end of the system execution. \nEven if " + "["+ startEvent + "]" + " occurs more than once before the end of the event sequence, only the last occurrence begins the restricted interval; previous occurrences of "+  "["+ startEvent + "]" + " do not have an effect." );
		scope.put("Before R", "The behavior must hold in a restricted interval in the event sequence, and this interval has a ending delimiter " + "["+ endEvent + "]" + ". The behavior is required to hold from the start of the event sequence through to the first occurrence of " + "["+ endEvent+ "]" +". " + "["+ endEvent + "]" +" is required to occur, and if it never occurs, then the behavior is not required to hold anywhere in the event sequence, i.e., system execution. ");
		scope.put("Before R variant", "The behavior must hold in a restricted interval in the event sequence, and this interval has a ending delimiter " + "["+ endEvent + "]" + ". The behavior is required to hold from the start of the event sequence through to the first occurrence of " + "["+ endEvent+ "]" +", if it ever occurs. " + "["+ endEvent + "]" +" is not required to occur, and if it never occurs, then the behavior is required to hold throughout the entire system execution, i.e., until the end of the execution. ");
		scope.put("After Q until R", "The behavior must hold in a restricted interval in the event sequence, and this interval has both a starting delimiter, " + "["+ startEvent+ "]" + ", and an ending delimiter " + "["+ endEvent + "]" + ". The behavior must hold from the first occurrence of " + "["+ startEvent + "]" + ", if it ever occurs, throughout the first subsequent occurrence of " + "["+ endEvent + "]" + ", if it ever occurs. \nEven if " + "["+ startEvent + "]" + " occurs more than once before the first occurence of " + "["+ endEvent + "]" + ", only the first of those occurences of " + "["+ startEvent + "]" + " potentially  begins the restricted interval; later occurrences of "+  "["+ startEvent + "]" + " do not have an effect. If an occurrence of " + "["+ startEvent + "]" + " is not followed by occurrence of " + "["+ endEvent + "]" + ", then the behavior is required to hold until the end of the event sequence." );
		scope.put("Between Q and R", "The behavior must hold in a restricted interval in the event sequence, and this interval has both a starting delimiter, " + "["+ startEvent+ "]" + ", and an ending delimiter " + "["+ endEvent + "]" + ". The behavior must hold from the first occurrence of " + "["+ startEvent + "]" + ", if it ever occurs, throughout the first subsequent occurrence of " + "["+ endEvent + "]" + ", if it ever occurs. \nEven if " + "["+ startEvent + "]" + " occurs more than once before the first occurence of " + "["+ endEvent + "]" + ", only the first of those occurences of " + "["+ startEvent + "]" + " potentially  begins the restricted interval; later occurrences of "+  "["+ startEvent + "]" + " do not have an effect. If an occurrence of " + "["+ startEvent + "]" + " is never followed by occurrence of " + "["+ endEvent + "]" + ", then the behavior is not required to hold for the remainder of the event sequence." );
		scope.put("After Q until R variant", "The behavior must hold in a restricted interval in the event sequence, and this interval has both a starting delimiter, " + "["+ startEvent+ "]" + ", and an ending delimiter " + "["+ endEvent + "]" + ". The behavior must hold from the last occurrence of " + "["+ startEvent + "]" + ", if it ever occurs, throughout the first subsequent occurrence of " + "["+ endEvent + "]" + ", if it ever occurs. \nEven if " + "["+ startEvent + "]" + " occurs more than once before the first occurence of " + "["+ endEvent + "]" + ", only the last occurrence begins the restricted interval; previous occurrences of "+  "["+ startEvent + "]" + " do not have an effect. If an occurrence of " + "["+ startEvent + "]" + " is not followed by occurrence of " + "["+ endEvent + "]" + ", then the behavior is required to hold until the end of the event sequence." );
		scope.put("Between Q and R variant", "The behavior must hold in a restricted interval in the event sequence, and this interval has both a starting delimiter, " + "["+ startEvent+ "]" + ", and an ending delimiter " + "["+ endEvent + "]" + ". The behavior must hold from the last occurrence of " + "["+ startEvent + "]" + ", if it ever occurs, throughout the first subsequent occurrence of " + "["+ endEvent + "]" + ", if it ever occurs. \nEven if " + "["+ startEvent + "]" + " occurs more than once before the first occurence of " + "["+ endEvent + "]" + ", only the last occurrence begins the restricted interval; previous occurrences of "+  "["+ startEvent + "]" + " do not have an effect. If an occurrence of " + "["+ startEvent + "]" + " is never followed by occurrence of " + "["+ endEvent + "]" + ", then the behavior is not required to hold for the remainder of the event sequence." );
		
	}
	
	public void fillBehavior(){
		behavior = new TreeMap<String, String>();
		 eventA = QuestionTreePage.textEventA.getText();
		 eventB = QuestionTreePage.textEventB.getText();
		 eventC = QuestionTreePage.textEventC.getText();
		 eventX = QuestionTreePage.textEventX.getText();
		behavior.put("Absence", "The event of interest for the required behavior is " + "[" +eventA + "]" + ". " + "["+eventA+"]" + " must never occur. ");
		behavior.put("ExistenceUnfair", "The event of interest for the required behavior is " + "["+ eventA + "]" + ". " + "["+eventA + "]" + " is required to occur one or more times.\nNo assumptions are made about fairness of the system scheduling (such as: all processes are allowed to make progress if they request to do so).");
		behavior.put("ExistenceFair", "The event of interest for the required behavior is " + "["+ eventA + "]" + ". " + "["+eventA + "]" + " is required to occur one or more times.\nIt is assumed that the system is fair, such that all processes are allowed to make progress if they request to do so.");
		behavior.put("Bounded Existence", "The event of interest for the required behavior is " + "["+ eventA + "]" + ". " + "["+eventA + "]" + " is required at most one time. ");
		behavior.put("Precedence", "The events of interest for the required behavior are " + "["+ eventA + "]" + " and " + "[" +eventB + "]" + ". " + "["+ eventB + "]" + " is not allowed to occur until after " + "[" +eventA + "]" + " occurs." + "["+ eventA + "]" + " is not required to occur and, if it does not occur, " + "["+ eventB + "]" + " is never allowed to occur.");
		behavior.put("Precedence variant", "The events of interest for the required behavior are " + "[" +eventA + "]" + " and " + "["+ eventB + "]" + ". " + "["+ eventB + "]" + " is not allowed to occur until after " + "["+eventA + "]" + " occurs." + "["+ eventA + "]" + " is required to occur whether or not " + "["+ eventB + "]" + " occurs.");
		behavior.put("ResponseUnfair", "The events of interest for the required behavior are " + "["+ eventA + "]" + " and " + "["+ eventB + "]" + ". If " + "["+ eventA + "]" + " occurs, " + "["+ eventB + "]" +" is required to occur subsequently. Event " + "["+ eventA + "]" + " is not required to occur.\nNo assumptions are made about fairness of the system scheduling (such as: all processes are allowed to make progress if they request to do so). ");
		behavior.put("ResponseFair", "The events of interest for the required behavior are " + "["+ eventA + "]" + " and " + "["+ eventB + "]" + ". If " + "["+ eventA + "]" + " occurs, " + "["+ eventB + "]" +" is required to occur subsequently. Event " + "["+ eventA + "]" + " is not required to occur. \nIt is assumed that the system is fair, such that all processes are allowed to make progress if they request to do so. ");
		behavior.put("Response variant Unfair", "The events of interest for the required behavior are " + "["+ eventA + "]" + " and " + "["+ eventB + "]" + ". If " + "["+ eventA + "]" + " occurs, " + "["+ eventB + "]" +" is required to occur subsequently. Event " + "["+ eventA + "]" + " is required to occur. \nNo assumptions are made about fairness of the system scheduling (such as: all processes are allowed to make progress if they request to do so). ");
		behavior.put("Response variant Fair", "The events of interest for the required behavior are " + "["+ eventA + "]" + " and " + "["+ eventB + "]" + ". If " + "["+ eventA + "]" + " occurs, " + "["+ eventB + "]" +" is required to occur subsequently. Event " + "["+ eventA + "]" + " is required to occur.\nIt is assumed that the system is fair, such that all processes are allowed to make progress if they request to do so. ");
		behavior.put("Precedence Chain 1", "The events of interest for the required behavior are " + " " + "["+ eventA + "]" + ", " + "["+ eventB + "]" + " and " + "["+ eventC + "]" + ". Event " + "["+ eventC + "]" + " is not allowed to occur until after " + "["+ eventA + "]" +" followed by event " + "["+ eventB + "]" + " occurs. The event chain " + "["+ eventA+ "]" +" followed by " + "["+ eventB + "]" + ", is not required to occur, and, if it does not occur, " + "["+ eventC + "]" + " is never allowed to occur.");
		behavior.put("Precedence Chain 2", "The events of interest for the required behavior are " + " " + "["+ eventA + "]" + ", " + "["+ eventB + "]" + " and " + "["+ eventC + "]" + ". Event " + "["+ eventB + "]" + " followed by " + "["+ eventC + "]" + " is not allowed to occur, until " + "["+ eventA  + "]" + " occurs. The event  " + "["+ eventA+ "]" + " is not required to occur, and, if does not occur, " + " the event chain: " + "["+ eventB + "]" + " followed by " + "["+ eventC + "]" + ", is never allowed to occur.");
		behavior.put("Response Chain 1", "The events of interest for the required behavior are " + " " + "["+ eventA + "]" + ", " + "["+ eventB + "]" + " and " + "["+ eventC + "]" + ". If " + "["+ eventA + "]" + " occurs, then " + "["+ eventB + "]" +" followed by " + "["+ eventC + "]" + " is required to occur subsequently. Event " + "["+ eventA + "]" + " is not required to occur. ");
		behavior.put("Response Chain 2", "The events of interest for the required behavior are " + " " + "["+ eventA + "]" + ", " + "["+ eventB + "]" + " and " + "["+ eventC + "]" + ". If " + "["+ eventA + "]" + " followed by " + "["+ eventB + "]" + " occurs, then " + "["+ eventC + "]" + " is required to occur subsequently. The event chain " + "["+ eventA + "]" +" followed by " + "["+ eventB + "]" + " is not required to occur. ");
		behavior.put("Constrained Response Chain 2", "The events of interest for the required behavior are " + " " + "["+ eventA + "]" + ", " + "["+ eventB + "]" + "," + "["+ eventC + "]" + ", and the exceptional event " + "["+ eventX + "]" +". If " + "["+ eventA + "]" + " followed by " + "["+ eventB + "]" + " occurs, without the event " + "["+ eventX + "]" + " occurring inbetween,  then " + "["+ eventC + "]" + "is required to occur subsequently. The event chain " + "["+ eventA + "]" +" followed by " + "["+ eventB + "]" + " is not required to occur. ");
		behavior.put("Universality", "The event of interest for the required behavior is " + "["+ eventA + "]" + ". " + "["+ eventA + "]" + " must always be possible to occur, at every step of the execution. This is useful for checking if an object state always has a certain value. \nThe events in such case are often getter methods, querying the state value. ");
	}
	
	
	public void setVisible(boolean visible) {

	    super.setVisible(visible);

	    if (visible) {
	        onEnterPage();
//			setPageComplete(true);
	    }
	 }
	
	void onEnterPage()
	{
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.verticalAlignment = SWT.TOP;
		gridData.verticalSpan = 5;
		gridData.heightHint = 450;
		gridData.widthHint = 550;
//		gridData.horizontalAlignment = GridData.GRAB_HORIZONTAL;
//		gridData.verticalAlignment = GridData.GRAB_VERTICAL;
//		gridData.se
//		StyledText styledText = new StyledText(aman, SWT.WRAP);
//		styledText.setLineJustify(0, 1, true);
//		styledText.setEditable(false);
//		styledText.setLineAlignment(6, 1, SWT.RIGHT);
//		styledText.setWordWrap(true);
//		styledText.setLayoutData(gridData); 
//		styledText.setText(((BehaviorQuestionTreePage)this.getPreviousPage()).textEventA.getText());
	}

	public static void boldify(String originalText, String event, int style){
		String newOrig = originalText;

		int index = newOrig.indexOf(event);
		int prevIndex = 0;
		String orig = newOrig; 
		while (index !=-1 ){
			orig = newOrig;
			
			StyleRange styleRange = new StyleRange();
			styleRange.start =  prevIndex + index;
			styleRange.length = event.length() ;
			styleRange.fontStyle = style;
			styledText.setStyleRange(styleRange);
			
			newOrig = orig.substring(index+event.length(), orig.length());
			prevIndex = prevIndex + index + event.length();
			index = newOrig.indexOf(event);
		}

	}
	
	public void createControl(Composite parent) {
		setPageComplete(true);
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		setControl(composite);
		GridData gridData = new GridData();
		gridData.verticalAlignment = SWT.TOP;
		gridData.verticalSpan = 2;
		gridData.heightHint = 300;
		gridData.widthHint = 860;

		styledText = new StyledText(composite, SWT.WRAP);
	
		styledText.setLineJustify(0, 1, true);
		styledText.setEditable(false);
		styledText.setWordWrap(true);
		styledText.setLayoutData(gridData); 
		fillScope();
		fillBehavior();
		String fullText = "SCOPE: \n" + scope.get(QuestionTreePage.scope) + "\n\nBEHAVIOR: \n" + behavior.get(QuestionTreePage.behavior);
		String pleaseReview = "\n\nClicking \"Finish\" will generate the Sequence Diagram, mu-calculus formula, as well as the monitoring process (when applicable) matching the elucidated property. ";
		styledText.setText(fullText + pleaseReview);

		boldify(fullText, "[" + startEvent +"]", SWT.BOLD);
		boldify(fullText, "[" + endEvent + "]", SWT.BOLD);
		boldify(fullText, "[" + eventA + "]", SWT.BOLD);
		boldify(fullText, "[" + eventB + "]",SWT.BOLD);
		boldify(fullText, "[" + eventC + "]", SWT.BOLD);
		boldify(fullText, "[" + eventX + "]", SWT.BOLD);
		boldify(fullText, "SCOPE", SWT.BOLD); boldify(fullText, "BEHAVIOR", SWT.BOLD);
		boldify(fullText + pleaseReview, pleaseReview, SWT.ITALIC);

		// this if there is an event/message with at least one parameter
		final Label parameterValuesText = new Label(composite,  SWT.NONE);
		parameterValuesText.setText("Assign parameter values to events/message exchanges:");
//		textFormula = new StyledText(composite, SWT.BORDER | SWT.WRAP);
//		gridData = new GridData();
//		gridData.horizontalSpan = 2;
//		gridData.verticalSpan = 2;
		GridData gd = new GridData();
		gd.verticalAlignment = SWT.TOP;
//		gd.horizontalSpan = 2;
		parameterValuesText.setLayoutData(gd);
		
		
		final Composite parametersComposite = new Composite(composite, SWT.TOP);
		parametersComposite.setVisible(true);
		GridLayout gl = new GridLayout();
//	    gl.numColumns = 2;
		gd = new GridData();
		gd.verticalAlignment = SWT.TOP;
		parametersComposite.setLayoutData(gd);
	    parametersComposite.setLayout(gl);
		final LinkedHashMap<Text, String> parameterTypes = new LinkedHashMap<Text,String>(); 

	    final Set<Text> traceLines = QuestionTreePage.traceLineMap.keySet();
	    final LinkedList<Text> traceLinesToChange = new LinkedList<Text>();
//	    final LinkedList<TraceLine> traceLinesList = new LinkedList<TraceLine>();
	    for(Text tl:traceLines){
	    	if(tl.isEnabled() && !tl.getText().equals("double-click to select")){
	    		Group eventInfo = new Group(parametersComposite, SWT.TOP);
	    		eventInfo.setText(QuestionTreePage.traceLineMap.get(tl).getOriginMessage());
	    		GridLayout gridLayout = new GridLayout();
	    		if(QuestionTreePage.traceLineMap.get(tl).isReply()){
	    			if(QuestionTreePage.traceLineMap.get(tl).getReturnParams()!=null){
//	    	    		traceLinesList.add(QuestionTreePage.traceLineMap.get(tl));
	    				traceLinesToChange.add(tl);
	    				gridLayout.numColumns = QuestionTreePage.traceLineMap.get(tl).getReturnParams().length;
		    			eventInfo.setLayout(gridLayout);
	    				String[] params = QuestionTreePage.traceLineMap.get(tl).getReturnParams();
	    				for(String param:params){
	    					final Text argument = new Text(eventInfo, SWT.BORDER);
	    					argument.setText(param);
	    					parameterTypes.put(argument, QuestionTreePage.traceLineMap.get(tl).parameterTypes.get(param));
	    					gd = new GridData();
	    					gd.verticalAlignment = SWT.TOP;
	    					argument.setLayoutData(gd);
	    				}
	    			}else{
	    				eventInfo.setLayout(gridLayout);
	    				final Label noParams = new Label(eventInfo, SWT.NONE);
	    				noParams.setText("No parameters...");
	    				gd = new GridData();
	    				gd.verticalAlignment = SWT.TOP;
	    				noParams.setLayoutData(gd);	    			
	    			}
	    		}
	    		else{
	    			if( QuestionTreePage.traceLineMap.get(tl).getParameters()!=null){
//	    	    		traceLinesList.add(QuestionTreePage.traceLineMap.get(tl));
	    				traceLinesToChange.add(tl);
	    				gridLayout.numColumns = QuestionTreePage.traceLineMap.get(tl).getParameters().length;
		    			eventInfo.setLayout(gridLayout);
	    				String[] params = QuestionTreePage.traceLineMap.get(tl).getParameters();
	    				for(String param:params){
	    					final Text argument = new Text(eventInfo, SWT.BORDER);
	    					argument.setText(param);
	    					parameterTypes.put(argument, QuestionTreePage.traceLineMap.get(tl).parameterTypes.get(param));
	    					gd = new GridData();
	    					argument.setLayoutData(gd);
	    				}
	    			}else{
	    				eventInfo.setLayout(gridLayout);
	    				final Label noParams = new Label(eventInfo, SWT.NONE);
	    				noParams.setText("No parameters...");
	    				gd = new GridData();
	    				noParams.setLayoutData(gd);	    			
	    			}
	    		}

	    	} 
	    }
	    
	    if(traceLinesToChange.size()!=0){
	    final Button setThem = new Button(parametersComposite, SWT.PUSH);
	    setThem.setText("Assign them!");

	    setThem.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent event) {
		  		StringBuffer quantifiers = new StringBuffer();
		  	
		    	  Control[] events =  parametersComposite.getChildren();
		    	  
		    	  for(Text tl:traceLinesToChange){
		    		  for(Control group:events){
		    			  if(group instanceof Group && QuestionTreePage.traceLineMap.get(tl).getMethodCall()!=null){
		    				  if(QuestionTreePage.traceLineMap.get(tl).getMethodCall().equals(((Group)group).getText()) 
		    						  && 
		    						  (QuestionTreePage.traceLineMap.get(tl).getParameters()!=null 
		    						  || 
		    						  QuestionTreePage.traceLineMap.get(tl).getReturnParams()!=null
		    						  )
		    					){
			    				  TraceLine toChangeTL = QuestionTreePage.traceLineMap.get(tl);
			    				  Control[] argumentsText = ((Group)group).getChildren();
			    				  String[] argsString = new String[argumentsText.length];

		    					  String[] paramNames;
		    					  if (toChangeTL.isReply)
		    						  paramNames = toChangeTL.getReturnParamNames();
		    					  else
		    						  paramNames = toChangeTL.getParamNames(); 
		    					  
			    				  int i=0;
			    				  for(Control argsText:argumentsText){
			    					  

			    						if (QuestionTreePage.behavior.equals("Universality")) { // special treatment 
			    							argsString[i] = ((Text)argsText).getText();
			    							quantifiers.insert(0,"forall " + paramNames[i] + ":" + parameterTypes.get((Text)argsText) +".val( " + paramNames[i] + "!= " + argsString[i] + "). ");
			    							i++;
			    							if(i == argumentsText.length){
			    								quantifiers = new StringBuffer(quantifiers.substring(0, quantifiers.length()-2));
			    								quantifiers.append(" => ");
			    							}
			    				  		 
			    					  }else{
//			    							 for(String paramName:paramNames){
			    								 if(((Text)argsText).getText().equals("*"))	{
			    									 if(quantifiers.indexOf(paramNames[i])!=-1){ //that parameter name already exists, add some randomness in the name
			    										 int random = (int )(Math.random() * 100 + 1);
			    										 quantifiers.append("forall " + paramNames[i] + random + ":" + parameterTypes.get((Text)argsText) +". ");
						    							 argsString[i] = paramNames[i] + random ;	 
			    									 }else {
			    										 quantifiers.append("forall " + paramNames[i] + ":" + parameterTypes.get((Text)argsText) +". ");
						    							 argsString[i] = paramNames[i];	 
			    									 }
					    						 }else
					    							 argsString[i] = ((Text)argsText).getText(); //new argument values taken from text fields	  
				    							 
//					    					  }
			    							 i++;
			    				  		}
			    				  }
			    				  if(toChangeTL.isReply){
			    					  // if it's a value, we don't need a quantifier, otherwise we need to prepend
			    					  // the formula with forall nameParam:DomainParam
			    					  
			    					  toChangeTL.setReturnParams(argsString);

			    				  }
			    				  else {
			    					  
			    					  toChangeTL.setParameters(argsString);
			    					  
			    				  }
			    				  
			    				  QuestionTreePage.traceLineMap.put(tl, toChangeTL);
			    				  
			    			  }
		    			  }
		    			 
		    			  		
		    		  }
		    	   
		    		  //and again
		      }	
		    			StringBuffer modifiedBuffer = new StringBuffer();
		    			modifiedBuffer.append(PatternMuCalculusFormat.patterns.get(QuestionTreePage.scope).get(QuestionTreePage.behavior));
		    			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#Q#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textStartEvent).toString()));
		    			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#R#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEndEvent).toString()));
		    			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#P#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventA).toString()));
		    			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#S#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventB).toString()));

		    			if (QuestionTreePage.behavior.equals("Precedence Chain 1")) {
		    				modifiedBuffer = new StringBuffer();
		    				modifiedBuffer.append(PatternMuCalculusFormat.patterns.get(QuestionTreePage.scope).get(QuestionTreePage.behavior));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#Q#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textStartEvent).toString()));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#R#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEndEvent).toString()));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#S#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventA).toString()));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#T#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventB).toString()));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#P#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventC).toString()));


		    			} else if(QuestionTreePage.behavior.equals("Precedence Chain 2")){
		    				
		    				modifiedBuffer = new StringBuffer();
		    				modifiedBuffer.append(PatternMuCalculusFormat.patterns.get(QuestionTreePage.scope).get(QuestionTreePage.behavior));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#Q#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textStartEvent).toString()));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#R#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEndEvent).toString()));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#P#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventA).toString()));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#S#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventB).toString()));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#T#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventC).toString()));

		    			}	else if(QuestionTreePage.behavior.equals("Response Chain 1")) {
		    				modifiedBuffer = new StringBuffer();
		    				modifiedBuffer.append(PatternMuCalculusFormat.patterns.get(QuestionTreePage.scope).get(QuestionTreePage.behavior));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#Q#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textStartEvent).toString()));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#R#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEndEvent).toString()));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#S#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventA).toString()));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#T#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventB).toString()));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#P# ", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventC).toString()));

		    			}	else if(QuestionTreePage.behavior.equals("Response Chain 2")){
		    				modifiedBuffer = new StringBuffer();
		    				modifiedBuffer.append(PatternMuCalculusFormat.patterns.get(QuestionTreePage.scope).get(QuestionTreePage.behavior));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#Q#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textStartEvent).toString()));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#R#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEndEvent).toString()));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#P# ", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventA).toString()));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#S#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventB).toString()));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#T#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventC).toString()));

		    			}
		    			else if(QuestionTreePage.behavior.equals("Constrained Response Chain 2")){
		    				modifiedBuffer = new StringBuffer();
		    				modifiedBuffer.append(PatternMuCalculusFormat.patterns.get(QuestionTreePage.scope).get(QuestionTreePage.behavior));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#Q#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textStartEvent).toString()));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#R#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEndEvent).toString()));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#P# ", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventA).toString()));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#S#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventB).toString()));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#T#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventC).toString()));
		    				modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#Z#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventX).toString()));

		    			}
		    			modifiedBuffer = new StringBuffer(quantifiers + "" + modifiedBuffer);
		    			textFormula.setText(modifiedBuffer.toString());
		    		  //END and again
		    	  
		      }
		    });
	    
	    }

		final Label note = new Label(parametersComposite, SWT.NONE);
		FontData[] fD = note.getFont().getFontData();
		fD[0].setHeight(9);
		note.setFont( new Font(Display.getCurrent(),fD[0]));
	    note.setText("note: use a wildcard \"*\" to test the property on all possible \nparameter values from the parameter domain. ");

		
		final Label labelFormula = new Label(composite,  SWT.NONE);
		labelFormula.setText("The resulting mu-calculus formula:");
		gd = new GridData();
		gd.horizontalSpan = 2;

		labelFormula.setLayoutData(gd);
		

	    
		textFormula = new StyledText(composite, SWT.BORDER | SWT.WRAP);
//		gridData = new GridData();
//		gridData.horizontalSpan = 2;
//		gridData.verticalSpan = 2;
		gd = new GridData();
		gd.horizontalSpan = 2;
		gd.verticalAlignment = SWT.TOP;
		gd.heightHint = 150;
		gd.widthHint = 650;
		textFormula.setWordWrap(true);
		textFormula.setEditable(true);
		textFormula.setLineJustify(0, 1, true);
		textFormula.setLayoutData(gd);
		
		PatternMuCalculusFormat.fill();
		StringBuffer modifiedBuffer = new StringBuffer();
		modifiedBuffer.append(PatternMuCalculusFormat.patterns.get(QuestionTreePage.scope).get(QuestionTreePage.behavior));
		LOGGER.fine("OriginalFormula: " + modifiedBuffer);

		modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#Q#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textStartEvent).toString())+" ");
		modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#R#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEndEvent).toString())+" ");
		modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#P# ", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventA).toString())+" ");
		modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#S#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventB).toString())+ " ");

		if (QuestionTreePage.behavior.equals("Precedence Chain 1")) {
			modifiedBuffer = new StringBuffer();
			modifiedBuffer.append(PatternMuCalculusFormat.patterns.get(QuestionTreePage.scope).get(QuestionTreePage.behavior));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#Q#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textStartEvent).toString()));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#R#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEndEvent).toString()));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#S#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventA).toString()));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#T#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventB).toString()));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#P# ", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventC).toString()));


		} else if(QuestionTreePage.behavior.equals("Precedence Chain 2")){
			
			modifiedBuffer = new StringBuffer();
			modifiedBuffer.append(PatternMuCalculusFormat.patterns.get(QuestionTreePage.scope).get(QuestionTreePage.behavior));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#Q#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textStartEvent).toString()));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#R#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEndEvent).toString()));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#P# ", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventA).toString()));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#S#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventB).toString()));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#T#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventC).toString()));

		}	else if(QuestionTreePage.behavior.equals("Response Chain 1")) {
			modifiedBuffer = new StringBuffer();
			modifiedBuffer.append(PatternMuCalculusFormat.patterns.get(QuestionTreePage.scope).get(QuestionTreePage.behavior));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#Q#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textStartEvent).toString()));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#R#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEndEvent).toString()));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#S#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventA).toString()));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#T#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventB).toString()));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#P# ", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventC).toString()));

		}	else if(QuestionTreePage.behavior.equals("Response Chain 2")){
			modifiedBuffer = new StringBuffer();
			modifiedBuffer.append(PatternMuCalculusFormat.patterns.get(QuestionTreePage.scope).get(QuestionTreePage.behavior));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#Q#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textStartEvent).toString()));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#R#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEndEvent).toString()));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#P# ", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventA).toString()));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#S#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventB).toString()));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#T#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventC).toString()));

		}
		else if(QuestionTreePage.behavior.equals("Constrained Response Chain 2")){
			modifiedBuffer = new StringBuffer();
			modifiedBuffer.append(PatternMuCalculusFormat.patterns.get(QuestionTreePage.scope).get(QuestionTreePage.behavior));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#Q#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textStartEvent).toString()));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#R#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEndEvent).toString()));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#P# ", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventA).toString()));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#S#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventB).toString()));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#T#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventC).toString()));
			modifiedBuffer = new StringBuffer(modifiedBuffer.toString().replaceAll("#Z#", " "+QuestionTreePage.traceLineMap.get(QuestionTreePage.textEventX).toString()));

		}
		
		textFormula.setText(modifiedBuffer.toString());
		final Label labelDirectoryFormula = new Label(composite, SWT.NONE);
		if (PatternMuCalculusFormat.patternsMonitorable.get(QuestionTreePage.scope).get(QuestionTreePage.behavior).booleanValue()){
			LOGGER.fine("Monitorable...");
			labelDirectoryFormula.setText("Select a location for the target mCRL2 model that will contain the monitor code:");
			GridData data = new GridData();
		    data.horizontalSpan = 2;
		    labelDirectoryFormula.setLayoutData(data);


			Button button = new Button(composite, SWT.PUSH);
			button.setText("Browse...");
			button.addSelectionListener(new SelectionAdapter() {
			      public void widgetSelected(SelectionEvent event) {
			    	DirectoryDialog dlg = new DirectoryDialog(getShell());
			        dlg.setFilterPath(textDirectoryFormula.getText());

			        dlg.setText("Please select a location for the target mCRL2 model that will contain the monitor code.");

			        String dir = dlg.open();
			        if (dir != null) {
			          // Set the text box to the new selection
			        	textDirectoryFormula.setText(dir);
			        	textDirectoryFormula.pack();
			        	LOGGER.fine("Enabling Finish button...");
			        	setPageComplete(true);
			    		LOGGER.fine("Is page complete?:" + isPageComplete());
			        	composite.pack();
			    		
			        } else{
			        	LOGGER.fine("Disabling Finish button...");
			    		setPageComplete(false);
			    		LOGGER.fine("Is page complete?:" + isPageComplete());

			        }
			      }
			    });
			data = new GridData();
		    data.horizontalSpan = 2;

			button.setLayoutData(data);
			

			textDirectoryFormula = new Text(composite, SWT.LEFT);
			textDirectoryFormula.setText(pathTemp);
			gd = new GridData();
			gd.horizontalSpan = 2;
			textDirectoryFormula.setLayoutData(gd);
			
		}
		else {
		    GridData  data = new GridData();
		    data.horizontalSpan = 2;

		    labelDirectoryFormula.setLayoutData(data);
			labelDirectoryFormula.setText("The property is not monitorable. ");
			if(textDirectoryFormula!=null)
			textDirectoryFormula.setEnabled(false);
		}
		
		
	
	}

}
