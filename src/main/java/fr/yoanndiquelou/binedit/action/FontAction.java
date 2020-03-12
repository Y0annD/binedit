package fr.yoanndiquelou.binedit.action;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;

import fr.yoanndiquelou.binedit.dialog.FontDialog;

/**
 * GoTo specific address.
 * 
 * @author yoann
 *
 */
public class FontAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7151485267747258653L;
	/** Resources bundle for i18n. */
	private transient ResourceBundle mBundle = ResourceBundle
			.getBundle("fr.yoanndiquelou.binedit.action.resources.ActionBundle");

	public FontAction() {
		super();
		putValue(NAME, mBundle.getString("display.font.short"));
		putValue(SHORT_DESCRIPTION, mBundle.getString("display.font.short"));
		putValue(LONG_DESCRIPTION, mBundle.getString("display.font.long"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new FontDialog().setVisible(true);
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}

}
