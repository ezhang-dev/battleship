@SuppressWarnings("serial")
public class ShotBoard extends GridBoard {//Shot layer of board
	private Shot[][] shots = new Shot[11][11];

	public ShotBoard(boolean isPlayer) {//go through the 11x11 board
		for (int x = 0; x < 11; x++) {
			for (int y = 0; y < 11; y++) {
				if (x == 0 || y == 0) { //skip row and column 0
					continue;
				}
				shots[x][y] = new Shot(x, y, isPlayer); //create shot
				set(x, y, shots[x][y]);
			}
		}
	}
	
	public Shot[][] getShots(){//return shots for manual placement by computer
		return shots;
	}
}
