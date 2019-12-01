package fr.yoanndiquelou.binedit.menu.action;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;

import fr.yoanndiquelou.binedit.Settings;

public class DisplayToolbarAction implements Action {

	
	@Override
	public void actionPerformed(ActionEvent e) {
		Settings.setDisplayToolbar(!Settings.getDisplayToolbar());
	}

	@Override
	public Object getValue(String key) {
		if("SwingSelectedKey".equals(key)) {
			return Settings.getDisplayToolbar();
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
