package fr.yoanndiquelou.binedit.handler;

import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;

import fr.yoanndiquelou.binedit.panel.FileNode;

public class FileTransferHandler extends TransferHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7167000860019586913L;

	public int getSourceActions(JComponent Source) {

		return COPY_OR_MOVE;
	}

	protected Transferable createTransferable(JComponent source) {

		JTree tree = (JTree) source;
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node == null)
			return null;
		File file =  ((FileNode)node.getUserObject()).getFile();
		Collection<File> fileList = new ArrayList<>();
		fileList.add(file);
		return new FileTransferable(fileList);
	}
}
