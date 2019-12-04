package fr.yoanndiquelou.binedit.laf.ui;

import java.awt.Color;
import java.awt.Font;
import java.util.prefs.Preferences;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public final class BinEditLookAndFeelCustomizer {

	private static Preferences mUIPreferences = Preferences.userNodeForPackage(BinEditLookAndFeelCustomizer.class);
	
	public static final void customize() {
		try {
			UIManager.setLookAndFeel(/*NimbusLookAndFeel.class.getName()*/UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		UIManager.put("BinaryViewer.Font", new Font(/*"Courier"*/"Fixedsys", Font.BOLD, mUIPreferences.getInt("BinaryViewer.font.size", 9)));
		UIManager.put("BinaryViewer.alternateRowColor", Color.lightGray);
		UIManager.put("Table.focusCellForeground", Color.black);
		UIManager.put("Table.focusCellBackground", Color.white);
		UIManager.put("Table.selectionForeground", Color.black);
		UIManager.put("Selection.background", new Color(191,98,4));
		UIManager.put("Selection.foreground", Color.black);
	}
	
}
