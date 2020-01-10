package fr.yoanndiquelou.binedit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import org.assertj.swing.data.TableCell;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.fixture.JFileChooserFixture;
import org.assertj.swing.fixture.JInternalFrameFixture;
import org.assertj.swing.fixture.JPanelFixture;
import org.assertj.swing.fixture.JToolBarFixture;
import org.assertj.swing.fixture.JTreeFixture;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import fr.yoanndiquelou.binedit.panel.InfoPanel;

@TestInstance(Lifecycle.PER_CLASS)
public class MainFrameTest extends DefaultUITest {
	/** File used for test. */
	private static final String FILE_STR = String.join(File.separator, "test-resources", "test.bin");
	private static final File file = new File(new File(FILE_STR).getAbsolutePath());
	private static final String TREE_SEPARATOR = "|";

	public void localSetUp() {
		resetPreferences();
	}

	@Test
	public void step01_testInitialisation() {
		mWindow.requireTitle("BinEdit");
	}

	@Test
	public void step02_displayMenu() {
		mWindow.menuItem("menu.edit").click();
		mWindow.menuItem("menu.edit.undo").requireVisible().requireDisabled();
		mWindow.menuItem("menu.edit.redo").requireVisible().requireDisabled();
		mWindow.menuItem("menu.edit.cut").requireVisible().requireDisabled();
		mWindow.menuItem("menu.edit.copy.binary").requireVisible().requireDisabled();
		mWindow.menuItem("menu.edit.copy.text").requireVisible().requireDisabled();
		mWindow.menuItem("menu.edit.paste").requireVisible().requireDisabled();
		mWindow.menuItem("menu.edit.deleteSelection").requireVisible().requireDisabled();
		mWindow.menuItem("menu.edit.selectAll").requireVisible().requireDisabled();
		mWindow.menuItem("menu.edit.select").requireVisible().requireDisabled();
		mWindow.menuItem("menu.edit.search").requireVisible().requireDisabled();
		mWindow.menuItem("menu.edit.next").requireVisible().requireDisabled();
		mWindow.menuItem("menu.edit.previous").requireVisible().requireDisabled();
		mWindow.menuItem("menu.edit.replace").requireVisible().requireDisabled();
		mWindow.menuItem("menu.edit.fill").requireVisible().requireDisabled();
		mWindow.menuItem("menu.edit.insert").requireVisible().requireDisabled();
		mWindow.menuItem("menu.edit.delete").requireVisible().requireDisabled();
		mWindow.menuItem("menu.edit.goto").requireVisible().requireDisabled();
		mWindow.menuItem("menu.edit.externalEditor").requireVisible().requireDisabled();

		mWindow.menuItem("menu.display").click();
		mWindow.menuItem("menu.display.toolbar").requireVisible().requireEnabled();
		mWindow.menuItem("menu.display.displaybar").requireVisible().requireDisabled();
		mWindow.menuItem("menu.display.statusbar").requireVisible().requireEnabled();
		mWindow.menuItem("menu.display.font").requireVisible().requireEnabled();
		mWindow.menuItem("menu.display.color").requireVisible().requireDisabled();
		mWindow.menuItem("menu.display.address").requireVisible().requireEnabled();
		mWindow.menuItem("menu.display.address.hexa").requireVisible().requireEnabled();
		mWindow.menuItem("menu.display.info.hexa").requireVisible().requireEnabled();
		mWindow.menuItem("menu.display.info.line").requireVisible().requireEnabled();
		JToolBarFixture toolbar = mWindow.toolBar("Toolbar");
		mWindow.menuItem("menu.display.toolbar").click();
		toolbar.requireNotVisible();
		mWindow.menuItem("menu.display").click();
		mWindow.menuItem("menu.display.toolbar").click();
		toolbar.requireVisible();

		mWindow.button("open.button").click();
		mWindow.fileChooser().setCurrentDirectory(file);
		mWindow.fileChooser().selectFile(file);
		mWindow.fileChooser().approve();
		JInternalFrameFixture binaryViewer = mWindow.internalFrame("BinaryViewer_" + FILE_STR);
		binaryViewer.table("ContentTable").requireColumnCount(Settings.getBytesPerLine() * 2 + 1);

		mWindow.menuItem("menu.edit").click();
		mWindow.menuItem("menu.edit.undo").requireEnabled().click();

		mWindow.menuItem("menu.edit").click();
		mWindow.menuItem("menu.edit.redo").requireEnabled().click();

		binaryViewer = mWindow.internalFrame("BinaryViewer_" + FILE_STR);
		// Test displayAddress
		mWindow.menuItem("menu.display").click();
		mWindow.menuItem("menu.display.address").click();
		binaryViewer.table("ContentTable").requireColumnCount(Settings.getBytesPerLine() * 2);
		mWindow.menuItem("menu.display").click();
		mWindow.menuItem("menu.display.address").click();
		binaryViewer.table("ContentTable").requireColumnCount(Settings.getBytesPerLine() * 2 + 1);

		// Test address hexa
		binaryViewer.table("ContentTable").requireCellValue(TableCell.row(1).column(0), "0x000018");
		mWindow.menuItem("menu.display").click();
		mWindow.menuItem("menu.display.address.hexa").click();
		binaryViewer.table("ContentTable").requireCellValue(TableCell.row(1).column(0), "00000024");
		mWindow.menuItem("menu.display").click();
		mWindow.menuItem("menu.display.address.hexa").click();
		binaryViewer.table("ContentTable").requireCellValue(TableCell.row(1).column(0), "0x000018");

		// Test info hexa
		binaryViewer.table("ContentTable").requireCellValue(TableCell.row(1).column(3), "1A");
		mWindow.menuItem("menu.display").click();
		mWindow.menuItem("menu.display.info.hexa").click();
		binaryViewer.table("ContentTable").requireCellValue(TableCell.row(1).column(3), "26");

		mWindow.menuItem("menu.edit").click();
		mWindow.menuItem("menu.edit.undo").click();
		mWindow.menuItem("menu.edit").click();
		mWindow.menuItem("menu.edit.redo").click();
		mWindow.menuItem("menu.display").click();
		mWindow.menuItem("menu.display.info.hexa").click();
		binaryViewer.table("ContentTable").requireCellValue(TableCell.row(1).column(3), "1A");

		// Test display status bar
		JPanelFixture infoPanel = binaryViewer.panel(InfoPanel.INFO_PANEL_NAME);
		infoPanel.requireVisible();
		mWindow.menuItem("menu.display").click();
		mWindow.menuItem("menu.display.statusbar").click();
		infoPanel.requireNotVisible();
		mWindow.menuItem("menu.display").click();
		mWindow.menuItem("menu.display.statusbar").click();
		infoPanel.requireVisible();
		binaryViewer.close();
	}

