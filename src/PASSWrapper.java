
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;

import info.remenska.PASS.monitor.mCRL2.Main;
import info.remenska.PASS.monitor.mCRL2.Mymcrl2Visitor;
import info.remenska.PASS.monitor.mCRL2.mcrl2Lexer;
import info.remenska.PASS.monitor.mCRL2.mcrl2Parser;
import info.remenska.PASS.wizards.CapturePropertyWizard;
import info.remenska.PASS.wizards.DisciplinedEnglishPage;
import info.remenska.PASS.wizards.SelectDataSetDialog;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.io.IOUtils;
import org.eclipse.swt.widgets.Label;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class PASSWrapper extends ApplicationWindow {
	public static Text modelFullPath;
//	public static String fileName;
	public static Label styledText;
//	public static LinkedList<String> actions;
    public static Hashtable<String, ArrayList<String>> actionsDict = new Hashtable<String, ArrayList<String>>();

	protected Control createContents(final Composite parent) {
	    getShell().setText("PASS Property ASSistant Web Start");
	    parent.setSize(450,150);

		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		
		styledText = new Label(composite, SWT.WRAP);

		styledText.setText("Before launching PASS, please select the working mCRL2 model.");
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		styledText.setLayoutData(gd);		
		
////		layout.set
//
//		GridData gridData = new GridData();
//		gridData.horizontalAlignment = GridData.BEGINNING;
//		gridData.verticalAlignment = SWT.TOP;
//
//		gridData.heightHint = 550;
//		gridData.widthHint = 650;
		Button buttonModel = new Button(composite, SWT.PUSH);
		buttonModel.setText("Browse...");
		final Button button = new Button(composite, SWT.PUSH);
		button.setText("Launch PASS!");
		button.setEnabled(false);
		
		buttonModel.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent event) {
		    	FileDialog dlg = new FileDialog(parent.getShell());
//		        dlg.setFilterPath(textDirectoryFormula.getText());

		        dlg.setText("Please select the original mCRL2 model");

		        // Customizable message displayed in the dialog
//		        dlg.setMessage("Please select the original mCRL2 model.");

		        // Calling open() will open and run the dialog.
		        // It will return the selected directory, or
		        // null if user cancels
		        String dir = dlg.open();
		        if (dir != null) {
		          // Set the text box to the new selection
		        	modelFullPath.setText(dir);
		        	modelFullPath.pack();
		        	
		        	Main main = new Main();
		        	// collect actions here
					InputStream ismcrl2;
					try {
						File mcrl2File = new File(dir);
						ismcrl2 = new FileInputStream(mcrl2File);
						DisciplinedEnglishPage.pathTemp = mcrl2File.getParent();
						DisciplinedEnglishPage.fileTemp = mcrl2File.getName();
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
					Enumeration<String> actionKeyEnum = Mymcrl2Visitor.actionsDict.keys();
					if(!actionKeyEnum.hasMoreElements()){
			        	button.setEnabled(false);
			        	styledText.setText("The model has no actions! Please try with a different one.");
			        	styledText.pack();
			        	composite.pack();
			        	parent.pack();

						// show some mesasge that the model has no actions; Disable PASS button
					} else{
						styledText.setText("Working mCRL2 model OK, please launch PASS!.");
			        	styledText.pack();
			        	composite.pack();
			        	parent.pack();
			        	button.setEnabled(true);
					}
//					while(actionKeyEnum.hasMoreElements()){
//						String act = actionKeyEnum.nextElement();
//						actions.add(act + Mymcrl2Visitor.actionsDict.get(act));
//					}
					
					SelectDataSetDialog.actionsDict =  Mymcrl2Visitor.actionsDict;
//		        	actions.add("One");
//		        	actions.add("Two");
//		        	actions.add("Three");
					} catch(org.antlr.v4.runtime.misc.ParseCancellationException e){
			        	styledText.setText("This is not a syntactically correct mCRL2 model, got an error parsing it. Please try again.");
			        	button.setEnabled(false);
			        	styledText.pack();
			        	composite.pack();
			        	parent.pack();
//						e.printStackTrace();
						
					} catch (FileNotFoundException e) {
						
						// TODO Auto-generated catch block
			        	styledText.pack();
			        	composite.pack();
			        	parent.pack();
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
			        	styledText.pack();
			        	composite.pack();
			        	parent.pack();
						e.printStackTrace();
					}
		        	
		        } else {
		        	// ERROR!!!
		        }
		      }
		    });
		
		modelFullPath = new Text(composite, SWT.LEFT);
		gd = new GridData();
		gd.horizontalSpan = 2;
		modelFullPath.setLayoutData(gd);		

		button.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
//				ReservationWizard wizard = new ReservationWizard();
				CapturePropertyWizard wizard = new CapturePropertyWizard();

				WizardDialog dialog = new WizardDialog(getShell(), wizard);
				dialog.setBlockOnOpen(true);
				dialog.create();
				int returnCode = dialog.open();
				if (returnCode == Dialog.OK)
					System.out.println("Bye");
				else
					System.out.println("Cancelled");
			}
		});
		return composite;
	}

	public PASSWrapper(Shell parentShell) {
		super(parentShell);

		
	}

	public static void main(String[] args) {
		PASSWrapper reservation = new PASSWrapper(null);
		reservation.setBlockOnOpen(true);
		reservation.open();
	}
	
	
}
