package fr.yoanndiquelou.binedit;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import fr.yoanndiquelou.binedit.laf.BinEditLookAndFeel;
import fr.yoanndiquelou.binedit.laf.ui.BinEditLookAndFeelCustomizer;

/**
 * Hello world!
 *
 */
public class App {
	public static void main( String[] args )
    {

        SwingUtilities.invokeLater(()->{
        	BinEditLookAndFeelCustomizer.customize();
        	MainFrame frame = new MainFrame();	
        });
    }
}