	@Test
	public void step03_testOpenFile() {
		mWindow.menuItem("menu.file").requireVisible();
		mWindow.menuItem("menu.file").click();
		mWindow.menuItem("menu.file.open").requireVisible();
		mWindow.menuItem("menu.file.open").click();

		System.out.println(file.getAbsolutePath());
		mWindow.fileChooser().setCurrentDirectory(file);
		mWindow.fileChooser().selectFile(file);
		mWindow.fileChooser().cancel();
		boolean found;
		try {
			mWindow.internalFrame("BinaryViewer_" + FILE_STR);
			found = true;
		} catch (ComponentLookupException e) {
			found = false;
		}
		Assert.assertFalse(found);
		mWindow.menuItem("menu.file.open").requireVisible();
		mWindow.menuItem("menu.file.open").click();
		JFileChooserFixture fileChooserFixture = mWindow.fileChooser();
		fileChooserFixture.setCurrentDirectory(file);
		fileChooserFixture.selectFile(file);
		fileChooserFixture.approveButton().requireEnabled();
		fileChooserFixture.approve();
		mWindow.internalFrame("BinaryViewer_" + FILE_STR).close();
		mWindow.button("open.button").click();
		mWindow.fileChooser().setCurrentDirectory(file);
		mWindow.fileChooser().selectFile(file);
		mWindow.fileChooser().approve();
		mWindow.internalFrame("BinaryViewer_" + FILE_STR).close();
	}

