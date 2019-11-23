package fr.yoanndiquelou.binedit.laf.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public final class BinEditLookAndFeelCustomizer {

	
	public static final void customize() {
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		UIDefaults uidefaults = super.getDefaults();
//		UIManager.put("EditorPaneUI", BEEditorPaneUI.class.getName());
		UIManager.put("Table.font", new Font("Courier", Font.PLAIN, 12));
		UIManager.put("Table.alternateRowColor", Color.lightGray);
		UIManager.put("Table.focusCellForeground", Color.black);
		UIManager.put("Table.focusCellBackground", Color.white);
		UIManager.put("Table.selectionForeground", Color.black);
		UIManager.put("Selection.background", new Color(191,98,4));
		UIManager.put("Selection.foreground", Color.black);
	}
}
