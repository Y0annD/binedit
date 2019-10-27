package fr.yoanndiquelou.binedit;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class MainFrameTest {
	
	private FrameFixture window;

	@BeforeAll
	public void setUp() {
		MainFrame frame = GuiActionRunner.execute(() ->new MainFrame());
		window = new FrameFixture(frame);
		window.show(); // shows the frame to test
	}
	
	@Test
	public void testInitialisation() {
		window.requireTitle("BinEdit");
	}
}
