package fr.yoanndiquelou.binedit.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import fr.yoanndiquelou.binedit.AppController;
import fr.yoanndiquelou.binedit.command.impl.CopyBinaryCommand;
import fr.yoanndiquelou.binedit.panel.BinaryViewer;

/**
 * Action to copy binary content.
 * 
 * @author yoann
 *
 */
public class CopyBinaryAction extends AbstractAction {

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
	public CopyBinaryAction() {
		super();
		putValue(NAME, mBundle.getString("copy.binary.short"));
		putValue(SHORT_DESCRIPTION, mBundle.getString("copy.binary.short"));
		putValue(SHORT_DESCRIPTION, mBundle.getString("copy.binary.long"));
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		BinaryViewer viewer = AppController.getInstance().getFocusedEditor();
		AppController.getInstance().executeCommand(new CopyBinaryCommand(viewer));
	}

}
