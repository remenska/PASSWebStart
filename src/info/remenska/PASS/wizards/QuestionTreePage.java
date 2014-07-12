package info.remenska.PASS.wizards;

import info.remenska.PASS.wizards.SelectDataSetDialog.ModelAction;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.LabelProvider;
//import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
//import org.eclipse.uml2.uml.Message;
//import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
//import org.eclipse.uml2.uml.Operation;
//import org.eclipse.uml2.uml.Parameter;
//import org.eclipse.uml2.uml.ParameterDirectionKind;
//import org.eclipse.uml2.uml.SendOperationEvent;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.dialogs.FilteredList;

public class QuestionTreePage extends WizardPage {
	private final static Logger LOGGER = Logger.getLogger("info.remenska.PASS");

	class MySelectionListener implements SelectionListener {
		public void widgetSelected(SelectionEvent e) {
			// ExpandBar contains: ExpandItem and Composite(contains:
			// Buttons))
			boolean isSelected = ((Button) e.getSource()).getSelection();
			if (isSelected) {

				List<TreeNode<String>> newQuestions = questionnaire
						.findTreeNode(((Button) e.getSource()).getText()).children;
				String selected = ((Button) e.getSource()).getText();
				// (1) find parent
				// (2) remove all children
				// (3) add this clicked one
				LOGGER.finer("SELECTED: " + selected);

				TreeNode<String> staticNode = questionnaire
						.findTreeNode(selected);
				LOGGER.finer("FOUND STATIC NODE: " + staticNode.data);

				TreeNode<String> staticParentNode = staticNode.parent;
				// LOGGER.finer("STATIC NODE PARENT: "+ staticParentNode.data);

				// (1)
				TreeNode<String> dynamicParentNode = dynamicQuestionnaire
						.findTreeNode(staticParentNode.data);
				// LOGGER.finer("DYNAMIC NODE PARENT: "+
				// dynamicParentNode.data);
				// LOGGER.finer("DYNAMIC PARENT HAS: "+
				// dynamicParentNode.children.size());

				// (2)
				dynamicParentNode.removeChildren();

				// (3)
				TreeNode<String> dynamicNode = dynamicParentNode.addChild(
						selected, false);

				Composite questionsHolder = ((Button) e.getSource())
						.getParent();

				// update the scope & behavior if it's decided with the answers
				// so far
				if (staticNode.isLeaf() && staticNode.getScope() != null)
					scope = staticNode.getScope();
				if (staticNode.isLeaf() && staticNode.getBehavior() != null)
					behavior = staticNode.getBehavior();

				if (scopeImage.get(staticNode) != null) {

					labelGraphicsHolder.setVisible(true);
					// TODO:
					 scopeGraphical = new Image(Display.getCurrent(),
					 this.getClass().getClassLoader().getResourceAsStream("images/ScopeTimelineView"
					 + scopeImage.get(staticNode)));
//					scopeGraphical = new Image(Display.getCurrent(),
//							scopeImage.get(staticNode));

					labelGraphicsHolder.getDisplay().asyncExec(new Runnable() {
						public void run() {

							Composite parent = labelGraphicsHolder.getParent();
							labelGraphicsHolder.setImage(scopeGraphical);
							labelGraphicsHolder.pack(true);
							getShell().pack();

							labelGraphicsHolder.redraw();
							labelGraphicsHolder.update();
						}
					});

				}

				// refresh enabled/disabled text fields for selection of events
				if (fieldMap.get(staticNode) != null) {
					for (Text ownedText : ownedTexts) {
						ownedText.setEnabled(false);
					}

					List<Text> fieldsToEnable = fieldMap.get(staticNode);
					for (Text field : fieldsToEnable) {
						field.setEnabled(true);
						field.pack(true);
					}

				}

				int oldOldHeight = questionsHolder.computeSize(SWT.DEFAULT,
						SWT.DEFAULT).y;
				// first remove existing expandBars. This means only answers
				// remain, no nested questions
				for (Control control : questionsHolder.getChildren()) {
					if (!(control instanceof Button)) {
						control.dispose();
					}
				}

				questionsHolder.layout();

				// dynamically create new questions.
				ExpandBar expandBarNewQuestions = new ExpandBar(
						questionsHolder, SWT.NONE);

				MyExpandListener myExpandListener = new MyExpandListener();
				expandBarNewQuestions.addExpandListener(myExpandListener);

				expandBarNewQuestions.setLayoutData(new GridData(GridData.FILL,
						GridData.FILL, true, true, 2, 1));

				int height = 0;
				ExpandItem prevQuest = null;
				// LOGGER.finer("NEW QUESTIONS: " + newQuestions);
				for (TreeNode<String> newQuestion : newQuestions) {
					Composite questionComposite = new Composite(
							expandBarNewQuestions, SWT.NONE);
					// LOGGER.finer("Added child, now: " +
					// questionnaireValidation.getLevel());
					GridLayout layout1 = new GridLayout(1, true);
					questionComposite.setLayout(layout1);

					ExpandItem question = new ExpandItem(expandBarNewQuestions,
							SWT.NONE, 0);
					question.setText((String) newQuestion.data);
					TreeNode<String> dynamicQuestion = dynamicNode.addChild(
							newQuestion.data, true);

					// LOGGER.finer("QUESTION INSERTED, TEXT:"+
					// question.getText());
					question.setControl(questionComposite);// Sets the control
															// that is shown
															// when the item is
															// expanded.

					// new answers for each question
					List<TreeNode<String>> answers = newQuestion.children;
					for (TreeNode<String> answer : answers) {
						Button label1 = new Button(questionComposite, SWT.RADIO);
						label1.setText((String) answer.data);
						label1.addSelectionListener(this);

						dynamicQuestion.addChild(answer.data, false);
					}
					question.setHeight(questionComposite.computeSize(
							SWT.DEFAULT, SWT.DEFAULT).y);
					height += questionComposite.computeSize(SWT.DEFAULT,
							SWT.DEFAULT).y;
					questionComposite.setVisible(true);
					questionComposite.layout();
					questionsHolder.layout();
				}

				// we need to adjust the (parent) ExpandItem's height

				ExpandBar expandBarParent = (ExpandBar) questionsHolder
						.getParent();
				ExpandItem[] expandItems = expandBarParent.getItems();
				ExpandItem theOneWeNeed = null;
				for (ExpandItem expandItem : expandItems) {
					if (expandItem.getControl().equals(questionsHolder))
						theOneWeNeed = expandItem;
				}
				questionsHolder.layout();

				// recalculate the height // <-- needs to be propagated to outer
				// levels
				if (theOneWeNeed != null) {
					int oldHeight = theOneWeNeed.getHeight();
					theOneWeNeed.setHeight(questionsHolder.computeSize(
							SWT.DEFAULT, SWT.DEFAULT).y);

				}

				recallibrateHeight(height, oldOldHeight, questionsHolder);
				questionsHolder.layout();

				LOGGER.fine("\n");
				LOGGER.fine("SCOPE: " + scope + "; BEHAVIOR: " + behavior);
				checkIfValid();

			}

		}

