package fr.yoanndiquelou.binedit.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

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
		super("", new ImageIcon("resources/undo.png"));
		putValue(SHORT_DESCRIPTION, mBundle.getString("undo.short"));
		putValue(LONG_DESCRIPTION, mBundle.getString("undo.long"));
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));

	}

	@Override
	public boolean isEnabled() {
		return AppController.getInstance().getUndoStackSize() > 0;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		AppController.getInstance().undo();
	}
}
