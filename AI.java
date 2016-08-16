import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class AI {//Game AI
	
	private static int[] size = { 5, 4, 3, 3, 2 }; // ship length
	private static Rectangle board = new Rectangle(0, 0, 10, 10); // board
																	// boundary

	private ArrayList<Rectangle> hits = new ArrayList<Rectangle>(); // hit shots
	private ArrayList<Rectangle> misses = new ArrayList<Rectangle>(); // miss
																		// shots
	private ArrayList<Shot> shots = new ArrayList<Shot>(); // current shots

	private int probabilities[][]; // probability matrix
	private int[][] sortList; // as a list [value,x,y]

	public AI() {
		updateProbabilities(); // default probability
	}

	public ArrayList<Shot> getShot() { //get AI shots
		int shotsLeft = Global.mode == 1 ? Global.computer.alive() : 1; // Salvo,
																		// shots
																		// left
																		// are
																		// ships
																		// left.
																		// Normal,
																		// one
																		// shot
		for (int x = 0; x < shotsLeft; x++) {
			shots.add(Global.human.getShots()[sortList[x][1] + 1][sortList[x][2] + 1]); // list
																						// is
																						// sorted
																						// so
																						// top
																						// shotsLeft
																						// are
																						// added
																						// to
																						// shots
		}
		return shots;
	}

	public void result(ArrayList<Boolean> res) { // process results of shots
		for (int i = 0; i < res.size(); i++) {
			Rectangle r = new Rectangle(shots.get(i).getCol()); // get copy of
																// collision
																// rectangle
			r.setLocation((int) r.getX() - 1, (int) r.getY() - 1); // shift
																	// -1,-1
			if (res.get(i)) { // add it to correct list
				hits.add(r);
			} else {
				misses.add(r);
			}
		}
		shots.clear(); // clear queue
		updateProbabilities(); // Calculate probabilities
	}

	public int[][] getShips() { // randomly place ships
		Random rand = new Random();
		int[][] ships = new int[5][4]; // array of ship placement
										// [size,x,y,rotation]
		ArrayList<Rectangle> placedShips = new ArrayList<Rectangle>(); // placed
																		// ships
		for (int i = 0; i < 5; i++) { // one of every ship
			int rotation = rand.nextInt(2); // rotation 0-horizontal 1-vertical
			int x = 0, y = 0;
			boolean valid = false;
			while (!valid) { // keep trying until valid placement
				x = rand.nextInt(10 - (rotation == 0 ? size[i] : 0)); // random
																		// x,y
																		// values,
																		// limited
																		// by
																		// ship
																		// length
				y = rand.nextInt(10 - (rotation == 1 ? size[i] : 0));
				valid = canFit(size[i], x, y, rotation, placedShips);
			}
			ships[i][0] = size[i]; // record placement
			ships[i][1] = x + 1;
			ships[i][2] = y + 1;
			ships[i][3] = rotation;
			placedShips.add(genRect(size[i], x, y, rotation)); // add ship to
																// placedShips
		}
		return ships;
	}

	private void updateProbabilities() { //calculate probabilities
		probabilities = new int[10][10]; // reset array
		for (int i = 0; i < 5; i++) {
			for (int x = 0; x < 10; x++) {
				for (int y = 0; y < 10; y++) {
					for (int r = 0; r < 2; r++) {
						if (canFit(size[i], x, y, r, misses)) { // if you can
																// fit the ship
							increaseProbability(size[i], x, y, r); // increase
																	// probabilities
						}
					}

				}
			}
		}
		hitProbability(); // handle hits
		createList(); // create and sort list
	}

	private void createList() { //convert matrix to list
		sortList = new int[100][3]; // reset list
		int i = 0;
		for (int x = 0; x < 10; x++) { // assign every probability an element on
										// the list
			for (int y = 0; y < 10; y++) {
				sortList[i][0] = probabilities[x][y];
				sortList[i][1] = x;
				sortList[i][2] = y;
				i++;
			}
		}
		Arrays.sort(sortList, new Comparator<int[]>() { // custom array sorting
														// based on first column
														// (probability)
			@Override
			public int compare(final int[] a, final int[] b) {
				return b[0] - a[0];
			}
		});

	}

	private boolean canFit(int size, int x, int y, int rotation, ArrayList<Rectangle> col) { // check
																								// if
																								// a
																								// ship
																								// can
																								// be
																								// placed
																								// without
																								// running
																								// into
																								// anything
																								// in
																								// col
		Rectangle s = genRect(size, x, y, rotation); // create ship
		for (int i = 0; i < col.size(); i++) { // go through everything in col
			if (s.intersects(col.get(i))) {
				return false;
			}
		}
		if (!board.contains(s)) { // check if it is not contained in board
			return false;
		}
		return true;
	}

	private Rectangle genRect(int size, int x, int y, int rotation) { // create
																		// a
																		// rectangle
																		// representing
																		// the
																		// ship
		if (rotation == 0) {
			return new Rectangle(x, y, size, 1);
		} else {
			return new Rectangle(x, y, 1, size);
		}
	}

	private void increaseProbability(int size, int x, int y, int rotation) { // go
																				// through
																				// every
																				// square
																				// and
																				// increase
																				// the
																				// probability
		for (int i = 0; i < size; i++) {
			probabilities[x][y]++;
			if (rotation == 0) {
				x++;
			} else {
				y++;
			}
		}
	}

	private void hitProbability() { // deal with hit square probability
		for (int i = 0; i < hits.size(); i++) {
			int sx = (int) hits.get(i).getX();
			int sy = (int) hits.get(i).getY();
			probabilities[sx][sy] = 0; // don't hit the square again
			if (sy + 1 < 10) { // there's more ship on one of the edges if it is
								// not the edge of the board, increase
								// probability
				probabilities[sx][sy + 1] *= 4;
			}
			if (sy - 1 > -1) {
				probabilities[sx][sy - 1] *= 4;
			}
			if (sx + 1 < 10) {
				probabilities[sx + 1][sy] *= 4;
			}
			if (sx - 1 > -1) {
				probabilities[sx - 1][sy] *= 4;
			}

		}
	}

}
