package fr.yoanndiquelou.binedit.command.impl;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import fr.yoanndiquelou.binedit.command.ICommand;
import fr.yoanndiquelou.binedit.panel.BinaryViewer;

/**
 * Action to copy text content to clipboard.
 * 
 * @author yoann
 *
 */
public class CopyTextCommand implements ICommand {
	/** Selected viewer. */
	private BinaryViewer mViewer;

	/**
	 * Copy binary command constructor.
	 * 
	 * @param viewer viewer
	 */
	public CopyTextCommand(BinaryViewer viewer) {
		mViewer = viewer;
	}

	@Override
	public void execute() {
		StringSelection stringSelection = new StringSelection(mViewer.getTextContentSelection());
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);

	}

}
