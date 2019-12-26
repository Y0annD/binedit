package fr.yoanndiquelou.binedit.action;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;

import fr.yoanndiquelou.binedit.Settings;

public class DisplayToolbarAction extends AbstractAction {

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
		Settings.setDisplayToolbar(!Settings.getDisplayToolbar());
	}

	@Override
	public Object getValue(String key) {
		if ("SwingSelectedKey".equals(key)) {
			return Settings.getDisplayToolbar();
		}
		return super.getValue(key);
	}

}
