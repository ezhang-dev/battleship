import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ShipPart extends JLabel {// Part of the ship, displayed on
										// shipBoard
	private static final ImageIcon images[] = { new ImageIcon(Shot.class.getResource("/res/null.gif")),
			new ImageIcon(Shot.class.getResource("/res/1.gif")), new ImageIcon(Shot.class.getResource("/res/2.gif")),
			new ImageIcon(Shot.class.getResource("/res/3.gif")), new ImageIcon(Shot.class.getResource("/res/4.gif")),
			new ImageIcon(Shot.class.getResource("/res/5.gif")), new ImageIcon(Shot.class.getResource("/res/6.gif")),
			new ImageIcon(Shot.class.getResource("/res/7.gif")), new ImageIcon(Shot.class.getResource("/res/8.gif")),
			new ImageIcon(Shot.class.getResource("/res/9.gif")), new ImageIcon(Shot.class.getResource("/res/10.gif")),
			new ImageIcon(Shot.class.getResource("/res/11.gif")), new ImageIcon(Shot.class.getResource("/res/12.gif")),
			new ImageIcon(Shot.class.getResource("/res/13.gif")), new ImageIcon(Shot.class.getResource("/res/14.gif")),
			new ImageIcon(Shot.class.getResource("/res/15.gif")),
			new ImageIcon(Shot.class.getResource("/res/16.gif")), };

	// GUI
	private Shot shot;// for revealing
	private int hitImage;
	private int regularImage;// may never be defined
	// Configuration
	private int x, y;
	private boolean hit = false;
	private Rectangle col;
	// DnD
	private Ship parent;
	private boolean sel = false;

	public ShipPart(Ship parent) {
		this.parent = parent;
		setOpaque(false);
		setPreferredSize(new Dimension(16, 16));
	}

	public String toString() {// represent ShipPart state with hit
		return String.format("(%d,%d) %b", x, y, hit);
	}

	public void location(int x, int y) {// set location of part
		this.x = x;
		this.y = y;
		col = new Rectangle(x, y, 1, 1);
	}


	public void set(int reg, int hit) {// set images with a visible image
		this.regularImage = reg;
		this.hitImage = hit;
	}

	public void set(int hit) {// set image only with hit
		this.hitImage = hit;
	}

	public void showImage(){//show image for this part of ship
		if(hit){//image is already showing
			return;
		}
		setIcon(images[this.regularImage]);// show image
	}

	public void dmove(int x, int y) {// indicate new coordinate for this part
		sel = true;// image selected, won't be placed into container in
					// ship.draw()
		parent.move(x - this.x, y - this.y);// figure out how much ship needs to
											// move globally
		sel = false;
	}

	public void rotate() { // right clicked at this point
		parent.rotate(x, y); // rotate ship around point
	}

	public boolean selected() {// is part selected
		return sel;
	}

	public void highlight(boolean hightlight) {// highlight part in red
		if (hightlight) {// switching depends on boolean
			setIcon(images[hitImage]);
		} else {
			setIcon(images[regularImage]);
		}
	}

	public boolean hit(Shot shot) { // check if this ship part was hit by shot
		if (this.col.contains(shot.getCol())) {
			this.shot = shot;// save shot for reveal
			this.hit = true;// save result for later, shot will provide image
			return true;
		}
		return false;
	}

	public void reveal() { // ship is fully hit, switch to hitImage
		setIcon(images[hitImage]);
		shot.hide(); // also hide shot so it's image won't block it
	}

	public boolean isHit() {// return if this part was hit
		return hit;
	}
}
