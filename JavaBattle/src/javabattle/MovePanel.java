package javabattle;

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
		Move testMove = new Move(0, "Test Move", 10, 50, 90, 5, MoveType.PHYSICAL_MELEE.getName(), "Using the test move", new String[]{"Missed"}, "A cool description");
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

		numTextField.setText("1");
		add(numTextField, "spany 2, gapright 5");

		nameLabel.setText(testMove.name);
		nameLabel.setFont(new Font("Consolas", Font.PLAIN, 16));
		nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		add(nameLabel, "growx, wrap");

		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
			powerLabel.setText(testMove.getPowerRangeString());
			powerLabel.setFont(new Font("Consolas", Font.PLAIN, 11));
			powerLabel.setHorizontalAlignment(SwingConstants.LEFT);
			infoPanel.add(powerLabel);
			infoPanel.add(Box.createGlue());

			accuracyLabel.setText(testMove.accuracy + "%");
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
			spLabel.setText(String.valueOf(testMove.SPcost));
			spLabel.setFont(new Font("Consolas", Font.PLAIN, 11));
			spLabel.setHorizontalAlignment(SwingConstants.LEFT);
			infoPanel.add(spLabel);
			infoPanel.add(Box.createGlue());

			// TODO: Add stat/cond setting when that's added
			statLabel.setText("N/A");
			statLabel.setFont(new Font("Consolas", Font.PLAIN, 11));
			statLabel.setHorizontalAlignment(SwingConstants.LEFT);
			infoPanel.add(statLabel);
			infoPanel.add(Box.createGlue());

			conditionLabel.setText("N/A");
			conditionLabel.setFont(new Font("Consolas", Font.PLAIN, 11));
			conditionLabel.setHorizontalAlignment(SwingConstants.LEFT);
			infoPanel.add(conditionLabel);

		add(infoPanel, "growx, pushx");
	}
}
