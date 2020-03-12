package fr.yoanndiquelou.binedit.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import fr.yoanndiquelou.binedit.AppController;
import fr.yoanndiquelou.binedit.command.impl.PasteCommand;
import fr.yoanndiquelou.binedit.panel.BinaryViewer;

/**
 * Action to paste content.
 * 
 * @author yoann
 *
 */
public class PasteAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6112448399407182908L;
	/** Resources bundle for i18n. */
	private transient ResourceBundle mBundle = ResourceBundle
			.getBundle("fr.yoanndiquelou.binedit.action.resources.ActionBundle");

	/**
	 * Paste action constructor.
	 */
	public PasteAction() {
		super("", UIManager.getIcon("paste.icon"));
		putValue(NAME, mBundle.getString("paste.short"));
		putValue(SHORT_DESCRIPTION, mBundle.getString("paste.short"));
		putValue(SHORT_DESCRIPTION, mBundle.getString("paste.long"));
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		BinaryViewer viewer = AppController.getInstance().getFocusedEditor();
		AppController.getInstance().executeCommand(new PasteCommand(viewer));
	}
}
