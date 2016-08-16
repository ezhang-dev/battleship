import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel implements ActionListener { // create game
																// layout and
																// manage logic

	private AI ai = new AI();

	public Game() {
		// 11 wide
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// computer board
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 11;
		c.gridheight = 11;
		add(new Board(false), c);

		// status board
		c.gridx = 0;
		c.gridy = 11;
		c.gridheight = 5;
		c.gridwidth = 5;
		Global.statusboard = new StatusBoard();
		add(Global.statusboard, c);

		// button
		c.gridx = 5;
		c.gridy = 11;
		c.gridheight = 5;
		c.gridwidth = 6;
		Global.button = new JButton("Deploy");
		Global.button.addActionListener(this);
		Global.button.setPreferredSize(new Dimension(95, 110));
		add(Global.button, c);

		// player board
		c.gridx = 0;
		c.gridy = 17;
		c.gridheight = 11;
		c.gridwidth = 11;
		add(new Board(true), c);

		// Random ships
		Global.human.placeShips(ai.getShips());
		Global.computer.placeShips(ai.getShips());

		// Status bar
		Global.statusbar.setPreferredSize(new Dimension(20, 30));
		Global.statusbar.setText("<html><p>Move ships by dragging. Right-click to rotate.</p></html>");
		
		//Global.computer.showShip();
	}

	@Override
	public void actionPerformed(ActionEvent e) {// button event handler

		switch (Global.state) {
		case 0: // deploy ships
			Global.dnd.enable(false);
			// transform into Shoot state
			Global.button.setText("Shoot");
			Global.button.setEnabled(false);
			Global.human.addShotBoard();
			Global.computer.addShotBoard();
			Global.state = 1;
			Global.statusbar.setText("<html><p>Select and deselect shots by clicking on the grid.</p></html>");
			break;
		case 1: // taking turns taking shots
			Global.button.setEnabled(false);
			// Player shot
			Global.computer.shoot(Shot.getPending());
			Shot.clear();
			if (Global.computer.alive() == 0) { // check for win
				if (JOptionPane.showConfirmDialog(null, "Congratulations, you won. would you like to play again?",
						"You Won", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					Global.reset();
				} else {
					System.exit(0);
				}

				break;
			}
			// computer shot
			ai.result(Global.human.shoot(ai.getShot()));
			if (Global.human.alive() == 0) {
				Global.computer.showShip();// Show computer ships
				if (JOptionPane.showConfirmDialog(null, "Sorry, you lost. would you like to play again?",
						"You Lost", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					Global.reset();
				} else {
					System.exit(0);
				}
				break;
			}
			break;
		}
	}

}
