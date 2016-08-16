import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Shot extends JToggleButton implements ActionListener {// interactive
																	// shot
																	// selection/result
																	// display

	private static final ImageIcon images[] = { new ImageIcon(Shot.class.getResource("/res/null.gif")),
			new ImageIcon(Shot.class.getResource("/res/select.gif")),
			new ImageIcon(Shot.class.getResource("/res/hit.gif")),
			new ImageIcon(Shot.class.getResource("/res/miss.gif")), };
	private static ArrayList<Shot> pending = new ArrayList<Shot>(); // pending
																	// shot list

	private int state = 0; // 0-not selected 1-selected 2-hit 3-miss
	private int x, y;
	private boolean isPlayer;
	private Rectangle col; // Collision rectangle

	public Shot(int x, int y, boolean isPlayer) {
		this.x = x;
		this.y = y;
		this.isPlayer = isPlayer;
		this.col = new Rectangle(x, y, 1, 1);
		setIcon(images[state]);

		// transparent
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);

		setPreferredSize(new Dimension(16, 16));

		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {// handle click events
		if (isPlayer || state > 1 || Global.shotsLeft == 0 && state == 0 || Global.state != 1) { // on
			// player
			// board,
			// already
			// hit/miss,
			// no
			// shots
			// remaining
			// and
			// not
			// selected, Game state is not 1
			return; // ignore click
		}
		if (this.getModel().isSelected()) { // model button is selected
			state = 1;// update state
			pending.add(this); // add to pending shot list
		} else {
			state = 0;
			pending.remove(this);
		}
		setIcon(images[state]); // images are mapped to state
		Global.onShotChange(); // fire "event" handler
	}

	public void hit(boolean hit) {// set icon according to hitting the ship
		if (state > 1) { // already in a final state
			return;
		}
		if (hit) { // set state according to hit
			state = 2;
		} else {
			state = 3;
		}
		setIcon(images[state]);
		Global.onShotChange();
	}

	public Rectangle getCol() {// get collision rectangle
		return col;
	}

	public String toString() {// dump state
		return String.format("(%d,%d) is in state %d", x, y, state);
	}

	public void hide() { // hide image without changing state
		setIcon(images[0]);
	}

	public static ArrayList<Shot> getPending() { // get pending shots
		return pending;
	}

	public static void clear() {// clear pending shots
		pending.clear();
		Global.onShotChange();
	}

}
