package javabattle;

import java.awt.*;
import java.awt.image.BufferedImage;
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
	private String description;

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
		testFrame.setSize(new Dimension(300, 150));
		testFrame.setMaximumSize(new Dimension(300, 150));
		testPanel.setDescription("Cuts down on damage taken");
//		testPanel.setDescription("");
		testPanel.setEnabled(false);
	}

	public MovePanel()
	{
		panelMove = new Move(0, "N/A", 0, 0, 0, 0, MoveType.PHYSICAL_MELEE.getName(), "", new String[]{""}, "");
		key = "0";
		description = "";
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
		setEnabled(true);
	}

	private void refresh()
	{
		numTextField.setText(key);
		nameLabel.setText(panelMove.name);
		powerLabel.setText((description.equals("")) ? panelMove.getPowerRangeString() : description);
		accuracyLabel.setText(panelMove.accuracy + "%");
		spLabel.setText(String.valueOf(panelMove.SPcost));
		int sp = Integer.valueOf(spLabel.getText().substring(spLabel.getText().indexOf(":") + 1));
		spLabel.setForeground((sp != 0) ? Color.BLUE : (Color.BLACK));
		spLabel.setIcon(new ImageIcon(colorImage(new ImageIcon("resources/effects/SP_icon.png").getImage(), (sp != 0) ? Color.BLUE : (Color.BLACK))));
		statLabel.setText("N/A");
		conditionLabel.setText("N/A");
	}

	@Override
	public void setEnabled(boolean flag)
	{
		Color iconColor = flag ? Color.BLACK : Color.GRAY;
		// sp - GRAY if disabled, BLACK if sp = 0, BLUE if sp > 0
		Color spColor = flag ? ((Integer.valueOf(spLabel.getText().substring(spLabel.getText().indexOf(":") + 1)) != 0) ? Color.BLUE : (Color.BLACK)) : Color.GRAY;
		if (flag)
		{
			numTextField.setForeground(Color.BLACK);
			numTextField.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
			nameLabel.setForeground(Color.BLACK);
			powerLabel.setForeground(Color.BLACK);
			accuracyLabel.setForeground(Color.BLACK);
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
		if (description.equals(""))
			powerLabel.setIcon(new ImageIcon(colorImage(new ImageIcon("resources/effects/Offense_icon.png").getImage(), iconColor)));
		accuracyLabel.setIcon(new ImageIcon(colorImage(new ImageIcon("resources/effects/Accuracy_icon.png").getImage(), iconColor)));
		spLabel.setIcon(new ImageIcon(colorImage(new ImageIcon("resources/effects/SP_icon.png").getImage(), spColor)));
		// TODO: Add stat, cond icon setting when they're added
//		Image a = new ImageIcon("resources/effects/Offense_icon.png").getImage();
//		Image b = new ImageIcon("resources/effects/SP_icon.png").getImage();
//		statLabel.setIcon(new ImageIcon(combineImage(a, b, 2)));
//		conditionLabel.setIcon(new ImageIcon(colorImage(new ImageIcon("resources/effects/Offense_icon.png").getImage(), iconColor)));
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

	public String getDescription() { return description; }
	public void setDescription(String desc)
	{
		this.description = desc;
		accuracyLabel.setVisible(desc.equals(""));
		spLabel.setVisible(desc.equals(""));
		statLabel.setVisible(desc.equals(""));
		conditionLabel.setVisible(desc.equals(""));
		if (desc.equals(""))
			powerLabel.setIcon(new ImageIcon(colorImage(new ImageIcon("resources/effects/Offense_icon.png").getImage(), powerLabel.getForeground())));
		else
			powerLabel.setIcon(null);
		refresh();
	}

	private BufferedImage colorImage(Image src, float r, float g, float b)
	{
		/* Original code copied from stackoverflow.com
		 * Answer posted by Hardcoded Cat
		 * http://stackoverflow.com/a/29388834/6548555
		 */
		// Convert the image to BufferedImage
		BufferedImage bimage = new BufferedImage(src.getWidth(null), src.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(src, 0, 0, null);
		bGr.dispose();

		// Copy image
		BufferedImage newImage = new BufferedImage(bimage.getWidth(), bimage.getHeight(), BufferedImage.TRANSLUCENT);
		Graphics2D graphics = newImage.createGraphics();
		graphics.drawImage(bimage, 0, 0, null);
		graphics.dispose();

		// Color image
		for (int i = 0; i < newImage.getWidth(); i++) {
			for (int j = 0; j < newImage.getHeight(); j++) {
				int ax = newImage.getColorModel().getAlpha(newImage.getRaster().getDataElements(i, j, null));
				int rx = newImage.getColorModel().getRed(newImage.getRaster().getDataElements(i, j, null));
				int gx = newImage.getColorModel().getGreen(newImage.getRaster().getDataElements(i, j, null));
				int bx = newImage.getColorModel().getBlue(newImage.getRaster().getDataElements(i, j, null));
				rx *= r;
				gx *= g;
				bx *= b;
				newImage.setRGB(i, j, (ax << 24) | (rx << 16) | (gx << 8) | (bx));
			}
		}
		return newImage;
	}

	private BufferedImage colorImage(Image src, Color color)
	{
		return colorImage(src, (float) (color.getRed() / 255f), (float) (color.getGreen() / 255f), (float) (color.getBlue() / 255f));
	}

	private BufferedImage combineImage(Image imgA, Image imgB, int gap)
	{
		// Convert the image to BufferedImage
		BufferedImage combImg = new BufferedImage(imgA.getWidth(null) + gap + imgB.getWidth(null), Math.max(imgA.getHeight(null), imgB.getHeight(nameLabel)), BufferedImage.TYPE_INT_ARGB);
		// Draw the image on to the buffered image
		Graphics2D g2d = combImg.createGraphics();
		g2d.drawImage(imgA, 0, 0, null);
		g2d.drawImage(imgB, imgA.getWidth(null) + gap, 0, this);
		g2d.dispose();
		return combImg;
	}
}
