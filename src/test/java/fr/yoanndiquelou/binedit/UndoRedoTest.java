package fr.yoanndiquelou.binedit;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class UndoRedoTest extends DefaultUITest {
	/** File used for test. */
	private static final String FILE_STR = String.join(File.separator, "test-resources", "test.bin");
	private static final File file = new File(new File(FILE_STR).getAbsolutePath());

	public void localSetUp() {
		resetPreferences();
	}

	@Test
	public void step01_testUndoToolbar() {
		sleep(500);
		mWindow.button("open.button").click();
		mWindow.fileChooser().setCurrentDirectory(file);
		mWindow.fileChooser().selectFile(file);
		mWindow.fileChooser().approve();

		mWindow.menuItem("menu.edit").click();
		mWindow.menuItem("menu.edit.undo").requireEnabled();
		mWindow.menuItem("menu.edit.redo").requireDisabled();
		mWindow.button("undo.button").requireEnabled();
		mWindow.button("redo.button").requireDisabled();
		mWindow.button("undo.button").click();
	}

	@Test
	public void step02_testRedoToolbar() {
		mWindow.menuItem("menu.edit.undo").requireDisabled();
		mWindow.menuItem("menu.edit.redo").requireEnabled();
		mWindow.button("undo.button").requireDisabled();
		mWindow.button("redo.button").requireEnabled();
		mWindow.button("redo.button").click();
	}

	@Test
	public void step03_testUndoMenu() {
		sleep(500);
		mWindow.button("undo.button").requireEnabled();
		mWindow.button("redo.button").requireDisabled();
		mWindow.menuItem("menu.edit").click();
		mWindow.menuItem("menu.edit.undo").requireEnabled();
		mWindow.menuItem("menu.edit.redo").requireDisabled();
		mWindow.menuItem("menu.edit.undo").click();
	}

	@Test
	public void step04_testRedoMenu() {
		sleep(500);
		mWindow.button("undo.button").requireDisabled();
		mWindow.button("redo.button").requireEnabled();
		mWindow.menuItem("menu.edit").click();
		mWindow.menuItem("menu.edit.undo").requireDisabled();
		mWindow.menuItem("menu.edit.redo").requireEnabled();
		mWindow.menuItem("menu.edit.redo").click();

		mWindow.internalFrame().close();
	}
}