	@Test
	public void step04_singleSelection() {
		mWindow.menuItem("menu.file.open").requireVisible();
		mWindow.menuItem("menu.file.open").click();
		mWindow.fileChooser().setCurrentDirectory(file);
		mWindow.fileChooser().selectFile(file);
		mWindow.fileChooser().approve();
		JInternalFrameFixture binaryViewer = mWindow.internalFrame("BinaryViewer_" + FILE_STR);
		binaryViewer.label("ShiftLabel").requireText("");
		binaryViewer.label("AddrLabel").requireText("0");
		binaryViewer.table("ContentTable").pressKey(KeyEvent.VK_SHIFT).cell(TableCell.row(1).column(0)).click();
		binaryViewer.table("ContentTable").pressKey(KeyEvent.VK_SHIFT).cell(TableCell.row(2).column(0)).click();
		binaryViewer.table("ContentTable").releaseKey(KeyEvent.VK_SHIFT);
		binaryViewer.label("AddrLabel").requireText("[0X00;0X30]");

		// Select in text data
		binaryViewer.table("ContentTable").cell(TableCell.row(2).column(25)).click();
		binaryViewer.table("ContentTable").pressKey(KeyEvent.VK_SHIFT).cell(TableCell.row(4).column(28)).click();
		binaryViewer.table("ContentTable").releaseKey(KeyEvent.VK_SHIFT);
		binaryViewer.label("AddrLabel").requireText("[0X30;0X63]");

		// from bottom to up
		binaryViewer.table("ContentTable").cell(TableCell.row(3).column(2)).click();
		binaryViewer.table("ContentTable").pressKey(KeyEvent.VK_SHIFT).cell(TableCell.row(1).column(1)).click();
		binaryViewer.table("ContentTable").releaseKey(KeyEvent.VK_SHIFT);
		binaryViewer.label("AddrLabel").requireText("[0X18;0X49]");

		// from bottom to up
		binaryViewer.table("ContentTable").cell(TableCell.row(3).column(2)).click();
		binaryViewer.table("ContentTable").pressKey(KeyEvent.VK_SHIFT).cell(TableCell.row(1).column(3)).click();
		binaryViewer.table("ContentTable").releaseKey(KeyEvent.VK_SHIFT);
		binaryViewer.label("AddrLabel").requireText("[0X1A;0X49]");

		// from bottom to up
		binaryViewer.table("ContentTable").cell(TableCell.row(1).column(2)).click();
		binaryViewer.table("ContentTable").pressKey(KeyEvent.VK_SHIFT).cell(TableCell.row(3).column(1)).click();
		binaryViewer.table("ContentTable").releaseKey(KeyEvent.VK_SHIFT);
		binaryViewer.label("AddrLabel").requireText("[0X19;0X48]");

		// Test selection without showing addresses.
		mWindow.menuItem("menu.display").click();
		mWindow.menuItem("menu.display.address").click();

		binaryViewer.table("ContentTable").cell(TableCell.row(1).column(2)).click();
		binaryViewer.table("ContentTable").pressKey(KeyEvent.VK_SHIFT).cell(TableCell.row(3).column(1)).click();
		binaryViewer.table("ContentTable").releaseKey(KeyEvent.VK_SHIFT);
		binaryViewer.label("AddrLabel").requireText("[0X1A;0X49]");
		mWindow.menuItem("menu.display").click();
		mWindow.menuItem("menu.display.address").click();
		// Addresses in base10
		mWindow.menuItem("menu.display").click();
		mWindow.menuItem("menu.display.address.hexa").click();
		binaryViewer.label("AddrLabel").requireText("[26;73]");
		mWindow.menuItem("menu.display").click();
		mWindow.menuItem("menu.display.address.hexa").click();
		binaryViewer.close();
	}

	@Test
	public void step05_testTree() {
		JTreeFixture tree = mWindow.tree("Tree");
		tree.replaceSeparator(TREE_SEPARATOR);
		tree.toggleRow(1);
		String path = ResourceBundle.getBundle("fr.yoanndiquelou.binedit.panel.ExplorerBundle").getString("DRIVES");
		path = path.concat(TREE_SEPARATOR);
		String[] filePath = file.getAbsolutePath().split(File.separator);
		if (file.getAbsolutePath().startsWith("/")) {
			path = path.concat("/").concat(TREE_SEPARATOR);
		}
		tree.expandPath(path);
		for (int i = 0; i < filePath.length - 1; i++) {
			if (!filePath[i].isEmpty()) {
				path = path.concat(filePath[i]).concat(TREE_SEPARATOR);
				tree.expandPath(path);
			}
		}
		path = path.concat(filePath[filePath.length - 1]);
		tree.selectPath(path);
		tree.drag(path);
		Point p = tree.target().getLocation();
		p.setLocation(p.x + tree.target().getWidth() + 20, p.y + 20);
		mWindow.drop();
		mWindow.internalFrame("BinaryViewer_" + FILE_STR).close();
		tree.doubleClickPath(path);
		mWindow.internalFrame("BinaryViewer_" + FILE_STR).close();
		tree.clickPath(path.substring(0, path.lastIndexOf(TREE_SEPARATOR)));
	}

