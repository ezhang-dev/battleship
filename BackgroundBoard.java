import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class BackgroundBoard extends GridBoard { //create background
	private static final ImageIcon blank = new ImageIcon(BackgroundBoard.class.getResource("/res/blank.gif"));

	public BackgroundBoard() {
		for (int x = 0; x < 11; x++) { //over every cell on a 11x11 grid
			for (int y = 0; y < 11; y++) {
				JLabel square = new JLabel(); //create a label
				square.setPreferredSize(new Dimension(16,16)); //16x16
				square.setHorizontalAlignment(SwingConstants.CENTER); //centered
				square.setVerticalAlignment(SwingConstants.CENTER);
				if (x == 0 && y == 0) { //top left corner blank
				} else if (x == 0) {
					square.setText(String.format("%d", y)); //top row numbers
				} else if (y == 0) {
					square.setText(String.format("%c", 'A' + (x - 1))); //left side letters offset from A using char manipulation
				} else {
					square.setIcon(blank); //(1,1)-(10,10) display blank image
				}
				set(x,y,square); //add to grid
			}
		}

	}
}
