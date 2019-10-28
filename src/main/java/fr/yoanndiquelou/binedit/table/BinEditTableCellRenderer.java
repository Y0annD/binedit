package fr.yoanndiquelou.binedit.table;

import java.awt.Component;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

public class BinEditTableCellRenderer extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3334898686602771898L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		Font font = UIManager.getFont("Table.font");

		if (isCellSelected(table, row, column)) {
			cell.setBackground(UIManager.getColor("Selection.background"));
			cell.setForeground(UIManager.getColor("Selection.foreground"));

			cell.setFont(font.deriveFont(Font.BOLD));
		} else {
			cell.setFont(font.deriveFont(Font.PLAIN));
			if (row % 2 != 0) {
				cell.setBackground(UIManager.getColor("Table.alternateRowColor"));
			} else {
				cell.setBackground(UIManager.getColor("Table.background"));
			}
		}
		return cell;
	}

	public boolean isCellSelected(JTable table, int row, int column) {
		boolean selected = false;
		BinEditTableModel model = (BinEditTableModel) table.getModel();

		List<Integer> selectedColumns = Arrays.stream(table.getSelectedColumns()).boxed().collect(Collectors.toList());
		List<Integer> selectedRows = Arrays.stream(table.getSelectedRows()).boxed().collect(Collectors.toList());

		if (selectedRows.size() > 1 && column != 0) {
			// Cas ou l'on à plusieurs lignes selectionnées
			int maxRows = selectedRows.get(selectedRows.size() - 1);
			int minRows = selectedRows.get(0);
			if (row > minRows && row < maxRows) {
				selected = true;
			} else if (row == minRows) {
				if (column <= model.getSettings().getNbWordPerLine()) {
					selected = column >= selectedColumns.get(0);
				} else {
					selected = column - model.getSettings().getNbWordPerLine() >= selectedColumns.get(0);
				}
			} else if (row == maxRows) {
				if (column <= model.getSettings().getNbWordPerLine()) {
					selected = column <= selectedColumns.get(selectedColumns.size() - 1);
				} else {
					selected = column - model.getSettings().getNbWordPerLine() <= selectedColumns
							.get(selectedColumns.size() - 1);
				}

			}
		} else if (!selectedColumns.isEmpty() && !selectedRows.isEmpty() && column != 0) {
			if (column <= model.getSettings().getNbWordPerLine()) {
				selected = selectedRows.contains(row) && (selectedColumns.contains(column)
						|| selectedColumns.contains(column + model.getSettings().getNbWordPerLine()));
			} else {
				selected = selectedRows.contains(row) && (selectedColumns.contains(column)
						|| selectedColumns.contains(column - model.getSettings().getNbWordPerLine()));
			}
		}
		return selected;
	}
}
