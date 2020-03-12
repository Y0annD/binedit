package fr.yoanndiquelou.binedit.command.impl;

import javax.swing.JOptionPane;

import fr.yoanndiquelou.binedit.command.ICommand;
import fr.yoanndiquelou.binedit.panel.BinaryViewer;

/**
 * Action to save content.
 * 
 * @author yoann
 *
 */
public class SaveCommand implements ICommand {
	/** Selected viewer. */
	private BinaryViewer mViewer;

	/**
	 * Save command constructor.
	 * 
	 * @param viewer viewer
	 */
	public SaveCommand(BinaryViewer viewer) {
		mViewer = viewer;
	}

	@Override
	public void execute() {
		JOptionPane.showMessageDialog(null, "Not implemented yet");
	}

}
