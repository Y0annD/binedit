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
		setName("MDI");
		mViewers = new HashSet<>();
	}

	/**
	 * Open a new editor.
	 * 
	 * @param file file to watch
	 */
	public BinaryViewer open(File file) {
		BinaryViewer viewer = new BinaryViewer(file);
		SwingUtilities.invokeLater(() -> {
			mViewers.add(viewer);
			add(viewer);
			viewer.toFront();
		});
		return viewer;
	}

	public Set<BinaryViewer> getViewers() {
		return mViewers;
	}
}
