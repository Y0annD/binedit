package fr.yoanndiquelou.binedit.panel;

import java.io.File;

public class FileNode {

	private File file;

	public FileNode(File file) {
		this.file = file;
	}
	
	public File getFile() {
		return this.file;
	}

	@Override
	public String toString() {
		String name = file.getName();
		if ("".equals(name)){
			return file.getAbsolutePath();
		} else {
			return name;
		}
	}
}