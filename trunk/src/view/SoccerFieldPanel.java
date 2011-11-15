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
import javax.swing.border.TitledBorder;

import model.Utils;
import model.dto.Player;
import model.dto.ResultantTeam;

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
	private JPanel substitutesPanel;

	public SoccerFieldPanel() {
		generatePanel();
	}

	public void setTeamToDisplay(ResultantTeam resultantTeam) {
		// clean all Labels first
		cleanAllLabels();
		String skillToMax = resultantTeam.getStatisticalInformation().getUserInputData().getSkillToMax();
		for (Player p : resultantTeam.getStarters()) {
			JLabel label = new JLabel(p.getName(), new ImageIcon(
					"src/resources/Player Icon 16x30.png"), JLabel.CENTER);
			label.setVerticalTextPosition(JLabel.BOTTOM);
			label.setHorizontalTextPosition(JLabel.CENTER);
			label.setToolTipText("<html> <b>Precio: </b> " + Utils.displayLikeMoney(p.getPrice()) + "<br/><b>" + skillToMax + "</b>: "+ p.getSkill(skillToMax).getValue() + "<br/><b>" + "Equipo" + "</b>: " + p.getClub() + "</html>");
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
		//Renders Substitutes...
		List<Player> substitutes = resultantTeam.getSubstitutes();
		if(substitutes.size()>0){
			substitutesPanel.setBorder(new TitledBorder("Suplentes"));
			JLabel bank = new JLabel(new ImageIcon("src/resources/Banco de Suplentes_small.jpg"));
			substitutesPanel.add(bank);
		}else{
			substitutesPanel.setBorder(null);
		}
		
		for (Player p : substitutes) {
			JLabel label = new JLabel(p.getName());
			label.setVerticalTextPosition(JLabel.BOTTOM);
			label.setHorizontalTextPosition(JLabel.CENTER);
			label.setToolTipText("<html> <b>Precio: </b> " + Utils.displayLikeMoney(p.getPrice()) + "<br/><b>" + skillToMax + "</b>: "+ p.getSkill(skillToMax).getValue() + "<br/><b>" + "Equipo" + "</b>: " + p.getClub() +  "<br/><b>" + "Posición" + "</b>: " + p.getPosition() +"</html>");
			substitutesPanel.add(label);
		}
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
		substitutesPanel.removeAll();
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

		substitutesPanel = new JPanel();
		substitutesPanel.setLayout(new BoxLayout(substitutesPanel,BoxLayout.Y_AXIS));
		this.add(substitutesPanel, BorderLayout.EAST);
		
	}
}
