package javabattle;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

/* TODO:
Get moveChoicesPanel sized right
Add menu
Tie to model and get a battle working
*/

/**
 *
 * @author Zac Hayes
 */
public class BattleWindow extends JFrame
{
	// Component declaration
	JPanel gamePanel = new JPanel(); // TODO Change to GraphicsPanel after window works and GraphicsPanel class is created
	JPanel moveChoicesPanel = new JPanel();
	MovePanel[] movePanel = new MovePanel[6];

	JMenuBar menuBar = new JMenuBar();
	JMenu theMenu = new JMenu("Menu");
	JMenuItem newGameMenuItem = new JMenuItem("New Game");
	JMenuItem musicMenuItem = new JMenuItem("Change Music");
	JMenuItem helpMenuItem = new JMenuItem("Help");
	JMenuItem exitMenuItem = new JMenuItem("Exit");
	// End of component declaration

	public static void main(String[] args)
	{
		BattleWindow testFrame = new BattleWindow();
		testFrame.setVisible(true);
	}

	public BattleWindow()
	{
		//<editor-fold defaultstate="collapsed" desc="add menu">
		setJMenuBar(menuBar);
		menuBar.add(theMenu);
		theMenu.add(newGameMenuItem); newGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		theMenu.addSeparator();
		theMenu.add(musicMenuItem); musicMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
		theMenu.add(helpMenuItem); helpMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		theMenu.addSeparator();
		theMenu.add(exitMenuItem);

		newGameMenuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
			}
		});

		musicMenuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
			}
		});

		helpMenuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
			}
		});

		exitMenuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
			}
		});
//</editor-fold>

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new MigLayout("alignx center, insets 0 0 10 0, flowy",
												 "[align center]",
												 "[|fill, grow]"));

		gamePanel.setPreferredSize(new Dimension(490, 295));
		gamePanel.setMinimumSize(new Dimension(490, 295));
		gamePanel.setBackground(Color.BLACK);
		getContentPane().add(gamePanel, "growx 100, pushx 100");

		moveChoicesPanel.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(Color.GRAY, 1, true),
			BorderFactory.createLineBorder(Color.DARK_GRAY, 3)));
		moveChoicesPanel.setLayout(new MigLayout("flowy, alignx center, gap 5, insets 5, fill"));
		getContentPane().add(moveChoicesPanel, "align center, gap 80 80 0 0, growx, pushx");

		for (MovePanel panel : movePanel)
		{
			panel = new MovePanel();
			panel.setPreferredSize(new Dimension(320, 35));
//			panel.setBorder(BorderFactory.createLineBorder(Color.yellow));
			moveChoicesPanel.add(panel, "growx, pushx");
		}

		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int) (0.5 * (screenSize.width - getWidth())), (int) (0.5 * (screenSize.height - getHeight())), getWidth(), getHeight());
		setMinimumSize(getSize());
	}
}
