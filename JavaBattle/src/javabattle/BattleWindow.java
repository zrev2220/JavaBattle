package javabattle;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
import net.miginfocom.swing.MigLayout;

/* TODO:
Tie to model and get a battle working
*/

public class BattleWindow extends JFrame
{
	// Component declaration
	GamePanel gamePanel = new GamePanel();
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
		gamePanel.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				gamePanelKeyPressed(e);
			}
		});

		moveChoicesPanel.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(Color.GRAY, 1, true),
			BorderFactory.createLineBorder(Color.DARK_GRAY, 3)));
		moveChoicesPanel.setLayout(new MigLayout("flowy, alignx center, gap 5, insets 5, fill"));
		getContentPane().add(moveChoicesPanel, "align center, gap 80 80 0 0, growx, pushx");

		for (MovePanel panel : movePanel)
		{
			panel = new MovePanel();
			panel.setPreferredSize(new Dimension(320, 35));
			moveChoicesPanel.add(panel, "growx, pushx");
		}

		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int) (0.5 * (screenSize.width - getWidth())), (int) (0.5 * (screenSize.height - getHeight())), getWidth(), getHeight());
		setMinimumSize(getSize());
		gamePanel.requestFocus();
	}

	private void gamePanelKeyPressed(KeyEvent e)
	{
		System.out.println("Key pressed: " + e.getKeyChar()); // TODO: Remove this PoC line when actually getting move choices
		gamePanel.requestFocus();
	}
}

class GamePanel extends JPanel implements Runnable
{
	final Font outputFont = new Font("Courier New", Font.PLAIN, 14);
	final Font winFont = new Font("Impact", Font.PLAIN, 24);
	private boolean isRunning;
	private final int framerate = 17;

	public GamePanel()
	{
		super();
		this.start();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		this.setBackground(Color.BLACK);
		final double W = this.getWidth();
		final double H = this.getHeight();
		Rectangle2D.Double backgroundRect = new Rectangle2D.Double(5, 75, W - 10, H - 150);
		double outW = 0.62 * W;
		RoundRectangle2D.Double outputBorder = new RoundRectangle2D.Double(0.5 * (W - outW), 2, outW, backgroundRect.getY() - 4, 5, 2); // TODO generalize
		g2d.setPaint(new Color(181, 230, 29));
		g2d.fill(backgroundRect);
		g2d.setPaint(Color.WHITE);
		g2d.setStroke(new BasicStroke(2));
		g2d.draw(outputBorder);

		g2d.dispose();
	}

	public void start()
	{
		isRunning = true;
		new Thread(this).start();
	}

	@Override
	@SuppressWarnings("SleepWhileInLoop")
	public void run()
	{
		while (isRunning)
		{
			repaint();
			try {
				Thread.sleep(framerate);
			} catch (InterruptedException e)
			{
				JOptionPane.showConfirmDialog(null, "The graphics engine was interrupted unexpectedly.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
