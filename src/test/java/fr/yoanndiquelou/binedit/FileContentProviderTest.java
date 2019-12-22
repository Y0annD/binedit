package fr.yoanndiquelou.binedit;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ResourceBundle;

import javax.swing.tree.DefaultMutableTreeNode;

import org.junit.jupiter.api.Test;

import fr.yoanndiquelou.binedit.model.FileContentProvider;

public class FileContentProviderTest {
	/** File content provider. */
	private FileContentProvider mProvider;

	public FileContentProviderTest() {
		mProvider = new FileContentProvider();
	}

	@Test
	public void step00_testGetRoot() {
		assertTrue(mProvider.getRoot() instanceof DefaultMutableTreeNode);
		String rootText = ResourceBundle.getBundle("fr.yoanndiquelou.binedit.panel.ExplorerBundle").getString("DRIVES");
		assertEquals(rootText, ((DefaultMutableTreeNode) mProvider.getRoot()).toString());
	}

	@Test
	public void step01_testGetChild() {
		assertNull(mProvider.getChild(null, 0));
		File[] roots = File.listRoots();
		for (int i = 0; i < roots.length; i++) {
			assertEquals((roots[i].getName().isEmpty() ? "/" : roots[i].getName()),
					mProvider.getChild(mProvider.getRoot(), i).toString());
		}
	}

	@Test
	public void step02_testGetChildCount() {
		assertEquals(0, mProvider.getChildCount(null));
		File[] roots = File.listRoots();

		assertEquals(roots[0].list().length, mProvider.getChildCount(mProvider.getChild(mProvider.getRoot(), 0)));
	}

	@Test
	public void step03_testGetIndexOfChild() {
		assertEquals(0, mProvider.getIndexOfChild(File.listRoots()[0], File.listRoots()[0].listFiles()[0]));
	}

	@Test
	public void step04_onlyForCoverage() {
		mProvider.removeTreeModelListener(null);
		mProvider.valueForPathChanged(null, null);
	}
}
