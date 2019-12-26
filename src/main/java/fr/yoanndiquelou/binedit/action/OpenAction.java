package fr.yoanndiquelou.binedit.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import fr.yoanndiquelou.binedit.AppController;
import fr.yoanndiquelou.binedit.command.impl.OpenFileCommand;

/**
 * Action to open a new file.
 * 
 * @author yoann
 *
 */
public class OpenAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9143498060835347216L;

	/** Resources bundle for i18n. */
	private transient ResourceBundle mBundle = ResourceBundle
			.getBundle("fr.yoanndiquelou.binedit.action.resources.ActionBundle");

	/** File chooser. */
	private JFileChooser mFileChooser;

	/**
	 * Constructor.
	 */
	public OpenAction() {
		super("", UIManager.getIcon("FileView.directoryIcon"));
		putValue(NAME, mBundle.getString("file.open.short"));
		putValue(SHORT_DESCRIPTION, mBundle.getString("file.open.short"));
		putValue(LONG_DESCRIPTION, mBundle.getString("file.open.long"));
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		mFileChooser = new JFileChooser();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		int result = mFileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			AppController.getInstance().executeCommand(new OpenFileCommand(mFileChooser.getSelectedFile()));
		}
	}

}
