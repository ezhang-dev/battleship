import java.awt.Rectangle;
import java.util.Arrays;

public class Ship {// Represents a ship

	// image parts: horizontal 1-5, vertical 6-10, dead=11-13,14-16

	private static int invalid = 0; // invalid ships
	// properties
	private int x, y;
	private int rotation = 0;
	private int size;
	private int life;
	private Rectangle col;
	private boolean isPlayer;
	private boolean triggered = false;
	// ship is invalid
	private boolean isValid = true;
	// gui drawing
	private GridBoard board;
	private ShipPart[] parts;

	public Ship(int size, int x, int y, boolean isPlayer, GridBoard board) {
		this.size = size;
		this.x = x;
		this.y = y;
		this.isPlayer = isPlayer;
		this.board = board;
		this.life = size;
		parts = new ShipPart[size];
		for (int i = 0; i < size; i++) {
			parts[i] = new ShipPart(this); // create new ShipParts with
											// reference to parent ship object
		}

		update();
	}

	public boolean shot(Shot shot) {// checks and records hit
		for (int i = 0; i < size; i++) {
			if (parts[i].hit(shot)) {
				life--;
				return true;
			}
		}
		return false;
	}

	public void updateHit() {// detects and updates if ship sunk
		if (life == 0) {
			for (int j = 0; j < size; j++) {
				parts[j].reveal();
			}
			if (!triggered && !isPlayer) { // if enemy ship sunk and not
											// triggered
				Global.statusboard.update(size); // update status board
				triggered = true; // set triggered
			}
		}
	}

	public String toString() {// dump state
		return String.format("Ship (%d,%d) rotataion %d parts:%s", x, y, rotation, Arrays.toString(parts));

	}

	public Rectangle col() { // get collision
		return col;
	}

	public void rotate(int x, int y) {// rotate ship around point(x,y)

		rotation = (rotation + 1) % 2;
		setLocation(x + (this.y - y), y + (this.x - x));
		update();
	}

	public void rotate() { // rotate around ship head
		rotate(x, y);
	}

	public boolean alive() {// is ship still alive
		return life != 0;
	}

	public void setLocation(int x, int y) { // set ship location
		this.x = x;
		this.y = y;
		update();
	}

	public void move(int dx, int dy) { // move ship
		this.x = this.x + dx;
		this.y = this.y + dy;
		update();

	}

	public void valid(boolean valid) {// indicate if ship location is valid
		if (!isValid && valid) {// update numval based on invalid ships
			invalid--;
		} else if (isValid && !valid) {
			invalid++;
		}
		isValid = valid;
		highlight(!valid);

	}

	public void highlight(boolean highlight) {// highlight ship in red
		for (int i = 0; i < size; i++) {
			parts[i].highlight(highlight);
		}
	}

	public void show() { //show ship
		for (int i = 0; i < size; i++) {
			parts[i].showImage();
		}
	}

	public static boolean allValid() { // check if all ships placements are
										// valid
		return invalid == 0;
	}

	private void update() {// update various parts of ship
		createImages();
		createCol();
		createLocation();
		draw();
	}

	private void createCol() {// update collision rectangle
		if (rotation == 0) {
			col = new Rectangle(x, y, size, 1);
		} else {
			col = new Rectangle(x, y, 1, size);
		}
	}

	private void createImages() {// update images in shipParts
		for (int i = 0; i < size; i++) {
			int reg, hit;
			if (i == 0) {// head
				reg = 1;
				hit = 11;
			} else if (i == size - 1) {// tail
				reg = 5;
				hit = 13;
			} else {// other
				reg = i + 1;
				hit = 12;
			}

			if (rotation == 1) {// rotation
				reg = reg + 5;
				hit = hit + 3;
			}
			parts[i].set(reg, hit);
			if (isPlayer) {// should show ship

				parts[i].showImage();
			}
		}
	}

	private void createLocation() {// update location in shipParts
		int x = this.x;
		int y = this.y;
		for (int i = 0; i < size; i++) {
			parts[i].location(x, y);
			if (rotation == 0) {
				x++;
			} else {
				y++;
			}
		}

	}

	private void draw() {// draw ship on board
		int x = this.x;
		int y = this.y;
		for (int i = 0; i < size; i++) {
			if (!parts[i].selected()) { // if parts wasn't selected by DnD
				board.set(x, y, parts[i]);// update part in new container
			}
			if (rotation == 0) {
				x++;
			} else {
				y++;
			}
		}
		board.refresh();

	}

	public static void reset() {// reset static vars
		invalid = 0;
	}

}