	@Test
	public void step06_testViewerSettingsFrame() {
		mWindow.menuItem("menu.file.open").requireVisible();
		mWindow.menuItem("menu.file.open").click();
		mWindow.fileChooser().setCurrentDirectory(file);
		mWindow.fileChooser().selectFile(file);
		mWindow.fileChooser().approve();
		JInternalFrameFixture binaryViewer = mWindow.internalFrame("BinaryViewer_" + FILE_STR);
		mWindow.menuItem("menu.display").requireVisible();
		mWindow.menuItem("menu.display.info.line").click();
		DialogFixture settingsFrame = mWindow.dialog("Preferences");
		settingsFrame.spinner("WORDS_PER_LINE").decrement().decrement().decrement().decrement();
		settingsFrame.button("CONFIRM").click();
		assertEquals(41, binaryViewer.table("ContentTable").target().getColumnCount());

		mWindow.menuItem("menu.display").requireVisible();
		mWindow.menuItem("menu.display.info.line").click();
		settingsFrame = mWindow.dialog("Preferences");
		settingsFrame.spinner("WORDS_PER_LINE").increment();
		settingsFrame.button("CANCEL").click();
		assertEquals(41, binaryViewer.table("ContentTable").target().getColumnCount());
		mWindow.menuItem("menu.display").requireVisible();
		mWindow.menuItem("menu.display.info.line").click();
		settingsFrame = mWindow.dialog("Preferences");
		settingsFrame.spinner("SHIFT").increment();
		settingsFrame.checkBox("FIXED_COLUMNS").click();
		settingsFrame.button("CONFIRM").click();
		int initialColumnCount = binaryViewer.table("ContentTable").target().getColumnCount();
		binaryViewer.resizeWidthTo(binaryViewer.target().getWidth() + 30);
		binaryViewer.panel(InfoPanel.INFO_PANEL_NAME).label("ShiftLabel").requireText("0X01 (1)");
		assertFalse(initialColumnCount == binaryViewer.table("ContentTable").target().getColumnCount());
		binaryViewer.resizeWidthTo(binaryViewer.target().getWidth() - 30);
		assertTrue(initialColumnCount == binaryViewer.table("ContentTable").target().getColumnCount());
		mWindow.menuItem("menu.display").requireVisible();
		mWindow.menuItem("menu.display.info.line").click();
		settingsFrame = mWindow.dialog("Preferences");
		settingsFrame.checkBox("FIXED_COLUMNS").click();
		settingsFrame.button("CONFIRM").click();
		binaryViewer.close();
	}

	@Test
	public void step07_testScroll() {
		mWindow.menuItem("menu.file.open").requireVisible();
		mWindow.menuItem("menu.file.open").click();
		mWindow.fileChooser().setCurrentDirectory(file);
		mWindow.fileChooser().selectFile(file);
		mWindow.fileChooser().approve();
		JInternalFrameFixture binaryViewer = mWindow.internalFrame("BinaryViewer_" + FILE_STR);
		binaryViewer.scrollBar("ContentScroll_Vertical").scrollToMaximum();
		binaryViewer.close();
	}

