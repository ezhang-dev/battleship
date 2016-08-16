import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main {//Main program entry point
	private static ImageIcon icon = new ImageIcon(Main.class.getResource("/res/icon.gif"));

	private static void createAndShowGui() { //create the window

		JFrame frame = new JFrame("Battleship"); //title
		frame.setIconImage(icon.getImage()); // icon
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(new MenuBar());//menubar
		Global.window=(JPanel) frame.getContentPane();
		Global.window.setLayout(new BorderLayout());
		Global.game=new Game();
		Global.window.add(Global.game,BorderLayout.CENTER); //game in center
		Global.window.add(Global.statusbar,BorderLayout.SOUTH); //status bar at bottom
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}

	public static void main(String[] args) { //Main function
		SwingUtilities.invokeLater(() -> {
			createAndShowGui();
		});
	}

}
