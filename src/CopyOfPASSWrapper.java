
import info.remenska.PASS.wizards.CapturePropertyWizard;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class CopyOfPASSWrapper extends ApplicationWindow {

	protected Control createContents(Composite parent) {
	    Composite container = new Composite(parent, SWT.VERTICAL);

//		Button button1 = new Button(container, SWT.PUSH);
//		button1.setText("Browse...");
		
		Button button = new Button(container, SWT.PUSH);
		button.setText("Make a reservation");
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
		return container;
	}

	public CopyOfPASSWrapper(Shell parentShell) {
		super(parentShell);
	}

	public static void main(String[] args) {
		CopyOfPASSWrapper reservation = new CopyOfPASSWrapper(null);
		reservation.setBlockOnOpen(true);
		reservation.open();
	}
}
