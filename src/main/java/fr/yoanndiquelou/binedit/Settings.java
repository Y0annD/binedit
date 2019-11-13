package fr.yoanndiquelou.binedit;

import java.util.prefs.Preferences;

public class Settings {
	
	public static final String WORD_PER_LINE = "WORD_PER_LINE";

	private static final Preferences mPrefs = Preferences.userRoot().node(Settings.class.getName());
	
	private static int mNbBytesPerLine = mPrefs.getInt(WORD_PER_LINE, 24);


	
	/**
	 * Get number of bytes per line.
	 * 
	 * @return number of bytes per line
	 */
	public static int getBytesPerLine() {
		return mNbBytesPerLine;
	}
	
	/**
	 * set number of bytes per line.
	 * 
	 * @param number of bytes per line
	 */
	public static void setBytesPerLine(int nbBytesPerLine) {
		mNbBytesPerLine = nbBytesPerLine;
		mPrefs.putInt(WORD_PER_LINE, mNbBytesPerLine);
	}
}