		private void recallibrateHeight(int height, int oldOldHeight,
				Composite questionsHolder) {
			// we need to propagate the height to all ExpandItem parents in the
			// hierarchy
			ExpandBar barHeigher = (ExpandBar) questionsHolder.getParent();
			ExpandItem[] items = barHeigher.getItems();
			ExpandItem itemExpanded = null;
			for (ExpandItem item : items) {
				if (item.getControl().equals(questionsHolder))
					itemExpanded = item;
			}
			ExpandItem theOneWeLookFor = null;
			Composite compositeControlled = (Composite) itemExpanded
					.getControl();
			while (itemExpanded != null) {
				theOneWeLookFor = null;
				ExpandBar itemParentBar = itemExpanded.getParent();
				Composite compositeParent = itemParentBar.getParent();
				if (compositeParent != null
						&& !itemParentBar.getParent().equals(parentLe)) {
					ExpandBar expandBarSuperParent = (ExpandBar) compositeParent
							.getParent();
					if (expandBarSuperParent != null) {
						ExpandItem[] allItemsOfSuperParent = expandBarSuperParent
								.getItems();

						for (ExpandItem item : allItemsOfSuperParent) {
							if (item.getControl().equals(compositeParent))
								theOneWeLookFor = item;
						}

						theOneWeLookFor.setHeight(theOneWeLookFor.getHeight()
								+ questionsHolder.computeSize(SWT.DEFAULT,
										SWT.DEFAULT).y - oldOldHeight);

						compositeParent.layout();

					}

				}
				itemExpanded = theOneWeLookFor;

			}

		}

