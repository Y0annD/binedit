package fr.yoanndiquelou.binedit.command.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import fr.yoanndiquelou.binedit.command.AbstractSaveCommand;
import fr.yoanndiquelou.binedit.command.ICommand;
import fr.yoanndiquelou.binedit.panel.BinaryViewer;

/**
 * Action to save As content.
 * 
 * @author yoann
 *
 */
public class SaveAsCommand extends AbstractSaveCommand implements ICommand {
	/** Selected viewer. */
	private BinaryViewer mViewer;

	/**
	 * Save As command constructor.
	 * 
	 * @param viewer viewer
	 */
	public SaveAsCommand(BinaryViewer viewer) {
		mViewer = viewer;
	}

	@Override
	public void execute() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);
		int result = fileChooser.showSaveDialog(null);
		if (JFileChooser.APPROVE_OPTION == result) {
			File file = mViewer.getFile();
			File outputFile = fileChooser.getSelectedFile();
			if (outputFile.exists()) {
				outputFile.delete();
			}
			try {
				outputFile.createNewFile();
				try (FileOutputStream fos = new FileOutputStream(outputFile)) {
					Files.copy(file.toPath(), fos);
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Unable to save file", "Error", JOptionPane.ERROR_MESSAGE);
			}

		}
	}

}
