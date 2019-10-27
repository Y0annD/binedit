package fr.yoanndiquelou.binedit.handler;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FileTransferable implements Transferable {

	private final List<File> files;
	private final DataFlavor[] flavors;

	/**
	 * A drag-and-drop object for transfering a file.
	 * 
	 * @param file file to transfer -- this file should already exist, otherwise it
	 *             may not be accepted by drag targets.
	 */
	public FileTransferable(Collection<File> files) {
		this.files = Collections.unmodifiableList(new ArrayList<>(files));
		this.flavors = new DataFlavor[] { DataFlavor.javaFileListFlavor };
	}

	public DataFlavor[] getTransferDataFlavors() {
		return this.flavors;
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return DataFlavor.javaFileListFlavor.equals(flavor);
	}

	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if (isDataFlavorSupported(flavor)) {
			return this.files;
		} else {
			return null;
		}
	}
}
