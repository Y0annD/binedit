package fr.yoanndiquelou.binedit.laf;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIDefaults;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import fr.yoanndiquelou.binedit.laf.ui.BEEditorPaneUI;
import fr.yoanndiquelou.binedit.laf.ui.MirrorButtonUI;

public class BinEditLookAndFeel extends NimbusLookAndFeel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -599970523176957003L;

	@Override
	public UIDefaults getDefaults() {
		UIDefaults uidefaults = super.getDefaults();
		uidefaults.put("EditorPaneUI", BEEditorPaneUI.class.getName());
		uidefaults.put("BinaryViewer.Font", new Font("Courier", Font.PLAIN, 12));
		uidefaults.put("Table.alternateRowColor", Color.lightGray);
		uidefaults.put("Table.focusCellForeground", Color.black);
		uidefaults.put("Table.focusCellBackground", Color.white);
		uidefaults.put("Table.selectionForeground", Color.black);
		uidefaults.put("Selection.background", new Color(191,98,4));
		uidefaults.put("Selection.foreground", Color.black);
		return uidefaults;
	}

	@Override
	public String getName() {
		return getClass().getName();
	}

	@Override
	public String getID() {
		return getClass().getName();
	}

	@Override
	public String getDescription() {
		return "BinEdit look and feel";
	}

	@Override
	public boolean isNativeLookAndFeel() {
		return true;
	}

	@Override
	public boolean isSupportedLookAndFeel() {
		return true;
	}
	

}
