package fr.yoanndiquelou.binedit.command.impl;

import javax.swing.JOptionPane;

import fr.yoanndiquelou.binedit.command.ICommand;
import fr.yoanndiquelou.binedit.panel.BinaryViewer;

/**
 * Action to cut content to clipboard.
 * 
 * @author yoann
 *
 */
public class CutCommand implements ICommand {
	/** Selected viewer. */
	private BinaryViewer mViewer;

	/**
	 * Cut command constructor.
	 * 
	 * @param viewer viewer
	 */
	public CutCommand(BinaryViewer viewer) {
		mViewer = viewer;
	}

	@Override
	public void execute() {
		JOptionPane.showMessageDialog(null, "Not implemented yet");
	}

}
