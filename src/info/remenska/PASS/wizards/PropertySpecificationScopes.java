package info.remenska.PASS.wizards;

import info.remenska.PASS.monitor.mCRL2.Main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import java.util.logging.Logger;

public class PropertySpecificationScopes extends WizardPage {
	private final static Logger LOGGER = Logger.getLogger("info.remenska.PASS");

	protected PropertySpecificationScopes(String pageName, String description) {
		super(pageName);
		setTitle(pageName);
		setDescription(description);

	}

	public void createControl(Composite parent) {
		String explanation = "Property Specification Patterns describe commonly occurring requirements in a generalized, classified manner. "
				+ "There are two aspects of each property. The first one tells when the property should hold, and the second "
				+ "tells what desired condition (behavior) should be satisfied during this time/scope. Hence, each property has a scope and a pattern."
				+

				"\n\nThe scope is the extent of the program execution "
				+ "over which the pattern must hold. There are five basic kinds of scopes: 'Globally' (the entire program execution), "
				+ "'Before-R' (the execution up to a given event), 'After-Q' (the execution after a given event), "
				+ "'Between-Q-and-R' (any part of the execution from one given event to another given event) "
				+ "and 'After-Q-Until-R' (like between, but the designated part of the execution continues even if the second event does not occur). \n\n"
				+ "There are several variations of these basic kinds of scope: \n"
				+ " \u26AB A variation of the 'After-Q' considers the last occurrence of the event Q as a starting "
				+ "delimiter. In other words, the behavior must hold from the last occurrence of Q (if it occurs more than once), until the end of the execution. This effectivelly resets the beginning of the interval. \n"
				+ " \u26AB Similarly, 'Until-R' considers the end-event delimiter of the scope optional: if it never occurs, then the behavior is required to hold until the end of the execution.\n\n"
				+ "The actually scope is determined by specifying a starting and an ending event for the pattern.";

//				+ "Before and after scopes for our patterns are interpreted relative to the first occurrence of the designated "
//				+ "state/event. We have done this because it matches our experience with real specifications. Note, however, that we could just "
//				+ "as easily interpret these scopes relative to the last occurrence of the designated state/event "
//				+ "(the mappings given in the patterns are easily transformed to match this interpretation). At present we do not "
//				+ "see the need for supporting both first and last occurrence scopes, but as we gain experience applying the patterns "
//				+ "we may wish to extend scopes in this way. ";

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		// layout.set
		setControl(composite);

		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.verticalAlignment = SWT.TOP;
		gridData.verticalSpan = 5;

		gridData.heightHint = 550;
		gridData.widthHint = 650;
		// gridData.horizontalAlignment = GridData.GRAB_HORIZONTAL;
		// gridData.verticalAlignment = GridData.GRAB_VERTICAL;
		// gridData.se
		StyledText styledText = new StyledText(composite, SWT.WRAP);
		styledText.setLineJustify(0, 1, true);
		styledText.setEditable(false);
		// styledText.setLineAlignment(6, 1, SWT.RIGHT);
		styledText.setWordWrap(true);
		styledText.setLayoutData(gridData);
		// styledText.setBounds(10,10,100,100);
		styledText.setText(explanation);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.verticalAlignment = SWT.TOP;
		Label imageHolder = new Label(composite, SWT.WRAP | SWT.BORDER);

		// TODO: Change
		// for testing
		Image scopesGraphical = new Image(
				Display.getCurrent(),
				"/home/daniela/IBM/rationalsdp/workspace1/info.remenska.PASSWebStart/images/scopes.gif");
		// for testing

		// Create icons
		// for deployment
//		ClassLoader cl = this.getClass().getClassLoader();
//		 Image scopesGraphical = new Image(Display.getCurrent(),
//		 cl.getResourceAsStream("images/scopes.gif"));
		// for deployment

		imageHolder.setImage(scopesGraphical);

		imageHolder.setLayoutData(gridData);

		gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.verticalAlignment = SWT.TOP;

		gridData.heightHint = 100;
		gridData.widthHint = 400;

		Link link = new Link(composite, SWT.NONE);
		link.setLayoutData(gridData);
		String message = "* The explanation and figure are partially borrowed from the <a href=\"http://patterns.projects.cis.ksu.edu/documentation/patterns/scopes.shtml\">Property Specification Patterns</a> project website.";
		link.setText(message);
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				LOGGER.fine("You have selected: " + e.text);
				try {
					// Open default external browser
					PlatformUI.getWorkbench().getBrowserSupport()
							.getExternalBrowser().openURL(new URL(e.text));
				} catch (PartInitException ex) {
					ex.printStackTrace();
				} catch (MalformedURLException ex) {
					ex.printStackTrace();
				}
			}
		});

	}

}
