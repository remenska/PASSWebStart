import info.remenska.PASS.monitor.mCRL2.Main;
import info.remenska.PASS.monitor.mCRL2.Mymcrl2Visitor;
import info.remenska.PASS.monitor.mCRL2.mcrl2Lexer;
import info.remenska.PASS.monitor.mCRL2.mcrl2Parser;
import info.remenska.PASS.wizards.CapturePropertyWizard;
import info.remenska.PASS.wizards.DisciplinedEnglishPage;
import info.remenska.PASS.wizards.SelectDataSetDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.io.IOUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class PASSWrapper extends ApplicationWindow {
	private final static Logger LOGGER = Logger.getLogger("info.remenska.PASS");
	private static FileHandler fh = null;

	public static Text modelFullPath;
	public static Label styledText;
	public static Hashtable<String, ArrayList<String>> actionsDict = new Hashtable<String, ArrayList<String>>();

	protected Control createContents(final Composite parent) {

		try {
			String timeStamp = new SimpleDateFormat().format(new Date());
			int random = (int) (Math.random() * 10000000);
//			fh = new FileHandler("./log_%u.%g_" +timeStamp + "_" + random + ".log", 30000,4);  
			fh = new FileHandler("./" + "_" + random + ".log", false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		fh.setFormatter(new SimpleFormatter());
		LOGGER.addHandler(fh);
		LOGGER.setLevel(Level.FINEST);
		getShell().setText("PASS Property ASSistant Web Start");
		parent.setSize(450, 150);

		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);

		styledText = new Label(composite, SWT.WRAP);

		styledText
				.setText("Before launching PASS, please select the working mCRL2 model.");
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		styledText.setLayoutData(gd);

		Button buttonModel = new Button(composite, SWT.PUSH);
		buttonModel.setText("Browse...");
		final Button button = new Button(composite, SWT.PUSH);
		button.setText("Launch PASS!");
		button.setEnabled(false);

		buttonModel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				// if Cancel has been hit, actions should be cleared
				SelectDataSetDialog.actionsDict.clear();

				FileDialog dlg = new FileDialog(parent.getShell());

				dlg.setText("Please select the original mCRL2 model");

				// Calling open() will open and run the dialog.
				// It will return the selected directory, or
				// null if user cancels
				String dir = dlg.open();
				if (dir != null) {
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
								(CharStream) new ANTLRInputStream(
										initialStringmcrl2));
						CommonTokenStream tokensmcrl2 = new CommonTokenStream(
								lexermcrl2);
						mcrl2Parser parsermcrl2 = new mcrl2Parser(tokensmcrl2);
						parsermcrl2.setErrorHandler(new BailErrorStrategy());
						ParseTree treemcrl2 = parsermcrl2.start();

						// we're using this visitor just to collect action &&
						// argument types
						Mymcrl2Visitor visitormcrl2 = new Mymcrl2Visitor(
								tokensmcrl2);
						visitormcrl2.visit(treemcrl2);
						Enumeration<String> actionKeyEnum = Mymcrl2Visitor.actionsDict
								.keys();
						if (!actionKeyEnum.hasMoreElements()) {
							button.setEnabled(false);
							styledText
									.setText("The model has no actions! Please try with a different one.");
							styledText.pack();
							composite.pack();
							parent.pack();

							// show some mesasge that the model has no actions;
							// Disable PASS button
						} else {
							styledText
									.setText("Working mCRL2 model OK, please launch PASS!.");
							styledText.pack();
							composite.pack();
							parent.pack();
							button.setEnabled(true);
						}

						SelectDataSetDialog.actionsDict = Mymcrl2Visitor.actionsDict;

					} catch (org.antlr.v4.runtime.misc.ParseCancellationException e) {
						styledText
								.setText("This is not a syntactically correct mCRL2 model, got an error parsing it. Please try again.");
						LOGGER.warning("This is not a syntactically correct mCRL2 model, got an error parsing it. Please try again.");
						LOGGER.warning(e.getStackTrace().toString());
						button.setEnabled(false);
						styledText.pack();
						composite.pack();
						parent.pack();
						 e.printStackTrace();

					} catch (FileNotFoundException e) {

						// TODO Auto-generated catch block
						styledText.pack();
						composite.pack();
						parent.pack();
						LOGGER.warning("File not found: "
								+ e.getStackTrace().toString());
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						styledText.pack();
						composite.pack();
						parent.pack();
						e.printStackTrace();
					}

				} else {
				}
			}
		});

		modelFullPath = new Text(composite, SWT.LEFT);
		gd = new GridData();
		gd.horizontalSpan = 2;
		modelFullPath.setLayoutData(gd);

		button.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
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
