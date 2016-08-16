import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Global { // global storage and functions

	// state
	public static int mode = 1;
	public static int state = 0;
	public static int shotsLeft = 5;
	// board
	public static Board human;
	public static Board computer;
	// DnD
	public static DnD dnd;
	public static JLayeredPane pane;
	// Global components
	public static JButton button;
	public static StatusBoard statusboard;
	public static JPanel window;
	public static JLabel statusbar = new JLabel();
	public static Game game;

	private Global() { // no objects
	}

	public static void onShotChange() { // triggered after shots
										// selected/unselected
		shotsLeft = (mode == 1 ? human.alive():1) - Shot.getPending().size(); // shots
																				// remaining
																				// based
																				// on
																				// game
																				// mode
		button.setEnabled(shotsLeft == 0); // enable when no shots remaining
		statusbar.setText(
				"<html><p>" + Shot.getPending().size() + " shot(s) selected, " + shotsLeft + " remaining.</p></html>"); // statusbar
	}

	public static void onShipChange() {// on shot move, rotate
		human.valid();// validate human ship placement
		button.setEnabled(Ship.allValid()); // enable if all valid
		if (!Ship.allValid()) {
			statusbar.setText("<html><p> Ship placement is invalid.</p></html>");
		} else {
			statusbar.setText("<html><p>Move ships by dragging. Right-click to rotate.</p></html>");
		}
	}

	public static void reset() {// reset all static vars
		// other classes
		Ship.reset();
		Shot.clear();
		//Globals
		state = 0;
		shotsLeft = 5;
		
		window.remove(game);
		game=new Game();
		window.add(game);
		
	}
}
