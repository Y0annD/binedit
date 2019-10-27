package fr.yoanndiquelou.binedit.table;

import java.awt.Component;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
		BinEditTableModel model = (BinEditTableModel) table.getModel();

		Font font = UIManager.getFont("Table.font");
		List<Integer> selectedColumns = Arrays.stream(table.getSelectedColumns()).boxed().collect(Collectors.toList());
		List<Integer> selectedRows = Arrays.stream(table.getSelectedRows()).boxed().collect(Collectors.toList());
		if (!selectedColumns.isEmpty() && !selectedRows.isEmpty() && column > 0 && selectedRows.contains(row)
				&& (selectedColumns.contains(column)
						|| selectedColumns.contains(column - model.getSettings().getNbWordPerLine() - 1))) {
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
}
