package fr.yoanndiquelou.binedit.panel;

public class ViewerSettings {

	private int mNbWordPerLine;

	public ViewerSettings() {
		mNbWordPerLine = 16;
	}

	public void setNbWordPerline(int nbWords) {
		mNbWordPerLine = nbWords;
	}

	/**
	 * Retourne le nombre de mots par ligne.
	 * 
	 * @return nombre de mots par ligne
	 */
	public int getNbWordPerLine() {
		return mNbWordPerLine;
	}
}
