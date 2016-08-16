import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar implements ActionListener {
	private JMenu menu = new JMenu("Game");
	private ButtonGroup game = new ButtonGroup();
	private JMenuItem[] items = { new JMenuItem("New Game"), new JRadioButtonMenuItem("Salvo Battleship"),
			new JRadioButtonMenuItem("Normal Battleship"), new JMenuItem("Exit") };

	public MenuBar() {
		menu.add(items[0]);
		items[0].setActionCommand("NG");
		items[0].addActionListener(this);

		menu.addSeparator();

		menu.add(items[1]);
		items[1].setActionCommand("SB");
		items[1].addActionListener(this);
		items[1].setSelected(true);
		game.add(items[1]);

		menu.add(items[2]);
		items[2].setActionCommand("NB");
		items[2].addActionListener(this);
		game.add(items[2]);

		menu.addSeparator();

		menu.add(items[3]);
		items[3].setActionCommand("EX");
		items[3].addActionListener(this);
		add(menu);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("NG")) {
			if (JOptionPane.showConfirmDialog(null, "Do you want to start a new game?", "New Game",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				Global.reset();
			}
		} else if (action.equals("SB")) {
			if (Global.mode == 0 && JOptionPane.showConfirmDialog(null,
					"Do you want to switch rules to Salvo Battleship? All progress will be lost.", "Switch rules",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				Global.reset();
				Global.mode = 1;
			} else {
				items[2-Global.mode].setSelected(true);
			}
		} else if (action.equals("NB")) {
			if (Global.mode == 1 && JOptionPane.showConfirmDialog(null,
					"Do you want to switch rules to Normal Battleship? All progress will be lost.", "Switch rules",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				Global.reset();
				Global.mode = 0;
			} else {
				items[2-Global.mode].setSelected(true);
			}
		} else if (action.equals("EX")) {
			if (JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Exit",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}

	}

}
