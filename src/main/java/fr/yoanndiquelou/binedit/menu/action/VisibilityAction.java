package fr.yoanndiquelou.binedit.menu.action;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;

import fr.yoanndiquelou.binedit.Settings;

public class VisibilityAction implements Action {
	/** Property to check. */
	private String mProperty;
	
	public VisibilityAction(String property) {
		mProperty = property;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Settings.setVisibility(mProperty, !Settings.getVisibility(mProperty));

	}

	@Override
	public Object getValue(String key) {
		if("SwingSelectedKey".equals(key)) {
			return Settings.getVisibility(mProperty);
		}
		return null;
	}

	@Override
	public void putValue(String key, Object value) {

	}

	@Override
	public void setEnabled(boolean b) {

	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {

	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {

	}

}
