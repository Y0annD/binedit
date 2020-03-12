package fr.yoanndiquelou.binedit.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import fr.yoanndiquelou.binedit.AppController;
import fr.yoanndiquelou.binedit.command.impl.SaveCommand;
import fr.yoanndiquelou.binedit.panel.BinaryViewer;

/**
 * Action to save content.
 * 
 * @author yoann
 *
 */
public class SaveAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6112448399407182908L;
	/** Resources bundle for i18n. */
	private transient ResourceBundle mBundle = ResourceBundle
			.getBundle("fr.yoanndiquelou.binedit.action.resources.ActionBundle");

	/**
	 * Save action constructor.
	 */
	public SaveAction() {
		super("", UIManager.getIcon("save.icon"));
		putValue(NAME, mBundle.getString("save.short"));
		putValue(SHORT_DESCRIPTION, mBundle.getString("save.short"));
		putValue(SHORT_DESCRIPTION, mBundle.getString("save.long"));
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		BinaryViewer viewer = AppController.getInstance().getFocusedEditor();
		AppController.getInstance().executeCommand(new SaveCommand(viewer));
	}
}
