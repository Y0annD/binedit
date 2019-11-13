package fr.yoanndiquelou.binedit.table;

import java.util.Locale;

import javax.swing.table.AbstractTableModel;

import fr.yoanndiquelou.binedit.model.ViewerSettings;
import fr.yoanndiquelou.binedit.utils.AddressUtils;

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
		return 2 * mSettings.getNbWordPerLine() + 1;
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
		if (isValidAddress(rowIndex, columnIndex)) {
			if (columnIndex > mSettings.getNbWordPerLine()) {
				byte[] content = new byte[1];
				content[0] = mContent[rowIndex * mSettings.getNbWordPerLine() + columnIndex
						- mSettings.getNbWordPerLine() - 1 + mSettings.getShift()];
				result = new String(content).replace("\n", ".").replace(" ", ".");
			} else if (columnIndex > 0) {
				result = String.format("%02x",
						mContent[rowIndex * mSettings.getNbWordPerLine() + columnIndex - 1 + mSettings.getShift()])
						.toUpperCase();
			} else {
				result = getAddress(rowIndex, columnIndex);
			}
		} else {
			result = "00";
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
		String result;
		long addr = rowIndex * mSettings.getNbWordPerLine() + columnIndex + mSettings.getShift();
		result = AddressUtils.getHexString(addr);

		while ((addr >= 0 ? result.length() : result.length() - 1) < maxChar) {
			if (addr < 0) {
				result = "-0".concat(result.substring(1));
			} else {
				result = "0".concat(result);
			}
		}
		return result;
	}

	/**
	 * The address at the specified coordinates is valid?
	 * 
	 * @param rowIndex    row index in table
	 * @param columnIndex column index in table
	 * @return true if valid.
	 */
	private boolean isValidAddress(int rowIndex, int columnIndex) {
		try {
			if (columnIndex == 0)
				return true;
			if (columnIndex > mSettings.getNbWordPerLine()) {
				columnIndex -= mSettings.getNbWordPerLine();
			}
			byte a = mContent[rowIndex * mSettings.getNbWordPerLine() + columnIndex - 1 + mSettings.getShift()];
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}

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
