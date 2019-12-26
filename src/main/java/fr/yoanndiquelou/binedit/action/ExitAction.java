package fr.yoanndiquelou.binedit.action;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;

public class ExitAction extends AbstractAction {

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
	public ExitAction() {
		super();
		putValue(NAME, mBundle.getString("file.exit.short"));
		putValue(SHORT_DESCRIPTION, mBundle.getString("file.exit.short"));
		putValue(LONG_DESCRIPTION, mBundle.getString("file.exit.long"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}

}
