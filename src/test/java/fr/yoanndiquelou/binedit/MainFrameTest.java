package fr.yoanndiquelou.binedit;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;

import org.assertj.swing.data.TableCell;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.fixture.JInternalFrameFixture;
import org.assertj.swing.fixture.JToolBarFixture;
import org.assertj.swing.fixture.JTreeFixture;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class MainFrameTest extends DefaultUITest {

	public void localSetUp() {
		resetPreferences();
	}

	@Test
	public void step01_testInitialisation() {
		mWindow.requireTitle("BinEdit");
	}

	@Test
	public void step02_testOpenFile() {
		mWindow.menuItem("menu.file").requireVisible();
		mWindow.menuItem("menu.file").click();
		mWindow.menuItem("menu.file.open").requireVisible();
		mWindow.menuItem("menu.file.open").click();
		mWindow.fileChooser().selectFile(new File("/Users/yoann/Devoxx2019.md"));
		mWindow.fileChooser().cancel();
		boolean found;
		try {
			mWindow.internalFrame("BinaryViewer_yoann/Devoxx2019.md");
			found = true;
		} catch (ComponentLookupException e) {
			found = false;
		}
		Assert.assertFalse(found);
		mWindow.menuItem("menu.file.open").requireVisible();
		mWindow.menuItem("menu.file.open").click();
		mWindow.fileChooser().selectFile(new File("/Users/yoann/Devoxx2019.md"));
		mWindow.fileChooser().approve();
		mWindow.internalFrame("BinaryViewer_yoann/Devoxx2019.md").close();
		mWindow.button("open.button").click();
		mWindow.fileChooser().selectFile(new File("/Users/yoann/Devoxx2019.md"));
		mWindow.fileChooser().approve();
		mWindow.internalFrame("BinaryViewer_yoann/Devoxx2019.md").close();
	}

	@Test
	public void step03_displayMenu() {

		mWindow.menuItem("menu.display").click();
		mWindow.menuItem("menu.display.toolbar").requireVisible().requireEnabled();
		mWindow.menuItem("menu.display.displaybar").requireVisible().requireDisabled();
		mWindow.menuItem("menu.display.statusbar").requireVisible().requireDisabled();
		mWindow.menuItem("menu.display.font").requireVisible().requireDisabled();
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
		mWindow.fileChooser().selectFile(new File("/Users/yoann/Devoxx2019.md"));
		mWindow.fileChooser().approve();
		JInternalFrameFixture binaryViewer = mWindow.internalFrame("BinaryViewer_yoann/Devoxx2019.md");
		binaryViewer.table("ContentTable").requireColumnCount(Settings.getBytesPerLine() * 2 + 1);

		// Test displayAddress
		mWindow.menuItem("menu.display").click();
		mWindow.menuItem("menu.display.address").click();
		binaryViewer.table("ContentTable").requireColumnCount(Settings.getBytesPerLine() * 2);
		mWindow.menuItem("menu.display").click();
		mWindow.menuItem("menu.display.address").click();
		binaryViewer.table("ContentTable").requireColumnCount(Settings.getBytesPerLine() * 2 + 1);

		// Test address hexa
		binaryViewer.table("ContentTable").requireCellValue(TableCell.row(1).column(0), "0x0018");
		mWindow.menuItem("menu.display").click();
		mWindow.menuItem("menu.display.address.hexa").click();
		binaryViewer.table("ContentTable").requireCellValue(TableCell.row(1).column(0), "000024");
		mWindow.menuItem("menu.display").click();
		mWindow.menuItem("menu.display.address.hexa").click();
		binaryViewer.table("ContentTable").requireCellValue(TableCell.row(1).column(0), "0x0018");

		// Test info hexa
		binaryViewer.table("ContentTable").requireCellValue(TableCell.row(0).column(1), "23");
		mWindow.menuItem("menu.display").click();
		mWindow.menuItem("menu.display.info.hexa").click();
		binaryViewer.table("ContentTable").requireCellValue(TableCell.row(0).column(1), "35");
		mWindow.menuItem("menu.display").click();
		mWindow.menuItem("menu.display.info.hexa").click();
		binaryViewer.table("ContentTable").requireCellValue(TableCell.row(0).column(1), "23");

		binaryViewer.close();
	}

	@Test
	public void step04_singleSelection() {
		mWindow.menuItem("menu.file.open").requireVisible();
		mWindow.menuItem("menu.file.open").click();
		mWindow.fileChooser().selectFile(new File("/Users/yoann/Devoxx2019.md"));
		mWindow.fileChooser().approve();
		JInternalFrameFixture binaryViewer = mWindow.internalFrame("BinaryViewer_yoann/Devoxx2019.md");
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

		binaryViewer.close();
	}

	@Test
	public void step05_testTree() {
		JTreeFixture tree = mWindow.tree("Tree");
		tree.replaceSeparator("-");
		System.out.println("Separator: " +tree.separator());
		tree.toggleRow(1);
		System.out.println(tree.node(1).value());
		tree.expandPath("Tous les disques-/-Users");
		tree.expandPath("Tous les disques-/-Users-yoann");
		tree.selectPath("Tous les disques-/-Users-yoann-Devoxx2019.md");
		tree.drag("Tous les disques-/-Users-yoann-Devoxx2019.md");
		Point p = tree.target().getLocation();
		p.setLocation(p.x+tree.target().getWidth()+20, p.y+20);
		mWindow.drop();
		mWindow.internalFrame("BinaryViewer_yoann/Devoxx2019.md").close();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
