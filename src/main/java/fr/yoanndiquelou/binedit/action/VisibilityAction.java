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
	
	/** Selected item key. */
	private static final String SELECTED_KEY = "SwingSelectedKey";
	
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
		boolean oldState = Settings.getVisibility(mProperty);
		AppController.getInstance().executeCommand(new VisibilityCommand(mProperty, !oldState));
		firePropertyChange(SELECTED_KEY, oldState, !oldState);

	}

	@Override
	public Object getValue(String key) {
		if (SELECTED_KEY.equals(key)) {
			return Settings.getVisibility(mProperty);
		}
		return super.getValue(key);
	}

}
