package fr.yoanndiquelou.binedit.panel;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JDesktopPane;
import javax.swing.SwingUtilities;

public class MDIPanel extends JDesktopPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5498373389728564295L;
	/** Collection des visualisateur ouvert. */
	private Set<BinaryViewer> mViewers;

	public MDIPanel() {
		mViewers = new HashSet<>();
	}

	public void open(File file) {
		SwingUtilities.invokeLater(() -> {
			BinaryViewer viewer = new BinaryViewer(file);
			mViewers.add(viewer);
			add(viewer);
			viewer.toFront();
		});
	}

	public Set<BinaryViewer> getViewers() {
		return mViewers;
	}
}
