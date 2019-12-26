package fr.yoanndiquelou.binedit.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import fr.yoanndiquelou.binedit.AppController;
import fr.yoanndiquelou.binedit.Settings;
import fr.yoanndiquelou.binedit.command.impl.VisibilityCommand;

/**
 * Action to change visibility.
 * 
 * @author yoann
 *
 */
public class VisibilityAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4564010127864159711L;
	/** Property to check. */
	private String mProperty;

	/**
	 * Constructor.
	 * 
	 * @param property property to switch
	 */
	public VisibilityAction(String property, String shortText, String longText) {
		mProperty = property;
		putValue(NAME, shortText);
		putValue(SHORT_DESCRIPTION, shortText);
		putValue(LONG_DESCRIPTION, longText);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		AppController.getInstance().executeCommand(new VisibilityCommand(mProperty, !Settings.getVisibility(mProperty)));

	}

	@Override
	public Object getValue(String key) {
		if ("SwingSelectedKey".equals(key)) {
			return Settings.getVisibility(mProperty);
		}
		return super.getValue(key);
	}

}
