import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GridBoard extends JPanel { // JPanel with a grid
	private JPanel[][] boxes;
	private int x, y;
	private JPanel overflow = new JPanel(); // overflowed part placement

	public GridBoard() {
		this(11, 11); // default 11x11
	}

	public GridBoard(int x, int y) {
		boxes = new JPanel[x][y];
		this.x = x;
		this.y = y;
		setOpaque(false);
		setLayout(new GridLayout(x, y, 1, 1));
		setBounds(0, 0, (16 + 1) * x - 1, (16 + 1) * y - 1);
		for (int ay = 0; ay < y; ay++) {// create a x by y grid of JPanels in a
										// GridLayout
			for (int ax = 0; ax < x; ax++) {
				boxes[ax][ay] = new JPanel();
				boxes[ax][ay].setPreferredSize(new Dimension(16, 16));
				boxes[ax][ay].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
				boxes[ax][ay].setName("" + ax + " " + ay); // name is coordinate
				boxes[ax][ay].setOpaque(false);

				add(boxes[ax][ay]);// placement is left to right, down to up
			}
		}
	}

	public void set(int x, int y, JComponent compoment) { // place a component
															// in a location
		if (x < 0 || x > this.x - 1 || y < 0 || y > this.y - 1) { // out of
																	// bounds
																	// locations
																	// go to
																	// overflow
			overflow.add(compoment);
			return;
		}
		boxes[x][y].add(compoment);
		boxes[x][y].revalidate();
		boxes[x][y].repaint();
	}

	public void refresh() { // refresh all squares
		for (int x = 0; x < this.x; x++) {
			for (int y = 0; y < this.y; y++) {
				boxes[x][y].revalidate();
				boxes[x][y].repaint();
			}
		}
	}
}
