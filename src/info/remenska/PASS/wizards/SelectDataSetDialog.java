package info.remenska.PASS.wizards;

import info.remenska.PASS.monitor.mCRL2.Mymcrl2Visitor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * Abstract class for all dialogs to select an UniDataSet entry from a table
 * 
 * @author Gerd Bartelt
 */
public class SelectDataSetDialog extends Dialog {
	public static Hashtable<String, ArrayList<String>> actionsDict = new Hashtable<String, ArrayList<String>>();

	private ModelAction result;

	private class MyContentProvider implements IStructuredContentProvider {
		private ModelAction[] elements;

		public ModelAction[] getElements(Object inputElement) {
			return elements;
		}

		public void dispose() {

		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			this.elements = (ModelAction[]) newInput;
		}
	}

	public class ModelAction {
		private String nameAction;
		private ArrayList<String> arguments;

		public String getNameAction() {
			return nameAction;
		}

		public void setNameAction(String nameAction) {
			this.nameAction = nameAction;
		}


		public ArrayList<String> getArguments() {
			return arguments;
		}

		public void setArguments(ArrayList<String> arguments) {
			this.arguments = arguments;
		}

		// public int counter;

		public ModelAction(String nameAction) {
			this.nameAction = nameAction;
		}

		public String toString() {
			if(this.arguments.size()!=0)
				return this.nameAction + ":" + this.arguments;
			else
				return this.nameAction;
		}
		// TODO: change to this:
		// //i.e uncomment
		// public String toString(){
		// return nameAction + arguments.toString();
		// }
	}

	protected TableViewer tableViewer;
	protected TableColumnLayout tableColumnLayout;
	protected String title = "";

	protected SelectDataSetDialog(Shell parentShell) {
		super(parentShell);
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public SelectDataSetDialog(String title) {
		this(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		this.title = title;
	}

	public void cancelPressed() {
		result = null;
		this.close();
	}

	protected Control createDialogArea(Composite parent) {

		Composite top = (Composite) super.createDialogArea(parent);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(top);

		this.getShell().setText(title);

		Composite searchComposite = new Composite(top, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(searchComposite);
		GridDataFactory.fillDefaults().grab(true, false)
				.align(SWT.END, SWT.CENTER).applyTo(searchComposite);

		// Define the SWT layout
		Composite tableComposite = new Composite(top, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(tableComposite);

		// Define the layout of the table
		tableColumnLayout = new TableColumnLayout();
		tableComposite.setLayout(tableColumnLayout);

		// Create the jface table viewer
		tableViewer = new TableViewer(tableComposite, SWT.BORDER
				| SWT.FULL_SELECTION);
		tableViewer.getTable().setLinesVisible(true);
		tableViewer.getTable().setHeaderVisible(false);
		tableViewer.setLabelProvider(new LabelProvider());
		tableViewer.setContentProvider(new MyContentProvider());
		tableViewer.setUseHashlookup(true);

		ModelAction[] model = createModel();
		tableViewer.setInput(model);

		tableViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {

					/**
					 * Selection Changed method
					 * 
					 * @param event
					 */
					public void selectionChanged(SelectionChangedEvent event) {
						ISelection selection = tableViewer.getSelection();
						if (selection != null
								&& selection instanceof IStructuredSelection) {
							Object obj = ((IStructuredSelection) selection)
									.getFirstElement();
							result = (ModelAction) obj;
							System.out.println(obj);
						}
					}

				});

		tableViewer.addDoubleClickListener(new IDoubleClickListener() {

			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = tableViewer.getSelection();
				if (selection != null
						&& selection instanceof IStructuredSelection) {
					Object obj = ((IStructuredSelection) selection)
							.getFirstElement();
					result = (ModelAction) obj;
					System.out.println(obj);
				}
				close();
			}
		});

		return top;
	}

	private ModelAction[] createModel() {
		ModelAction[] elements = new ModelAction[actionsDict.keySet().size()];
		Enumeration<String> actionKeyEnum = Mymcrl2Visitor.actionsDict.keys();
		int i = 0;
		while (actionKeyEnum.hasMoreElements()) {
			String act = actionKeyEnum.nextElement();
			elements[i] = new ModelAction(act);
			elements[i].setArguments(actionsDict.get(act));
			i++;
			// show some mesasge that the model has no actions; Disable PASS
			// button
		}
		return elements;
	}

	protected Point getInitialSize() {
		return new Point(400, 450);
	}

	public ModelAction getResult() {
		return result;
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
	}
}