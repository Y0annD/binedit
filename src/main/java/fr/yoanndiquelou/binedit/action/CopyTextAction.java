package fr.yoanndiquelou.binedit.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import fr.yoanndiquelou.binedit.AppController;
import fr.yoanndiquelou.binedit.command.impl.CopyTextCommand;
import fr.yoanndiquelou.binedit.panel.BinaryViewer;

/**
 * Action to copy text content.
 * 
 * @author yoann
 *
 */
public class CopyTextAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6112448399407182908L;
	/** Resources bundle for i18n. */
	private transient ResourceBundle mBundle = ResourceBundle
			.getBundle("fr.yoanndiquelou.binedit.action.resources.ActionBundle");

	/**
	 * Copy binary action constructor.
	 */
	public CopyTextAction() {
		super("", UIManager.getIcon("copy.icon"));
		putValue(NAME, mBundle.getString("copy.text.short"));
		putValue(SHORT_DESCRIPTION, mBundle.getString("copy.text.short"));
		putValue(SHORT_DESCRIPTION, mBundle.getString("copy.text.long"));
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_MASK));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		BinaryViewer viewer = AppController.getInstance().getFocusedEditor();
		AppController.getInstance().executeCommand(new CopyTextCommand(viewer));
	}
}
