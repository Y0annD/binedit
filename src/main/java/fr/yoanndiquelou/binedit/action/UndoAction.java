package fr.yoanndiquelou.binedit.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import fr.yoanndiquelou.binedit.AppController;

public class UndoAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 313774862288218051L;

	/** Resources bundle for i18n. */
	private transient ResourceBundle mBundle = ResourceBundle
			.getBundle("fr.yoanndiquelou.binedit.action.resources.ActionBundle");

	public UndoAction() {
		super("", UIManager.getIcon("undo.icon"));
		putValue(SHORT_DESCRIPTION, mBundle.getString("undo.short"));
		putValue(LONG_DESCRIPTION, mBundle.getString("undo.long"));
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		AppController.getInstance().undo();
	}
}
