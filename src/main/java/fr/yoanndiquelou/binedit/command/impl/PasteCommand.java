package fr.yoanndiquelou.binedit.command.impl;

import javax.swing.JOptionPane;

import fr.yoanndiquelou.binedit.command.ICommand;
import fr.yoanndiquelou.binedit.panel.BinaryViewer;

/**
 * Action to paste text content to clipboard.
 * 
 * @author yoann
 *
 */
public class PasteCommand implements ICommand {
	/** Selected viewer. */
	private BinaryViewer mViewer;

	/**
	 * Paste command constructor.
	 * 
	 * @param viewer viewer
	 */
	public PasteCommand(BinaryViewer viewer) {
		mViewer = viewer;
	}

	@Override
	public void execute() {
		JOptionPane.showMessageDialog(null, "Not implemented yet");
//		StringSelection stringSelection = new StringSelection(mViewer.getTextContentSelection());
//		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//		clipboard.setContents(stringSelection, null);

	}

}