		public void widgetDefaultSelected(SelectionEvent e) {
			widgetSelected(e);
		}
	}

	public MySelectionListener mySelectionListener;
	public TreeNode<String> questionnaire;
	public TreeNode<String> dynamicQuestionnaire;
	public static Label labelStartEvent, labelEndEvent;
	public static Text textStartEvent, textEndEvent;
	public static TraceLine trStartEvent, trEndEvent;

	public LinkedList<Text> ownedTexts;
	public static Label labelEventA, labelEventB, labelEventC, labelEventX;
	public static Text textEventA, textEventB, textEventC, textEventX;
	public static TraceLine trEventA, trEventB, trEventC, trEventX;

	public static String scope, behavior;
	public Label labelGraphicsHolder;
	public Image scopeGraphical;
	public static LinkedHashMap<TreeNode<String>, String> scopeImage;
	public static LinkedHashMap<TreeNode<String>, LinkedList<Text>> fieldMap;
	public static HashMap<Text, TraceLine> traceLineMap;

	// public static String
	// determinePrimitiveType(org.eclipse.uml2.uml.internal.impl.PrimitiveTypeImpl
	// typearg) {
	// // LOGGER.info("typeArg:"+typearg+";"+typearg.eIsProxy());
	// if (typearg.getName().equals("Integer"))
	// return "Int";
	// else if (typearg.getName().equals("Boolean"))
	// return "Bool";
	// else if (typearg.getName().equals("Natural"))
	// return "Nat";
	// else if (typearg.getName().equals("String"))
	// return "SortString";
	// else
	// return "Unknown %FIXME";
	//
	// }

	public static void fillTreeMap() throws IOException {

		// TODO:
//		String path = "/home/daniela/IBM/rationalsdp/workspace1/info.remenska.PASSWebStart/images/ScopeTimelineView/";
		 String path = "/";

		scopeImage.put(Questionnaire.answ12, path + "1.png");
		scopeImage.put(Questionnaire.answ11, null);
		scopeImage.put(Questionnaire.answ1111, path + "3.png");
		scopeImage.put(Questionnaire.answ111111, path + "3.png");
		scopeImage.put(Questionnaire.answ111112, path + "6.png");
		scopeImage.put(Questionnaire.answ1112, path + "2.png");
		scopeImage.put(Questionnaire.answ111211, path + "7.png");
		scopeImage.put(Questionnaire.answ111212, path + "8.png");
		scopeImage.put(Questionnaire.answ1113, path + "4.png");
		scopeImage.put(Questionnaire.answ11311, path + "4.png");
		scopeImage.put(Questionnaire.answ11312, path + "9.png");
		scopeImage.put(Questionnaire.answ1131211, path + "11.png"); // same as
																	// 5?
		scopeImage.put(Questionnaire.answ1131212, path + "10.png");
		scopeImage.put(Questionnaire.answ1131111, path + "12.png"); // same as
																	// 5?
		scopeImage.put(Questionnaire.answ1131112, path + "13.png");

		// (10,10) can just as well be (12 13)! how to determine?

		fieldMap.put(Questionnaire.answ11, new LinkedList<Text>());
		fieldMap.put(Questionnaire.answ1111,
				new LinkedList<Text>(Arrays.asList(textStartEvent)));
		fieldMap.put(Questionnaire.answ1112,
				new LinkedList<Text>(Arrays.asList(textEndEvent)));
		fieldMap.put(
				Questionnaire.answ1113,
				new LinkedList<Text>(Arrays
						.asList(textStartEvent, textEndEvent)));

		fieldMap.put(Questionnaire.answ12, new LinkedList<Text>());
		fieldMap.put(Questionnaire.aansw11,
				new LinkedList<Text>(Arrays.asList(textEventA)));
		fieldMap.put(Questionnaire.aansw12,
				new LinkedList<Text>(Arrays.asList(textEventA, textEventB)));
		fieldMap.put(
				Questionnaire.aansw13,
				new LinkedList<Text>(Arrays.asList(textEventA, textEventB,
						textEventC)));
		fieldMap.put(
				Questionnaire.aansw1312,
				new LinkedList<Text>(Arrays.asList(textEventA, textEventB,
						textEventC)));
		fieldMap.put(
				Questionnaire.aansw1312,
				new LinkedList<Text>(Arrays.asList(textEventA, textEventB,
						textEventC)));
		fieldMap.put(
				Questionnaire.aansw1312,
				new LinkedList<Text>(Arrays.asList(textEventA, textEventB,
						textEventC)));

		fieldMap.put(
				Questionnaire.aansw131112,
				new LinkedList<Text>(Arrays.asList(textEventA, textEventB,
						textEventC, textEventX)));
		fieldMap.put(
				Questionnaire.aansw131111,
				new LinkedList<Text>(Arrays.asList(textEventA, textEventB,
						textEventC)));

		trEventA = new TraceLine();
		trEventB = new TraceLine();
		trEventC = new TraceLine();
		trEventX = new TraceLine();
		trStartEvent = new TraceLine();
		trEndEvent = new TraceLine();
		traceLineMap.put(textEventA, trEventA);
		traceLineMap.put(textEventB, trEventB);
		traceLineMap.put(textEventC, trEventC);
		traceLineMap.put(textEventX, trEventX);
		traceLineMap.put(textStartEvent, trStartEvent);
		traceLineMap.put(textEndEvent, trEndEvent);

	}

