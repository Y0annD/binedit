package fr.yoanndiquelou.binedit.panel;

import java.awt.BorderLayout;
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

import fr.yoanndiquelou.binedit.handler.FileTransferHandler;

/**
 * Explorateur de fichiers.
 * 
 * @author yoann
 *
 */
public class ExplorerPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2415755023812800243L;

	private DefaultTreeModel mTreeModel;

	/**
	 * Explorateur de fichier
	 */
	public ExplorerPanel() {
//		mTreeModel = new DefaultTreeModel();
		TreeModel model = new FileContentProvider();
		JTree tree = new JTree(model);
		tree.setTransferHandler(new FileTransferHandler());
		tree.addTreeWillExpandListener(new TreeWillExpandListener() {

			@Override
			public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
//				JTree tree = (JTree) event.getSource();
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
			}
		});
		tree.setDragEnabled(true);
		tree.setShowsRootHandles(true);
		JScrollPane scroll = new JScrollPane(tree);
		setLayout(new BorderLayout());
		add(scroll);
//		for(File drive: getAllDrives()) {
//		CreateChildNodes ccn = 
//                new CreateChildNodes(drive, node);
//        new Thread(ccn).start();
//		}
	}

	/**
	 * Get list of drives.
	 * 
	 * @return
	 */
//	private File[] getAllDrives() {
//		return File.listRoots();
//	}
//
//	private ArrayList getAllDirectories(File file) {
//
//		ArrayList<File> directories = new ArrayList<>();
//		File[] allSub = file.listFiles();
//		if (allSub != null) {
//			for (File sub : allSub) {
//				if (sub.isDirectory()) {
//					directories.add(sub);
//				}
//			}
//		}
//		return directories;
//	}

	/**
	 * Structure de fichier de l'ordinateur.
	 * 
	 * @return structure de fichiers
	 */
//	private DefaultMutableTreeNode getTreeStructure() {
//		File[] roots = getAllDrives();
//		DefaultMutableTreeNode allDrives = new DefaultMutableTreeNode(mBundle.getString("DRIVES"));
//		for (File root : roots) {
//			DefaultMutableTreeNode drive = new DefaultMutableTreeNode(root);
//			ArrayList<File> folderNodes = getAllDirectories(root);

//			for (File folderNode : folderNodes) {
//				DefaultMutableTreeNode childDrive = new DefaultMutableTreeNode(folderNode.getName());
////				File[] files = folderNode.listFiles();
////				if (null != files) {
////
////					for (File txt : folderNode.listFiles()) {
////						childDrive.add(new DefaultMutableTreeNode(txt));
////					}
////				}
//				drive.add(childDrive);
//			}
//			allDrives.add(drive);
//		}
//		return allDrives;
//	}
//	
//	public class CreateChildNodes implements Runnable {
//
//        private DefaultMutableTreeNode root;
//
//        private File fileRoot;
//
//        public CreateChildNodes(File fileRoot, 
//                DefaultMutableTreeNode root) {
//            this.fileRoot = fileRoot;
//            this.root = root;
//        }
//
//        @Override
//        public void run() {
//            createChildren(fileRoot, root);
//        }
//
//        private void createChildren(File fileRoot, 
//                DefaultMutableTreeNode node) {
//            File[] files = fileRoot.listFiles();
//            if (files == null) return;
//
//            for (File file : files) {
//                DefaultMutableTreeNode childNode = 
//                        new DefaultMutableTreeNode(new FileNode(file));
//                node.add(childNode);
//                if (file.isDirectory()) {
//                	System.out.println(file.getAbsolutePath());
//                    createChildren(file, childNode);
//                }
//            }
//        }
//
//    }
//

}
