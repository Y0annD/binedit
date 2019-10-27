package fr.yoanndiquelou.binedit.panel;

import java.io.File;

import javax.swing.JDesktopPane;
import javax.swing.SwingUtilities;

public class MDIPanel extends JDesktopPane {

	public MDIPanel() {

	}

	public void open(File file) {
//		JInternalFrame frame = new JInternalFrame(file.getAbsolutePath(), true, true, true,
//        true);
		SwingUtilities.invokeLater(() -> {
			add(new BinaryViewer(file));
		});
	}
}
