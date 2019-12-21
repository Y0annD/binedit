package fr.yoanndiquelou.binedit;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.launcher.ApplicationLauncher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import fr.yoanndiquelou.binedit.panel.BinaryViewer;

/**
 * Abstract test class for UI test.
 * 
 * @author yoann
 *
 */
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public abstract class DefaultUITest {
	/** UI Robot. */
	protected Robot mRobot;
	/** Window fixture. */
	protected FrameFixture mWindow;

	@BeforeAll
	public void onSetUp() {
		localSetUp();
		mRobot = BasicRobot.robotWithCurrentAwtHierarchy();
		ApplicationLauncher.application(App.class).start();
		mWindow = WindowFinder.findFrame(MainFrame.class).using(mRobot);
		
		FailOnThreadViolationRepaintManager.install();
	}

	@BeforeEach
	public void beforeEach() {
		resetPreferences();
	}

	public abstract void localSetUp();

	@AfterAll
	public void tearDown() {
		mWindow.cleanUp();
	}

	public void resetPreferences() {
		try {
			Preferences.userRoot().node(Settings.class.getName()).clear();
			Preferences.userRoot().node(BinaryViewer.class.getName()).clear();
			Preferences.userRoot().clear();
			Preferences.systemRoot().clear();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}
}
