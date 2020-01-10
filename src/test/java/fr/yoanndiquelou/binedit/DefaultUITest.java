package fr.yoanndiquelou.binedit;

import java.security.Permission;
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
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import fr.yoanndiquelou.binedit.panel.BinaryViewer;

/**
 * Abstract test class for UI test.
 * 
 * @author yoann
 *
 */
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@TestInstance(Lifecycle.PER_CLASS)
public abstract class DefaultUITest {
	/** UI Robot. */
	protected Robot mRobot;
	/** Window fixture. */
	protected FrameFixture mWindow;

	protected static class ExitException extends SecurityException {
		public final int status;

		public ExitException(int status) {
			super("There is no escape!");
			this.status = status;
		}
	}

	private static class NoExitSecurityManager extends SecurityManager {
		@Override
		public void checkPermission(Permission perm) {
			// allow anything.
		}

		@Override
		public void checkPermission(Permission perm, Object context) {
			// allow anything.
		}

		@Override
		public void checkExit(int status) {
			super.checkExit(status);
			throw new ExitException(status);
		}
	}

	@BeforeAll
	public void onSetUp() {
		localSetUp();
		mRobot = BasicRobot.robotWithCurrentAwtHierarchy();
		ApplicationLauncher.application(App.class).start();
		sleep(5000);
		mWindow = WindowFinder.findFrame(MainFrame.class).using(mRobot);

		FailOnThreadViolationRepaintManager.install();
		System.setSecurityManager(new NoExitSecurityManager());
	}

	public void sleep(long timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@BeforeEach
	public void beforeEach() {
		resetPreferences();
	}

	public abstract void localSetUp();

	@AfterAll
	public void tearDown() {
		if (null != mWindow) {
			mWindow.close();
			mWindow.cleanUp();
		}
		if (null != mRobot) {
			mRobot.cleanUp();
		}
		System.setSecurityManager(null);
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
