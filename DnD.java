import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DnD implements MouseListener, MouseMotionListener { //handle dragging and dropping shipParts

	private GridBoard board; // host of parts
	private ShipPart part = null; // part being dragged
	private JPanel org; // original panel

	private int xAdjustment;// offsets
	private int yAdjustment;

	private boolean enable = true; // is DnD enabled

	public DnD(GridBoard board) {
		this.board = board;
	}

	public void enable(boolean enable) { //enable/disable DnD
		this.enable = enable;
	}

	// onMouseDown
	public void mousePressed(MouseEvent e) {//when mouse button is pressed
		if (!enable) { // ignore everything if not enabled
			return;
		}
		if (part == null && SwingUtilities.isLeftMouseButton(e)) { // not
																	// dragging
																	// anything
																	// and is
																	// left
																	// button
			Component c = board.findComponentAt(e.getX(), e.getY()); // what got
																		// clicked

			if (!(c instanceof ShipPart)) // nothing to drag, ignore
				return;

			org = (JPanel) c.getParent(); // save panel for later

			Point parentLocation = c.getParent().getLocation(); // Relative
																// offsets
			xAdjustment = parentLocation.x - e.getX();
			yAdjustment = parentLocation.y - e.getY();

			part = (ShipPart) c; // save part
			part.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment); // move
																				// it

			Global.pane.add(part, JLayeredPane.DRAG_LAYER); // move part to top
															// of everything
		} else if (SwingUtilities.isRightMouseButton(e)) { // right click
			Component c = board.findComponentAt(e.getX(), e.getY());
			if (!(c instanceof ShipPart)) // check if ShipPart
				return;
			((ShipPart) c).rotate(); // rotate
			Global.onShipChange(); // fire event handler

		}
	}

	// onMouseMove
	public void mouseDragged(MouseEvent e) {//when mouse is moved
		if (part == null) // nothing dragged, nothing to do
			return;

		int x = e.getX() + xAdjustment; // make sure part stays in board
		int xMax = board.getWidth() - part.getWidth();
		x = Math.min(x, xMax);
		x = Math.max(x, 0);
		x = (x + 9) / 17 * 17; // snap to grid on 17x17 squares

		int y = e.getY() + yAdjustment;
		int yMax = board.getHeight() - part.getHeight();
		y = Math.min(y, yMax);
		y = Math.max(y, 0);
		y = (y + 8) / 17 * 17;

		Component c = board.findComponentAt(x, y); // what is it being dragged
													// over
		if (c instanceof JLabel) { // found shipPart
			c = c.getParent(); // get it's container
		}
		part.setLocation(x, y); // set part location
		if (c.getName() != null) { // in case got invalid container
			// 3.1f String Parsing
			int ax = Integer.parseInt(c.getName().split(" ")[0]);
			int ay = Integer.parseInt(c.getName().split(" ")[1]);
			part.dmove(ax, ay); // move ship
			Global.onShipChange();
		}

	}

	// onMouseUp
	public void mouseReleased(MouseEvent e) {//when lifted
		if (part == null || SwingUtilities.isRightMouseButton(e)) // nothing
																	// dragged
																	// or right
																	// click
			return;

		part.setVisible(false); // remove part from drag pane
		Global.pane.remove(part);
		part.setVisible(true);

		int xMax = board.getWidth() - part.getWidth(); // boundary checks
		int x = Math.min(part.getX(), xMax);
		x = Math.max(x, 0);

		int yMax = board.getHeight() - part.getHeight();
		int y = Math.min(part.getY(), yMax);
		y = Math.max(y, 0);

		Component c = board.findComponentAt(x, y); // find container
		if (c instanceof JLabel) { // got label, find container that contains it
			c = c.getParent();
		}

		JPanel parent;
		if (c.getName() == null) { // managed to get a invalid cotainer
			parent = org;
		} else {
			parent = (JPanel) c;
		}
		parent.add(part); // add part to container
		parent.validate();
		parent.repaint();
		if (c.getName() != null) { //just in case
			int ax = Integer.parseInt(c.getName().split(" ")[0]); //find location and move
			int ay = Integer.parseInt(c.getName().split(" ")[1]);
			((ShipPart) part).dmove(ax, ay);
		}
		part = null;
		Global.onShipChange();
	}

	//do nothing
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

}
