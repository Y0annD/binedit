package fr.yoanndiquelou.binedit;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import fr.yoanndiquelou.binedit.laf.BinEditLookAndFeel;

/**
 * Hello world!
 *
 */
public class App {
	public static void main( String[] args )
    {
        try {
        	BinEditLookAndFeel blaf = new BinEditLookAndFeel();
            UIManager.setLookAndFeel(blaf);
          } catch (Exception e) {
            e.printStackTrace();
          }
        SwingUtilities.invokeLater(()->{
        	MainFrame frame = new MainFrame();	
        });
    }
}
