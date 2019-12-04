package fr.yoanndiquelou.binedit.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

import fr.yoanndiquelou.binedit.Settings;
import fr.yoanndiquelou.binedit.utils.AddressUtils;

/**
 * Cell renderer pour sélection d'addresses continues.
 * 
 * @author yoann
 *
 */
public class BinEditTableCellRenderer extends DefaultTableCellRenderer {
	/** Resources bundle. */
	private ResourceBundle mBundle = ResourceBundle.getBundle("fr.yoanndiquelou.binedit.table.resources.BinaryTable");

	/**
	 * 
	 */
	private static final long serialVersionUID = 3334898686602771898L;
	/** Bordure de selection. */
	private transient Border mSelectedBorder;
	/** Bordure de delimitation. */
	private transient Border mLimitBorder;
	/** Bordure par defaut. */
	private transient Border mDefaultBorder;

	public BinEditTableCellRenderer() {
		mSelectedBorder = BorderFactory.createCompoundBorder();
		mLimitBorder = BorderFactory.createCompoundBorder();
		mDefaultBorder = BorderFactory.createEmptyBorder();

		mSelectedBorder = BorderFactory.createCompoundBorder(mSelectedBorder,
				BorderFactory.createMatteBorder(2, 2, 2, 2, UIManager.getColor("Selection.background")));

		mLimitBorder = BorderFactory.createCompoundBorder(mLimitBorder,
				BorderFactory.createMatteBorder(0, 0, 0, 2, Color.cyan));
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		String result;
		byte byteValue = (byte) ((int) value & 0xFF);
		BinEditTableModel model = (BinEditTableModel) table.getModel();
		int diffAddress = 0;
		if (Settings.getVisibility(Settings.DISPLAY_ADDRESSES)) {
			diffAddress = 1;
		}
		if (column == 0 && Settings.getVisibility(Settings.DISPLAY_ADDRESSES)) {
			if (Settings.getVisibility(Settings.ADDRESSES_HEXA)) {
				result = AddressUtils.getHexString((int) value);
			} else {
				result = String.valueOf(value);
			}
			while (result.length() < model.mMaxAddrStr.length()) {
				if ((int) value < 0) {
					result = "-0".concat(result.substring(1));
				} else {
					result = (Settings.getVisibility(Settings.ADDRESSES_HEXA) ? "0x0" : "0")
							.concat(result.substring((Settings.getVisibility(Settings.ADDRESSES_HEXA) ? 2 : 0)));
				}
			}
		} else if (model.isValidAddress(row, column)) {
			if (column >= model.getSettings().getNbWordPerLine() +diffAddress) {
				result = new String(new byte[] { byteValue }).replace("\n", ".").replace(" ", ".");
			} else if (!Settings.getVisibility(Settings.INFO_HEXA)) {
				result = String.valueOf(byteValue);
			} else {
				result = String.format("%02x", byteValue).toUpperCase(Locale.getDefault());
			}
		} else {
			result = "";
		}
		JComponent cell = (JComponent) super.getTableCellRendererComponent(table, result, isSelected, hasFocus, row,
				column);
		setHorizontalAlignment(JLabel.CENTER);
		setVerticalAlignment(JLabel.CENTER);

		if (column == 0 && Settings.getVisibility(Settings.DISPLAY_ADDRESSES)) {
			setHorizontalAlignment(JLabel.RIGHT);
		} else if (column <= model.getSettings().getNbWordPerLine()) {
			String tooltip = "<html><b>" + mBundle.getString("VALUE") + "</b><br/><b>" + mBundle.getString("BINARY")
					+ " :</b> " + byteValue + "<br /><b>" + mBundle.getString("HEXA") + ": </b>0x"
					+ String.format("%02x", byteValue).toUpperCase(Locale.getDefault()) + "<hr/><b>"
					+ mBundle.getString("ADDRESS") + "</b><br/><b>" + mBundle.getString("BINARY") + " : </b>"
					+ model.getAddress(row, column) + "<br/><b>" + mBundle.getString("HEXA") + ": </b>0x"
					+ String.format("%02x", model.getAddress(row, column)).toUpperCase(Locale.getDefault()) + "</html>";
			cell.setToolTipText(tooltip);
		}
		Font font = UIManager.getFont("BinaryViewer.Font");
		cell.setBorder(mDefaultBorder);

		if (isCellSelected(table, row, column)) {
			cell.setBackground(UIManager.getColor("Selection.background"));
			cell.setForeground(Color.white);

			cell.setFont(font.deriveFont(Font.BOLD));
			cell.setBorder(mSelectedBorder);
		} else {
			cell.setFont(font.deriveFont(Font.PLAIN));
			if (row % 2 != 0) {
				cell.setBackground(UIManager.getColor("BinaryViewer.alternateRowColor"));
			} else {
				cell.setBackground(UIManager.getColor("BinaryViewer.background"));
			}
			cell.setForeground(Color.black);
		}
		if ((column == 0 && Settings.getVisibility(Settings.DISPLAY_ADDRESSES))
				|| (column == (model.getSettings().getNbWordPerLine()-1+diffAddress))) {
			cell.setBorder(mLimitBorder);
		}
		return cell;
	}

