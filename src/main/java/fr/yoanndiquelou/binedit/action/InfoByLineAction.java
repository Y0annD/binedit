package fr.yoanndiquelou.binedit.action;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;

import fr.yoanndiquelou.binedit.AppController;
import fr.yoanndiquelou.binedit.frame.ViewerSettingsFrame;

public class InfoByLineAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7608268097159413672L;

	/** Resources bundle for i18n. */
	private transient ResourceBundle mBundle = ResourceBundle
			.getBundle("fr.yoanndiquelou.binedit.action.resources.ActionBundle");

	/**
	 * Constructor.
	 */
	public InfoByLineAction() {
		super();
		putValue(NAME, mBundle.getString("display.infobyline.short"));
		putValue(SHORT_DESCRIPTION, mBundle.getString("display.infobyline.short"));
		putValue(LONG_DESCRIPTION, mBundle.getString("display.infobyline.long"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ViewerSettingsFrame frame = new ViewerSettingsFrame(
				AppController.getInstance().getFocusedEditor().getSettings());
		frame.setVisible(true);
	}

}
