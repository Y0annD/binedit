package fr.yoanndiquelou.binedit.action;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;

import fr.yoanndiquelou.binedit.Settings;

public class DisplayToolbarAction extends AbstractAction {
	
	/** Selected item key. */
	private static final String SELECTED_KEY = "SwingSelectedKey";

	/**
	 * 
	 */
	private static final long serialVersionUID = -4230456953083448056L;
	
	/** Resources bundle for i18n. */
	private transient ResourceBundle mBundle = ResourceBundle.getBundle("fr.yoanndiquelou.binedit.action.resources.ActionBundle");

	public DisplayToolbarAction() {
		super();
		putValue(NAME, mBundle.getString("display.toolbar.short"));
		putValue(SHORT_DESCRIPTION, mBundle.getString("display.toolbar.short"));
		putValue(LONG_DESCRIPTION, mBundle.getString("display.toolbar.long"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean oldState = Settings.getDisplayToolbar();
		Settings.setDisplayToolbar(!oldState);
		firePropertyChange(SELECTED_KEY, oldState, !oldState);
	}

	@Override
	public Object getValue(String key) {
		if (SELECTED_KEY.equals(key)) {
			return Settings.getDisplayToolbar();
		}
		return super.getValue(key);
	}

}