	private static String createIndent(int depth) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			sb.append(' ');
			sb.append(' ');
		}
		return sb.toString();
	}

	public Composite parentLe = null;

	protected QuestionTreePage(String pageName, String description,
			TreeNode<String> questionnaire) {
		super(pageName);
		this.questionnaire = questionnaire;
		setTitle(pageName);
		setDescription(description);
		setPageComplete(false);
	}

	public void traverseQuestionnaire(TreeNode<String> questionnaireAman) {
		LOGGER.fine("TRAVERSAL-------");
		Iterator<TreeNode<String>> iter = dynamicQuestionnaire.iterator();
		while (iter.hasNext()) {
			TreeNode<String> el = iter.next();
			String ident = createIndent(el.getLevel());
			String hmmm = el.isQuestion ? "Q: " : "A: ";

			LOGGER.fine(el.getLevel() + ident + hmmm + el.data);
		}
	}

	public void checkIfValid() {
		boolean isValid = true;
		Iterator<TreeNode<String>> iter = dynamicQuestionnaire.iterator();
		while (iter.hasNext()) {
			TreeNode<String> el = iter.next();
			if (el.isQuestion)
				if (el.children.size() != 1 && el.isQuestion) {
					isValid = false;
					break;
				}
		}

		for (Text ownedText : ownedTexts) {
			if (ownedText.isEnabled()
					&& ownedText.getText().equals("double-click to select"))
				isValid = false;
		}
		if (isValid)
			setPageComplete(true);
		else
			setPageComplete(false);
	}

	public void addEventSlots(Composite parent, ExpandBar root,
			Listener operationListener) {

	}

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.FILL);
		parentLe = composite;

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;

		composite.setLayout(gridLayout);

		setControl(composite);
		scopeImage = new LinkedHashMap<TreeNode<String>, String>();
		fieldMap = new LinkedHashMap<TreeNode<String>, LinkedList<Text>>();
		ownedTexts = new LinkedList<Text>();
		traceLineMap = new HashMap<Text, TraceLine>();
		ExpandBar root = new ExpandBar(composite, SWT.V_SCROLL | SWT.H_SCROLL);
		;

		Listener operationListener = new Listener() {

			// @Override
			public void handleEvent(Event event) {

				SelectDataSetDialog dialog = new SelectDataSetDialog(getShell());
				dialog.setTitle("PASS Property ASSistant: Select an action");
				dialog.create();
				dialog.open();
				if (dialog.getResult() != null) {
					LOGGER.fine(dialog.getResult().toString());
					((Text) event.widget).setText(dialog.getResult()
							.getNameAction());
					ModelAction action = dialog.getResult();
					((Text) event.widget).pack();
					TraceLine trObj = new TraceLine();
					trObj.setOriginMessage(action.getNameAction());
					ArrayList<String> parameters = action.getArguments();
					LinkedList<String> paramsList = new LinkedList<String>();

					if (parameters.size() > 0) {
						int i = 1;
						for (String argument : parameters) {
							paramsList.add("arg" + i);
							trObj.parameterTypes.put("arg" + i++, argument);
						}
						trObj.setParameters(paramsList
								.toArray(new String[paramsList.size()]));
						trObj.setParamNames(paramsList
								.toArray(new String[paramsList.size()]));
					}

					dialog.close();
					checkIfValid();
					traceLineMap.put((Text) event.widget, trObj);
				}
			}
		};
		//
		addEventSlots(composite, root, operationListener);
		try {
			fillTreeMap();
		} catch (IOException e) {
			e.printStackTrace();
		}

		labelGraphicsHolder = new Label(composite, SWT.WRAP | SWT.BORDER);

		labelGraphicsHolder.setImage(null);

		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.verticalAlignment = SWT.TOP;
		labelGraphicsHolder.setLayoutData(gridData);
		labelGraphicsHolder.setVisible(false);

		composite.pack();

		mySelectionListener = new MySelectionListener();
		final MyExpandListener myExpandListener = new MyExpandListener();
		// root.setBackgroundImage(new
		// Image(display,"/home/daniela/Downloads/background.jpg"));
		ExpandItem question = new ExpandItem(root, SWT.NONE, 0);
		question.setText((String) questionnaire.data);
		root.addExpandListener(myExpandListener);
		Composite answersContainer = new Composite(root, SWT.NONE);
		dynamicQuestionnaire = new TreeNode<String>(this.questionnaire.data,
				true);
		GridLayout layout1 = new GridLayout(1, true);
		answersContainer.setLayout(layout1);
		answersContainer.setVisible(true);
		question.setControl(answersContainer); //
		// now the answers
		List<TreeNode<String>> deca = this.questionnaire.children;
		for (TreeNode dete : deca) {
			Button answer = new Button(answersContainer, SWT.RADIO);
			answer.setText((String) dete.data);

			answer.addSelectionListener(mySelectionListener);

			dynamicQuestionnaire.addChild((String) dete.data, false);
		}

		question.setHeight(answersContainer.computeSize(SWT.DEFAULT,
				SWT.DEFAULT).y);
		LOGGER.fine("Dynamic QUESTIONNAIRE: ");
		traverseQuestionnaire(dynamicQuestionnaire);
		PatternMuCalculusFormat.fill();
	}

	class MyExpandListener implements ExpandListener {

		public void itemCollapsed(ExpandEvent e) {
			// we need to propagate the height to all ExpandItem parents in the
			// hierarchy

			ExpandItem itemExpanded = (ExpandItem) e.item;
			ExpandItem theOneWeLookFor = null;
			Composite compositeControlled = (Composite) itemExpanded
					.getControl();
			int heightToRemove = compositeControlled.computeSize(SWT.DEFAULT,
					SWT.DEFAULT).y;
			while (itemExpanded != null) {
				theOneWeLookFor = null;

				ExpandBar itemParentBar = itemExpanded.getParent();
				compositeControlled = (Composite) itemExpanded.getControl();

				Composite compositeParent = itemParentBar.getParent();
				if (compositeParent != null
						&& !itemParentBar.getParent().equals(parentLe)) {
					ExpandBar expandBarSuperParent = (ExpandBar) compositeParent
							.getParent();
					if (expandBarSuperParent != null) {
						ExpandItem[] allItemsOfSuperParent = expandBarSuperParent
								.getItems();

						for (ExpandItem item : allItemsOfSuperParent) {
							if (item.getControl().equals(compositeParent))
								theOneWeLookFor = item;
						}

						theOneWeLookFor.setHeight(theOneWeLookFor.getHeight()
								- heightToRemove);

						compositeParent.layout();
						compositeControlled.layout();
					}

				}
				itemExpanded = theOneWeLookFor;

			}
		}

		public void itemExpanded(ExpandEvent e) {
			// we need to propagate the height to all ExpandItem parents in the
			// hierarchy
			ExpandItem itemExpanded = (ExpandItem) e.item;
			ExpandItem theOneWeLookFor = null;
			Composite compositeControlled = (Composite) itemExpanded
					.getControl();
			int heightToAdd = compositeControlled.computeSize(SWT.DEFAULT,
					SWT.DEFAULT).y;
			while (itemExpanded != null) {
				theOneWeLookFor = null;
				ExpandBar itemParentBar = itemExpanded.getParent();
				compositeControlled = (Composite) itemExpanded.getControl();
				if (itemParentBar.getParent() != null
						&& !itemParentBar.getParent().equals(parentLe)) {
					ExpandBar expandBarSuperParent = (ExpandBar) itemParentBar
							.getParent().getParent(); // BUG
					if (expandBarSuperParent != null) {
						ExpandItem[] allItemsOfSuperParent = expandBarSuperParent
								.getItems();

						for (ExpandItem item : allItemsOfSuperParent) {
							if (item.getControl().equals(
									itemParentBar.getParent()))
								theOneWeLookFor = item;
						}

						theOneWeLookFor.setHeight(theOneWeLookFor.getHeight()
								+ heightToAdd);

						itemParentBar.getParent().layout();
					}

				}
				itemExpanded = theOneWeLookFor;

			}
		}
	}
}

