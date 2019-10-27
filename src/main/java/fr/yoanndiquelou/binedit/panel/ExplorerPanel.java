package fr.yoanndiquelou.binedit.panel;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ExplorerPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2415755023812800243L;

	public ExplorerPanel() {
		JScrollPane scroll = new JScrollPane();
		JFileChooser chooser = new JFileChooser();
		scroll.add(chooser);
	}

}
