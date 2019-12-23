package fr.yoanndiquelou.binedit.menu.action;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;

import fr.yoanndiquelou.binedit.AppController;
import fr.yoanndiquelou.binedit.dialog.GoToDialog;

public class GotoAction implements Action {

	private boolean mEnabled;

	@Override
	public void actionPerformed(ActionEvent e) {
		new GoToDialog(AppController.getInstance().getFocusedEditor()).setVisible(true);
	}

	@Override
	public Object getValue(String key) {
		return null;
	}

	@Override
	public void putValue(String key, Object value) {
	}

	@Override
	public void setEnabled(boolean b) {
		mEnabled = b;
	}

	@Override
	public boolean isEnabled() {
		return mEnabled;
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
	}

}
