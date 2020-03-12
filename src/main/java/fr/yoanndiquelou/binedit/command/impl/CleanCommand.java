package fr.yoanndiquelou.binedit.command.impl;

import javax.swing.JOptionPane;

import fr.yoanndiquelou.binedit.command.ICommand;
import fr.yoanndiquelou.binedit.panel.BinaryViewer;

/**
 * Action to clean content.
 * 
 * @author yoann
 *
 */
public class CleanCommand implements ICommand {
	/** Selected viewer. */
	private BinaryViewer mViewer;

	/**
	 * Clean command constructor.
	 * 
	 * @param viewer viewer
	 */
	public CleanCommand(BinaryViewer viewer) {
		mViewer = viewer;
	}

	@Override
	public void execute() {
		JOptionPane.showMessageDialog(null, "Not implemented yet");
	}

}
