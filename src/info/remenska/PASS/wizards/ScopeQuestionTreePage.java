package info.remenska.PASS.wizards;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class ScopeQuestionTreePage extends QuestionTreePage {

	protected ScopeQuestionTreePage(String pageName, String description,
			TreeNode<String> questionnaire) {
		super(pageName, description, questionnaire);
	}
	
	public void addEventSlots(Composite composite, ExpandBar root, Listener operationListener){
		
		GridData gridData = new GridData();
		gridData.verticalSpan = 5;
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		root.setLayoutData(gridData);
		
		 gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.verticalAlignment = SWT.TOP;
		labelStartEvent = new Label(composite, SWT.NONE);
		labelStartEvent.setText("Start Event: ");
		FontData fontData = labelStartEvent.getFont().getFontData()[0];
		Font font = new Font(Display.getCurrent(), new FontData(fontData.getName(), fontData
		    .getHeight(), SWT.BOLD));
		labelStartEvent.setFont(font);
		labelStartEvent.setLayoutData(gridData);
		
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.verticalAlignment = SWT.TOP;
		textStartEvent = new Text(composite, SWT.FILL); // THESE SHOULD BE STATIC
		textStartEvent.setText("double-click to select");
		textStartEvent.setEditable(false);
		textStartEvent.setLayoutData(gridData);
		textStartEvent.addListener(SWT.MouseDoubleClick, operationListener);
		textStartEvent.setEnabled(false);
		
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.verticalAlignment = SWT.TOP;
		labelEndEvent = new Label(composite, SWT.NONE);
		labelEndEvent.setText("End Event: ");
		labelEndEvent.setFont(font);
		labelEndEvent.setLayoutData(gridData);
		
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.verticalAlignment = SWT.TOP;
		textEndEvent = new Text(composite, SWT.FILL);
		textEndEvent.setText("double-click to select");
		textEndEvent.setEditable(false);
		textEndEvent.addListener(SWT.MouseDoubleClick,operationListener);
		textEndEvent.setLayoutData(gridData);
		textEndEvent.setEnabled(false);
		ownedTexts.add(textStartEvent);
		ownedTexts.add(textEndEvent);
	}

}