class TraceLine {
	public boolean isReply = false;
	public String className;
	public String[] parameters = null;
	public String[] returnParams = null;

	public String[] getParamNames() {
		return paramNames;
	}

	public void setParamNames(String[] paramNames) {
		this.paramNames = paramNames;
	}

	public String[] getReturnParamNames() {
		return returnParamNames;
	}

	public void setReturnParamNames(String[] returnParamNames) {
		this.returnParamNames = returnParamNames;
	}

	public String[] paramNames = null;
	public String[] returnParamNames = null;
	public LinkedHashMap<String, String> parameterTypes = new LinkedHashMap<String, String>();

	public String[] getReturnParams() {
		return returnParams;
	}

	public void setReturnParams(String[] returnParams) {
		this.returnParams = returnParams;
	}

	public String methodCall;
	public boolean isAsynchronous = false;
	String originMessage;

	public String getOriginMessage() {
		return originMessage;
	}

	public void setOriginMessage(String originMessage) {
		this.originMessage = originMessage;
		this.methodCall = originMessage;
	}

	public boolean isAsynchronous() {
		return isAsynchronous;
	}

	public void setAsynchronous(boolean isAsynchronous) {
		this.isAsynchronous = isAsynchronous;
	}

	public String getMethodCall() {
		return methodCall;
	}

	public void setMethodCall(String methodCall) {
		this.methodCall = methodCall;
	}

	public String[] getParameters() {
		return parameters;
	}

	public void setParameters(String[] parameters) {
		this.parameters = parameters;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String objectName;

	public String getClassNameSource() {
		return classNameSource;
	}

	public void setClassNameSource(String classNameSource) {
		this.classNameSource = classNameSource;
	}

	public String getObjectNameSource() {
		return objectNameSource;
	}

	public void setObjectNameSource(String objectNameSource) {
		this.objectNameSource = objectNameSource;
	}

	public String classNameSource;
	public String objectNameSource;

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public boolean isReply() {
		return isReply;
	}

	public void setIsReply(boolean isReply) {
		this.isReply = isReply;
	}

	public String toString() {
		StringBuffer tmp = new StringBuffer();

		if (parameters != null) {
			StringBuffer params = new StringBuffer(Arrays.toString(parameters));
			params = new StringBuffer(params.substring(1));
			params = new StringBuffer(params.substring(0, params.length() - 1));
			tmp.append(getMethodCall() + "(" + params + ")");

		} else
			tmp.append(getMethodCall());

		// }

		return tmp.toString();
	}

}
