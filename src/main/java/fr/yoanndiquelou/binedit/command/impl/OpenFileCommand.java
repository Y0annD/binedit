package fr.yoanndiquelou.binedit.command.impl;

import java.io.File;

import fr.yoanndiquelou.binedit.AppController;
import fr.yoanndiquelou.binedit.command.IUndoableCommand;
import fr.yoanndiquelou.binedit.panel.BinaryViewer;

public class OpenFileCommand implements IUndoableCommand {
	/** File to open. */
	private File mFile;
	/** Editor. */
	private BinaryViewer mViewer;

	/**
	 * GoTo command.
	 * 
	 * @param viewer  viewer
	 * @param address new address
	 */
	public OpenFileCommand(File file) {
		mFile = file;
	}

	@Override
	public void execute() {
		mViewer = AppController.getInstance().openFile(mFile);
	}

	@Override
	public void undo() {
		mViewer.dispose();
	}

}
