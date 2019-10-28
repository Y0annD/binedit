package fr.yoanndiquelou.binedit.table;

import javax.swing.table.AbstractTableModel;

import fr.yoanndiquelou.binedit.panel.ViewerSettings;

public class BinEditTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6407633384182119219L;
	/** Contenu. */
	public final byte[] mContent;
	/** Nombre de byte par ligne. */
	public final ViewerSettings mSettings;
	/** adresse maximum sous forme de chaine de caractere. */
	public final String mMaxAddrStr;

	public BinEditTableModel(byte[] content, ViewerSettings settings) {
		mContent = content;
		mSettings = settings;
		mMaxAddrStr = String.format("%02x", content.length);
	}

	@Override
	public int getRowCount() {
		return (int) Math.floor(mContent.length / (double) mSettings.getNbWordPerLine());
	}

	@Override
	public int getColumnCount() {
		return 2 * mSettings.getNbWordPerLine()+1;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return false;
		} else {
			return super.isCellEditable(rowIndex, columnIndex);
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String result;
		if (columnIndex > mSettings.getNbWordPerLine() ) {
			byte[] content = new byte[1];
			content[0] = mContent[rowIndex * mSettings.getNbWordPerLine() + columnIndex - mSettings.getNbWordPerLine()
					 - 1];
			result = new String(content).replace("\n", ".").replace(" ", ".");
		} else if (columnIndex > 0) {
			result = String.format("%02x", mContent[rowIndex * mSettings.getNbWordPerLine() + columnIndex - 1])
					.toUpperCase();
		} else {
			result = getAddress(rowIndex, columnIndex);
		}
		return result;
	}

	/**
	 * Get address with 00 added before when needed.
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 * @return
	 */
	private String getAddress(int rowIndex, int columnIndex) {
		int maxChar = mMaxAddrStr.length();
		String result = String.format("%02x", rowIndex * mSettings.getNbWordPerLine() + columnIndex).toUpperCase();
		while (result.length() < maxChar) {
			result = "0".concat(result);
		}
		return result;
	}

	/**
	 * Viewer settings.
	 * 
	 * @return
	 */
	public ViewerSettings getSettings() {
		return mSettings;
	}
}
