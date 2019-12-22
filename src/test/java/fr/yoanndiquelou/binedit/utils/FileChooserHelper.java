package fr.yoanndiquelou.binedit.utils;

import java.io.File;
import java.nio.file.Files;

import org.assertj.core.util.Arrays;
import org.assertj.swing.fixture.JFileChooserFixture;

public class FileChooserHelper {

	public static void selectFile(JFileChooserFixture fixture, File file) {
		File currentDirectory = fixture.target().getCurrentDirectory();
		if (Arrays.asList(currentDirectory.listFiles()).contains(file)) {
			fixture.selectFile(file);
		} else {
			// search common directory
			String commonPath = commonPath(currentDirectory.getAbsolutePath(), file.getAbsolutePath());
			while (!fixture.target().getCurrentDirectory().getAbsolutePath().contentEquals(commonPath)) {
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fixture.selectFile(fixture.target().getCurrentDirectory().getParentFile());
			}
			
		}
	}

	/**
	 * Find common path of two files.
	 * 
	 * @param paths files
	 * @return common path
	 */
	public static String commonPath(String... paths) {
		String commonPath = "";
		String[][] folders = new String[paths.length][];
		for (int i = 0; i < paths.length; i++) {
			folders[i] = paths[i].split("/"); // split on file separator
		}
		for (int j = 0; j < folders[0].length; j++) {
			String thisFolder = folders[0][j]; // grab the next folder name in the first path
			boolean allMatched = true; // assume all have matched in case there are no more paths
			for (int i = 1; i < folders.length && allMatched; i++) { // look at the other paths
				if (folders[i].length < j) { // if there is no folder here
					allMatched = false; // no match
					break; // stop looking because we've gone as far as we can
				}
				// otherwise
				allMatched &= folders[i][j].equals(thisFolder); // check if it matched
			}
			if (allMatched) { // if they all matched this folder name
				commonPath += thisFolder + "/"; // add it to the answer
			} else {// otherwise
				break;// stop looking
			}
		}
		return commonPath;
	}
}
