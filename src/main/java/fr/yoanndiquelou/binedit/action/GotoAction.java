package fr.yoanndiquelou.binedit.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import fr.yoanndiquelou.binedit.AppController;
import fr.yoanndiquelou.binedit.dialog.GoToDialog;

/**
 * GoTo specific address.
 * 
 * @author yoann
 *
 */
public class GotoAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7151485267747258653L;
	/** Resources bundle for i18n. */
	private transient ResourceBundle mBundle = ResourceBundle
			.getBundle("fr.yoanndiquelou.binedit.action.resources.ActionBundle");

	public GotoAction() {
		super();
		putValue(NAME, mBundle.getString("display.goto.short"));
		putValue(SHORT_DESCRIPTION, mBundle.getString("display.goto.short"));
		putValue(LONG_DESCRIPTION, mBundle.getString("display.goto.long"));
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SwingUtilities.invokeLater(() -> {
			JDialog goToDialog = new GoToDialog(AppController.getInstance().getFocusedEditor());
			goToDialog.setLocationRelativeTo(AppController.getInstance().getFocusedEditor());
			goToDialog.setVisible(true);
		});

	}

}
