package fr.yoanndiquelou.binedit.panel;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import fr.yoanndiquelou.binedit.AppController;
import fr.yoanndiquelou.binedit.handler.FileTransferHandler;
import fr.yoanndiquelou.binedit.model.FileContentProvider;

/**
 * File explorer.
 * 
 * @author yoann
 *
 */
public class ExplorerPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2415755023812800243L;

	/**
	 * File explorer.
	 */
	public ExplorerPanel() {
		TreeModel model = new FileContentProvider();
		JTree tree = new JTree(model);
		tree.setName("Tree");
		tree.setTransferHandler(new FileTransferHandler());
		tree.addTreeWillExpandListener(new TreeWillExpandListener() {

			@Override
			public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
				if (node.getChildCount() == 0) {

					File file = ((FileNode) node.getUserObject()).getFile();
					if (file.isDirectory()) {
						File[] childs = file.listFiles();
						for (File child : childs) {
							node.add(new DefaultMutableTreeNode(new FileNode(child)));
						}
					}
				}

			}

			@Override
			public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
				// We don't care
			}
		});
		tree.setDragEnabled(true);
		tree.setShowsRootHandles(true);
		tree.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int selRow = tree.getRowForLocation(e.getX(), e.getY());
				TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
				if (selRow != -1&&e.getClickCount() == 2 && !((FileNode)((DefaultMutableTreeNode)selPath.getLastPathComponent()).getUserObject()).getFile().isDirectory()) {
						AppController.getInstance().openFile(((FileNode)((DefaultMutableTreeNode)selPath.getLastPathComponent()).getUserObject()).getFile());
				}
			}
		});
		JScrollPane scroll = new JScrollPane(tree);
		setLayout(new BorderLayout());
		add(scroll);
	}

}
