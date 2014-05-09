package info.remenska.PASS.wizards;

import info.remenska.PASS.monitor.mCRL2.Main;

import java.io.FileNotFoundException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.graphics.Rectangle;
import java.util.logging.Logger;

public class CapturePropertyWizard extends Wizard {
	private final static Logger LOGGER = Logger.getLogger("info.remenska.PASS"); 

	PropertySpecificationScopes propertyScopes;
	QuestionTreePage scopeQuestionTreePage;
	QuestionTreePage behaviorQuestionTreePage;
	DisciplinedEnglishPage disciplinedEnglishPage;
	public void addPages() {
		propertyScopes = new PropertySpecificationScopes("Introduction",
				"Property Specification Scopes");
		addPage(propertyScopes);

		scopeQuestionTreePage = new ScopeQuestionTreePage(
				"Scope Question Tree View",
				"Please answer the following questions regarding the scope of the property:",
				Questionnaire.scopeQuestionTree);
		addPage(scopeQuestionTreePage);
		behaviorQuestionTreePage = new BehaviorQuestionTreePage(
				"Behavior Question Tree View",
				"Please answer the following questions regarding the behavior of the property:",
				Questionnaire.behaviorQuestionTree);
		addPage(behaviorQuestionTreePage);
		// disciplinedEnglishPage = new
		// DisciplinedEnglishPage("Disciplined English Summary: ",
		// "Please review the collected information regarding the property.");
		// addPage(disciplinedEnglishPage);
		this.getShell().setMaximized(true);
		this.getShell().setFocus();
		// this.getShell().pack();
		// this.getShell().setMaximized(true);
		Rectangle clientArea = this.getShell().getClientArea();
		this.getShell().setSize(clientArea.width, clientArea.height);

		// this.getShell().pack();
	}

	@Override
	public boolean performFinish() {
		Main main = new Main();
		String resultModel = new String();
		// generate the model with the monitor
		try {
			if(DisciplinedEnglishPage.textDirectoryFormula!=null && DisciplinedEnglishPage.textDirectoryFormula.isEnabled()){
			resultModel = main.generateMonitor(new String[] {
					DisciplinedEnglishPage.pathTemp + "/" + DisciplinedEnglishPage.fileTemp,
					DisciplinedEnglishPage.textFormula.getText(), DisciplinedEnglishPage.textDirectoryFormula.getText() + "/" + DisciplinedEnglishPage.fileTemp, "false"});
			
			MessageDialog dialog = new MessageDialog(getShell(),
					"Model with the monitor has been generated", null,
					"The new model is written at: \n" + resultModel,
					MessageDialog.INFORMATION, new String[] { "OK" }, 0);
			int result = dialog.open();

			
			}
		} catch (FileNotFoundException e2) {
			MessageDialog dialog = new MessageDialog(getShell(),
					"Generating model, almost there...", null,
					"PROBLEM! Directory does not exist or permission denied:\n"
							+ e2.getMessage(), MessageDialog.INFORMATION,
					new String[] { "OK" }, 0);
			int result = dialog.open();
			LOGGER.severe("PROBLEM! Directory does not exist or permission denied:"
							+ e2.getMessage());
			return false;

		} catch (NullPointerException e2) {
			MessageDialog dialog = new MessageDialog(getShell(),
					"Generating model, almost there...", null,
					"PROBLEM! Mu-calculus formula or mCRL2 is not well formed, or property not monitorable."
							+ e2.getMessage(), MessageDialog.INFORMATION,
					new String[] { "OK" }, 0);
			int result = dialog.open();

			LOGGER.severe("Mu-calculus formula or mCRL2 is not well formed. " + e2.getStackTrace());
			return false;
		} catch (RuntimeException e) {
			MessageDialog dialog = new MessageDialog(
					getShell(),
					"Generating model, almost there...",
					null,
					"OOPS! Sorry, something went wrong during parsing. \nCheck for synax errors in formula or model. Maybe the poperty is not monitorable.",
					MessageDialog.INFORMATION, new String[] { "OK" }, 0);
			int result = dialog.open();
			e.printStackTrace();
			return false;
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			MessageDialog dialog = new MessageDialog(getShell(),
					"Generating model, almost there...", null,
					"PROBLEM! Sorry, something went wrong during parsing."
							+ e2.getMessage(), MessageDialog.INFORMATION,
					new String[] { "OK" }, 0);
			int result = dialog.open();
			e2.printStackTrace();
			return false;
		}
	
		// here is where we generate the SD

		try {
			final DisciplinedEnglishPage disciplinedEnglishPage = (DisciplinedEnglishPage) this
					.getPage("Disciplined English Summary: ");

		} catch (Exception e) {
			LOGGER.severe("The operation was interrupted!"); //$NON-NLS-1$
			LOGGER.severe("StackTrace: " + e);
			return false;
		}

		this.getShell().setFocus();
		// this.dispose();
		return true;
	}

	// public static Collection<org.eclipse.uml2.uml.Class> getClasses(
	// org.eclipse.uml2.uml.Package rootPackage) {
	// return EcoreUtil.getObjectsByType(rootPackage.eContents(),
	// UMLPackage.Literals.CLASS);
	//
	// }

}