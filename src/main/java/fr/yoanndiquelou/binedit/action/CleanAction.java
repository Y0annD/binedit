package fr.yoanndiquelou.binedit.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import fr.yoanndiquelou.binedit.AppController;
import fr.yoanndiquelou.binedit.command.impl.CleanCommand;
import fr.yoanndiquelou.binedit.panel.BinaryViewer;

/**
 * Action to clean content.
 * 
 * @author yoann
 *
 */
public class CleanAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6112448399407182908L;
	/** Resources bundle for i18n. */
	private transient ResourceBundle mBundle = ResourceBundle
			.getBundle("fr.yoanndiquelou.binedit.action.resources.ActionBundle");

	/**
	 * Clean action constructor.
	 */
	public CleanAction() {
		super("", UIManager.getIcon("clean.icon"));
		putValue(NAME, mBundle.getString("clean.short"));
		putValue(SHORT_DESCRIPTION, mBundle.getString("clean.short"));
		putValue(SHORT_DESCRIPTION, mBundle.getString("clean.long"));
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_MASK));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		BinaryViewer viewer = AppController.getInstance().getFocusedEditor();
		AppController.getInstance().executeCommand(new CleanCommand(viewer));
	}
}
