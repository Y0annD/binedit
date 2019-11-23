package fr.yoanndiquelou.binedit.model;

/**
 * Display mode.
 * 
 * @author yoann
 *
 */
public enum DisplayMode {

	BYTE(false, 1, "B"), BYTECHAR(true, 1, "BC"), WORD(false, 2, "W"), WORDCHAR(true, 2, "WC"), DWORD(false, 4, "D"),
	DWORDCHAR(true, 4, "DC"), QWORD(false, 8, "Q"), QWORDCHAR(true, 8, "QC");
	/** Display text next to value. */
	boolean mDisplayChar;
	/** Number of byte for value. */
	int mNbBytes;
	/** Texte a afficher. */
	String mText;

	/**
	 * Display mode constructor.
	 * 
	 * @param displayChar display char or not
	 * @param bytes       number of bytes per value
	 * @param text        texte a afficher
	 */
	DisplayMode(boolean displayChar, int bytes, String text) {
		mDisplayChar = displayChar;
		mNbBytes = bytes;
		mText = text;
	}

	/**
	 * Display char or not.
	 * 
	 * @return true to display
	 */
	public boolean displayChar() {
		return mDisplayChar;
	}

	/**
	 * Number of bytes per value.
	 * 
	 * @return number of bytes
	 */
	public int getBytes() {
		return mNbBytes;
	}

	/**
	 * Get text to display.
	 * 
	 * @return text to display
	 */
	public String getText() {
		return mText;
	}

	/**
	 * Get display mode from values.
	 * 
	 * @param displayChar display char or not
	 * @param bytes       number of bytes
	 * @return display mode
	 */
	public static DisplayMode fromValue(boolean displayChar, int bytes) {
		DisplayMode mode = BYTECHAR;
		for (DisplayMode m : values()) {
			if (m.displayChar() == displayChar && m.getBytes() == bytes) {
				mode = m;
				break;
			}
		}
		return mode;
	}
}
