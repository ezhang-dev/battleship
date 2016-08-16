import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StatusBoard extends JPanel {
	private boolean[] status = new boolean[5];//ship status
	private Ship[] ships = new Ship[5];//ships
	private GridBoard board = new GridBoard(5, 5);//board

	public StatusBoard() {
		setBorder(BorderFactory.createTitledBorder("Ships Left"));
		setPreferredSize(new Dimension(95, 110));
		setOpaque(true);
		board.setLayout(new GridLayout(5, 5, 0, 0));//remove space between squares
		ships[0] = new Ship(2, 0, 0, true, board);//hard coded positions
		ships[1] = new Ship(3, 0, 1, true, board);
		ships[2] = new Ship(3, 0, 2, true, board);
		ships[3] = new Ship(4, 0, 3, true, board);
		ships[4] = new Ship(5, 0, 4, true, board);
		add(board);
	}

	public void update(int size) {//update hits based on sunk ship size
		size--;//2,3,3,4,5->1,2,2,3,4
		if (size < 3) {
			size--;//1,2,2,3,4->0,1,1,3,4
		}
		if (size == 1 && status[1] == true) {// if this is second 3 long (3-2=1) sunk
			size = 2; //set size to 2, first slot has already been filled
		}
		status[size] = true;
		for (int i = 0; i < 5; i++) {
			ships[i].highlight(status[i]);
		}
	}

}
