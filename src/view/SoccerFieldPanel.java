package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.dto.Player;

public class SoccerFieldPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4806291985250181830L;
	private JPanel teamLabelsPanel;
	private JPanel arqPanel;
	private JPanel defPanel;
	private JPanel volPanel;
	private JPanel delPanel;

	public SoccerFieldPanel() {
		generatePanel();
	}

	public void setTeamToDisplay(List<Player> team) {
		// clean all Labels first
		cleanAllLabels();
		for (Player p : team) {
			JLabel label = new JLabel(p.getName(), new ImageIcon(
					"src/resources/Player Icon 16x30.png"), JLabel.CENTER);
			label.setVerticalTextPosition(JLabel.BOTTOM);
			label.setHorizontalTextPosition(JLabel.CENTER);
			label.setToolTipText("Precio: $" + p.getPrice());
			switch (p.getPosition()) {
			case ARQ:
				label.setIcon(new ImageIcon(
						"src/resources/GoalKeeper Icon 16x30.png"));
				label.setForeground(new Color(255, 0, 0));
				arqPanel.add(label);
				break;
			case DEF:
				label.setForeground(new Color(0, 255, 0));
				defPanel.add(label);
				break;
			case VOL:
				label.setForeground(new Color(255, 255, 255));
				volPanel.add(label);
				break;
			case DEL:
				label.setForeground(new Color(0, 255, 255));
				delPanel.add(label);
				break;
			}
		}
		organizeTeamLabelsPanel();
	}

	private void organizeTeamLabelsPanel() {
		teamLabelsPanel.removeAll();
		
		teamLabelsPanel.add(arqPanel);
		teamLabelsPanel.add(defPanel);
		teamLabelsPanel.add(volPanel);
		teamLabelsPanel.add(delPanel);
	}

	private void cleanAllLabels() {
		arqPanel.removeAll();
		defPanel.removeAll();
		volPanel.removeAll();
		delPanel.removeAll();
	}

	private void generatePanel() {
		// generates imagePanel with a soccer field as background image.
		ImagePanel imagePanel = new ImagePanel(new ImageIcon(
				"src/resources/Cancha 500.png").getImage());
		imagePanel.setLayout(new BorderLayout());

		teamLabelsPanel = new JPanel();
		teamLabelsPanel.setOpaque(false);
		teamLabelsPanel.setLayout(new BoxLayout(teamLabelsPanel,
				BoxLayout.Y_AXIS));
		
		// arqPanel
		arqPanel = new JPanel(new FlowLayout());
		arqPanel.setOpaque(false);
		arqPanel.setMaximumSize(new Dimension(330, 20));
		
		// defPanel
		defPanel = new JPanel(new FlowLayout());
		defPanel.setOpaque(false);
	

		// volPanel
		volPanel = new JPanel(new FlowLayout());
		volPanel.setOpaque(false);
		

		// delPanel
		delPanel = new JPanel(new FlowLayout());
		delPanel.setOpaque(false);
		

		imagePanel.add(teamLabelsPanel, BorderLayout.CENTER);
		this.add(imagePanel);
	}
}