	@Test
	public void step08_testGoTo() {
		mWindow.menuItem("menu.file.open").requireVisible();
		mWindow.menuItem("menu.file.open").click();
		mWindow.fileChooser().setCurrentDirectory(file);
		mWindow.fileChooser().selectFile(file);
		mWindow.fileChooser().approve();
		JInternalFrameFixture binaryViewer = mWindow.internalFrame("BinaryViewer_" + FILE_STR);

		mWindow.menuItem("menu.edit").click();
		mWindow.menuItem("menu.edit.goto").requireVisible().requireEnabled().click();

		DialogFixture goToDialog = mWindow.dialog("GoTo");
		goToDialog.textBox("ADDRESS").enterText("0X2AG 0");
		goToDialog.textBox("ADDRESS").requireText("0X2A0");
		goToDialog.button("GOTO_BUTTON").click();
		binaryViewer.scrollBar("ContentScroll_Vertical").requireValue(448);

		mWindow.menuItem("menu.edit").click();
		mWindow.menuItem("menu.edit.undo").requireVisible().requireEnabled().click();

		binaryViewer.scrollBar("ContentScroll_Vertical").requireValue(0);

		mWindow.menuItem("menu.edit").click();
		mWindow.menuItem("menu.edit.redo").requireVisible().requireEnabled().click();

		binaryViewer.scrollBar("ContentScroll_Vertical").requireValue(448);

		mWindow.menuItem("menu.edit").click();
		mWindow.menuItem("menu.edit.goto").requireVisible().requireEnabled().click();

		goToDialog = mWindow.dialog("GoTo");
		goToDialog.textBox("ADDRESS").enterText("0");
		goToDialog.textBox("ADDRESS").requireText("0");
		goToDialog.button("GOTO_BUTTON").click();
		binaryViewer.scrollBar("ContentScroll_Vertical").requireValue(0);

		mWindow.menuItem("menu.edit").click();
		mWindow.menuItem("menu.edit.goto").requireVisible().requireEnabled().click();

		goToDialog = mWindow.dialog("GoTo");
		goToDialog.textBox("ADDRESS").enterText("672");
		goToDialog.textBox("ADDRESS").requireText("672");
		goToDialog.button("GOTO_BUTTON").click();
		binaryViewer.scrollBar("ContentScroll_Vertical").requireValue(448);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		binaryViewer.close();
	}

	@Test
	public void step09_testSetFont() {
		mWindow.menuItem("menu.display").click();
		mWindow.menuItem("menu.display.font").requireVisible().requireEnabled().click();

		DialogFixture fontDialog = mWindow.dialog();
		fontDialog.list("FontList").selectItem("Times New Roman");

		fontDialog.list("FontStyle").selectItem("TimesNewRomanPS-BoldMT");

		fontDialog.spinner().increment();
		fontDialog.spinner().requireValue(13);
		fontDialog.button("cancel").click();

		mWindow.menuItem("menu.display").click();
		mWindow.menuItem("menu.display.font").requireVisible().requireEnabled().click();

		fontDialog = mWindow.dialog();
		fontDialog.list("FontList").selectItem("Times New Roman");
		fontDialog.list("FontList").requireSelection("Times New Roman");
		fontDialog.list("FontStyle").selectItem("TimesNewRomanPS-BoldMT");
		fontDialog.list("FontStyle").requireSelection("TimesNewRomanPS-BoldMT");

		fontDialog.spinner().increment();
		fontDialog.spinner().requireValue(13);
		fontDialog.button("ok").click();
	}

	@Test
	public void step10_testCopyActions() {
		mWindow.menuItem("menu.file.open").requireVisible();
		mWindow.menuItem("menu.file.open").click();
		mWindow.fileChooser().setCurrentDirectory(file);
		mWindow.fileChooser().selectFile(file);
		mWindow.fileChooser().approve();
		JInternalFrameFixture binaryViewer = mWindow.internalFrame("BinaryViewer_" + FILE_STR);

		// from bottom to up
		binaryViewer.table("ContentTable").cell(TableCell.row(3).column(2)).click();
		binaryViewer.table("ContentTable").pressKey(KeyEvent.VK_SHIFT).cell(TableCell.row(1).column(1)).click();
		binaryViewer.table("ContentTable").releaseKey(KeyEvent.VK_SHIFT);
		binaryViewer.label("AddrLabel").requireText("[0X18;0X49]");

		mWindow.menuItem("menu.edit").click();
		mWindow.menuItem("menu.edit.copy.binary").requireVisible().requireEnabled().click();
		// Test clipboard content
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		String data;
		try {
			data = (String) clipboard.getData(DataFlavor.stringFlavor);
			StringBuilder expectedContent = new StringBuilder();
			for(int i = 0x18; i <=0x49;i++) {
				expectedContent.append(String.format("%02x", i));
				if(i<0x49) {
					expectedContent.append(" ");
				}
			}
			assertEquals(expectedContent.toString(), data);
		} catch (UnsupportedFlavorException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Unable to get clipboard content");
		}

	}
}
