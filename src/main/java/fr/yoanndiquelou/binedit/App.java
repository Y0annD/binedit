package fr.yoanndiquelou.binedit;

import javax.swing.SwingUtilities;

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
