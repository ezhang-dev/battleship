import java.awt.Rectangle;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ShipBoard extends GridBoard {// Ship layer of board

	private boolean isPlayer;
	private Rectangle col = new Rectangle(1, 1, 10, 10);
	private Ship[] ships = new Ship[5];

	public ShipBoard(boolean isPlayer) {
		this.isPlayer = isPlayer;
		if (isPlayer) {// if player, create DnD and add Listeners
			Global.dnd = new DnD(this);
			addMouseListener(Global.dnd);
			addMouseMotionListener(Global.dnd);
		}

	}

	public ArrayList<Boolean> shoot(ArrayList<Shot> shots) {// Takes list of
															// shots and return
															// results
		ArrayList<Boolean> results = new ArrayList<Boolean>();
		for (int i = 0; i < shots.size(); i++) {
			boolean hit = false;
			for (int a = 0; a < (ships.length); a++) {
				if (ships[a].shot(shots.get(i))) {// A shot can only hit one
													// ship
					hit = true;
					break;
				}
			}
			shots.get(i).hit(hit);// notify shot to update image
			results.add(hit);// record results
		}
		for (int x = 0; x < ships.length; x++) {// update all ships to reveal
												// newly dead ones
			ships[x].updateHit();
		}
		return results;
	}

	public void valid() {// check if ship placement is valid on board
		Boolean[] valid = { true, true, true, true, true };
		for (int i = 0; i < 5; i++) { // use handshake strategy to
										// effectively check if
										// collisions between ships
			for (int j = i + 1; j < 5; j++) {
				boolean c = !ships[i].col().intersects(ships[j].col());// check
																		// if
																		// not
																		// intersecting
				valid[i] &= c; // for ship to be valid, it must never intersect
								// due to ANDing with false
				valid[j] &= c;
			}
			valid[i] &= col.contains(ships[i].col());// also check to see if
														// contained in board
														// (not on labels)
		}
		for (int x = 0; x < ships.length; x++) {
			ships[x].valid(valid[x]);

		}

	}

	public int alive() {// count amount of alive ships
		int alive = 0;
		for (int i = 0; i < ships.length; i++) {
			alive += ships[i].alive() ? 1 : 0; // increment by 1 if alive else 0
		}
		return alive;
	}
	
	public void showShip() {// show hidden ships
		for (int i = 0; i < ships.length; i++) {
			ships[i].show();
		}
	}

	public void placeShips(int[][] ships) {// place ships according to
											// configuration
		for (int i = 0; i < ships.length; i++) {
			int[] s = ships[i];
			this.ships[i] = new Ship(s[0], s[1], s[2], isPlayer, this);
			if (s[3] == 1) {
				this.ships[i].rotate();
			}
		}
	}

}
