package fr.yoanndiquelou.binedit.panel;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class FileContentProvider implements TreeModel {

	/** Resources bundle for i18n. */
	private ResourceBundle mBundle = ResourceBundle.getBundle("fr.yoanndiquelou.binedit.panel.ExplorerBundle");

	private DefaultMutableTreeNode root = new DefaultMutableTreeNode(mBundle.getString("DRIVES"));

	public FileContentProvider() {
		File[] drives = File.listRoots();
		for (File drive : drives) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(new FileNode(drive));
			root.add(node);
		}
	}

	@Override
	public Object getRoot() {
		return root;
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {

	}

	@Override
	public Object getChild(Object parent, int index) {
		if (parent == null)
			return null;
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) parent;
		return node.getChildAt(index);
	}

	@Override
	public int getChildCount(Object parent) {
		if (parent == null) {
			return 0;
		} else {
			DefaultMutableTreeNode node = ((DefaultMutableTreeNode) parent);
			if (node.getChildCount() > 0) {
				return node.getChildCount();
			} else {
				File f = new File(node.toString());
				File[] childs = f.listFiles();
				if (null != childs) {
					for (File child : childs) {
						node.add(new DefaultMutableTreeNode(new FileNode(child)));
					}
					return childs.length;
				} else {
					return 0;
				}
			}
		}
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		List<File> list = Arrays.asList(((File) parent).listFiles());
		return list.indexOf(child);
	}

	@Override
	public boolean isLeaf(Object node) {
		DefaultMutableTreeNode mutableNode = (DefaultMutableTreeNode) node;
		if (node.equals(root)) {
			return false;
		} else {
			File f = ((FileNode) mutableNode.getUserObject()).getFile();
			return !f.isDirectory();
		}
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {

	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {

	}

}