	private boolean multilineSelection(BinEditTableModel model, int row, int column) {
		boolean selected;
		// Cas ou l'on à plusieurs lignes selectionnées
		long maxRows = model.getMaxSelectionAddr() / model.getSettings().getNbWordPerLine();
		long minRows = model.getMinSelectionAddr() / model.getSettings().getNbWordPerLine();
		long minColumn = model.getMinSelectionAddr() % model.getSettings().getNbWordPerLine();
		long maxColumn = model.getMaxSelectionAddr() % model.getSettings().getNbWordPerLine();
		if (Settings.getVisibility(Settings.DISPLAY_ADDRESSES)) {
			minColumn += 1;
			maxColumn += 1;
		}
		if (row > minRows && row < maxRows) {
			selected = true;
		} else if (row == minRows) {
			if (column <= model.getSettings().getNbWordPerLine()) {
				selected = column >= minColumn;
			} else {
				selected = column - model.getSettings().getNbWordPerLine() >= minColumn;
			}
		} else if (row == maxRows) {
			if (column <= model.getSettings().getNbWordPerLine()) {
				selected = column <= maxColumn;
			} else {
				selected = column - model.getSettings().getNbWordPerLine() <= maxColumn;
			}

		} else {
			selected = false;
		}
		return selected;
	}

	public boolean isCellSelected(JTable table, int row, int column) {
		boolean selected = false;
		BinEditTableModel model = (BinEditTableModel) table.getModel();

		if (model.getMaxSelectionAddr() / model.getSettings().getNbWordPerLine() != model.getMinSelectionAddr()
				/ model.getSettings().getNbWordPerLine()
				&& (column != 0 || !Settings.getVisibility(Settings.DISPLAY_ADDRESSES))) {
			selected = multilineSelection(model, row, column);
		} else if ((column != 0 || !Settings.getVisibility(Settings.DISPLAY_ADDRESSES))) {
			int delta = (Settings.getVisibility(Settings.DISPLAY_ADDRESSES) ? 1 : 0);
			if (column <= model.getSettings().getNbWordPerLine()) {
				selected = (row == model.getMinSelectionAddr() / model.getSettings().getNbWordPerLine()
						&& column - delta <= model.getMaxSelectionAddr() % model.getSettings().getNbWordPerLine()
						&& column - delta >= model.getMinSelectionAddr() % model.getSettings().getNbWordPerLine());
			} else {
				selected = (row == model.getMinSelectionAddr() / model.getSettings().getNbWordPerLine()
						&& column - delta - model.getSettings().getNbWordPerLine() <= model.getMaxSelectionAddr()
								% model.getSettings().getNbWordPerLine()
						&& column - delta - model.getSettings().getNbWordPerLine() >= model.getMinSelectionAddr()
								% model.getSettings().getNbWordPerLine());
			}
		}
		return selected;
	}
}
