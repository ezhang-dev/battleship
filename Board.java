import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Board extends JPanel { //battleship board

	private JLayeredPane pane = new JLayeredPane();
	// layers
	private BackgroundBoard background = new BackgroundBoard();
	private ShipBoard shipboard;
	private ShotBoard shotboard;
	private boolean isPlayer;

	public Board(boolean isPlayer) {
		this.isPlayer = isPlayer;
		if (isPlayer) { // save board for later access
			Global.pane = pane; // save for drag and drop
			Global.human = this;
		} else {
			Global.computer = this;
		}
		// board config
		setBorder(BorderFactory.createTitledBorder(isPlayer ? "Player Board" : "Computer Board")); // title
																									// of
																									// board
		setPreferredSize(new Dimension(197, 210));
		setOpaque(true);
		setLayout(new GridLayout(1, 1, 0, 0));

		// add layers to pane
		pane.add(background, new Integer(0));

		shipboard = new ShipBoard(isPlayer);
		pane.add(shipboard, new Integer(1));

		// add pane to board
		add(pane);
	}

	// add shotboard layer
	public void addShotBoard() {
		shotboard = new ShotBoard(isPlayer);
		pane.add(shotboard, new Integer(2));
	}

	// shipboard pass-through
	public void valid() {
		shipboard.valid();
	}

	public int alive() {
		return shipboard.alive();
	}

	public ArrayList<Boolean> shoot(ArrayList<Shot> pending) {
		return shipboard.shoot(pending);

	}

	public void placeShips(int[][] ships) {
		shipboard.placeShips(ships);
	}
	
	public void showShip(){
		shipboard.showShip();
	}

	// get shots of board for AI
	public Shot[][] getShots() {
		return shotboard.getShots();
	}

}
