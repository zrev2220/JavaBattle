package javabattle;
// TODO: Add icons to info labels

import java.awt.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.event.*;
import net.miginfocom.swing.MigLayout;

/**
 * @author Zac Hayes
 */
public class MovePanel extends JPanel
{
	// Component declaration
	JTextField numTextField = new JTextField();
	JLabel nameLabel = new JLabel();
	JPanel infoPanel = new JPanel();
		JLabel powerLabel = new JLabel(),
		accuracyLabel = new JLabel(),
		spLabel = new JLabel(),
		statLabel = new JLabel(),
		conditionLabel = new JLabel();
	// End of component declaration

	private String key;
	private Move panelMove;

	public static void main(String[] args)
	{
		JFrame testFrame = new JFrame();
		testFrame.setVisible(true);
		testFrame.getContentPane().setLayout(new MigLayout("insets 10, fill"));
		MovePanel testPanel = new MovePanel();
		testPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
		testFrame.getContentPane().add(testPanel, "grow");
		testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testFrame.pack();
	}

	public MovePanel()
	{
		panelMove = new Move(0, "N/A", 0, 0, 0, 0, MoveType.PHYSICAL_MELEE.getName(), "", new String[]{""}, "");
		key = "";
		setLayout(new MigLayout("aligny center, gap 0"));

		numTextField.setEditable(false);
		numTextField.setOpaque(false);
		numTextField.setFont(new Font("Consolas", Font.BOLD, 16));
		numTextField.setHorizontalAlignment(SwingConstants.CENTER);
		// listen for changes in the text, resize accordingly
		numTextField.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void changedUpdate(DocumentEvent e) { resize(); }
			@Override
			public void removeUpdate(DocumentEvent e) { resize(); }
			@Override
			public void insertUpdate(DocumentEvent e) { resize(); }

			public void resize()
			{
				numTextField.setPreferredSize(null);
				numTextField.setMinimumSize(numTextField.getPreferredSize());
				numTextField.setPreferredSize(new Dimension((int) (numTextField.getPreferredSize().getWidth()) + 5, (int) numTextField.getPreferredSize().getHeight()));
			}
		});

		add(numTextField, "spany 2, gapright 5");

		nameLabel.setFont(new Font("Consolas", Font.PLAIN, 16));
		nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		add(nameLabel, "growx, wrap");

		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
			powerLabel.setFont(new Font("Consolas", Font.PLAIN, 11));
			powerLabel.setHorizontalAlignment(SwingConstants.LEFT);
			infoPanel.add(powerLabel);
			infoPanel.add(Box.createGlue());

			accuracyLabel.setFont(new Font("Consolas", Font.PLAIN, 11));
			accuracyLabel.setHorizontalAlignment(SwingConstants.LEFT);
			infoPanel.add(accuracyLabel);
			infoPanel.add(Box.createGlue());

			spLabel.addPropertyChangeListener("text", new PropertyChangeListener()
			{
				@Override
				public void propertyChange(PropertyChangeEvent e)
				{
					spLabel.setForeground((Integer.valueOf(spLabel.getText()) > 0) ? Color.BLUE : Color.GRAY);
				}
			});
			spLabel.setFont(new Font("Consolas", Font.PLAIN, 11));
			spLabel.setHorizontalAlignment(SwingConstants.LEFT);
			infoPanel.add(spLabel);
			infoPanel.add(Box.createGlue());

			// TODO: Add stat/cond setting when that's added
			statLabel.setFont(new Font("Consolas", Font.PLAIN, 11));
			statLabel.setHorizontalAlignment(SwingConstants.LEFT);
			infoPanel.add(statLabel);
			infoPanel.add(Box.createGlue());

			conditionLabel.setFont(new Font("Consolas", Font.PLAIN, 11));
			conditionLabel.setHorizontalAlignment(SwingConstants.LEFT);
			infoPanel.add(conditionLabel);

		add(infoPanel, "growx, pushx");

		refresh();
	}

	private void refresh()
	{
		numTextField.setText(key);
		nameLabel.setText(panelMove.name);
		powerLabel.setText(panelMove.getPowerRangeString());
		accuracyLabel.setText(panelMove.accuracy + "%");
		spLabel.setText(String.valueOf(panelMove.SPcost));
		statLabel.setText("N/A");
		conditionLabel.setText("N/A");
	}

	@Override
	public void setEnabled(boolean flag)
	{
		if (flag)
		{
			numTextField.setForeground(Color.BLACK);
			numTextField.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
			nameLabel.setForeground(Color.BLACK);
			powerLabel.setForeground(Color.BLACK);
			accuracyLabel.setForeground(Color.BLACK);
			int sp = Integer.valueOf(spLabel.getText().substring(spLabel.getText().indexOf(":") + 1));
			spLabel.setForeground((sp != 0) ? Color.BLUE : (Color.BLACK));
			statLabel.setForeground(Color.BLACK);
			conditionLabel.setForeground(Color.BLACK);
		}
		else
		{
			numTextField.setForeground(Color.GRAY);
			numTextField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			nameLabel.setForeground(Color.GRAY);
			powerLabel.setForeground(Color.GRAY);
			accuracyLabel.setForeground(Color.GRAY);
			spLabel.setForeground(Color.GRAY);
			statLabel.setForeground(Color.GRAY);
			conditionLabel.setForeground(Color.GRAY);
		}
	}

	public String getKey() { return key; }
	public void setKey(String key)
	{
		this.key = key;
		refresh();
	}

	public Move getPanelMove() { return panelMove; }
	public void setPanelMove(Move panelMove)
	{
		this.panelMove = panelMove;
		refresh();
	}
}
