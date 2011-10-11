package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Player;

public class SoccerFieldPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4806291985250181830L;
	private JPanel arqPanel;
	private JPanel defPanel;
	private JPanel volPanel;
	private JPanel delPanel;

	
	public SoccerFieldPanel(){
		generatePanel();
	}
	
	public void setTeamToDisplay(List<Player> team){
		//clean all Labels first
		cleanAllLabels();
		for(Player p : team){
			JLabel label = new JLabel(p.getName());
			label.setToolTipText("Precio: $" + p.getPrice());
			switch (p.getPosition()){
				case ARQ:
					label.setForeground(new Color(255,0,0));
					arqPanel.add(label);
					break;
				case DEF:
					label.setForeground(new Color(0,255,0));
					defPanel.add(label);
					break;
				case VOL: 
					label.setForeground(new Color(50,50,255));
					volPanel.add(label);
					break;
				case DEL: 
					label.setForeground(new Color(0,255,255));
					delPanel.add(label);
					break;
			}
		}
	}
	
	private void cleanAllLabels() {
		arqPanel.removeAll();
		defPanel.removeAll();
		volPanel.removeAll();
		delPanel.removeAll();
	}

	private void generatePanel(){
		//generates imagePanel with a soccer field as background image.
	    ImagePanel imagePanel = new ImagePanel(new ImageIcon("src/resources/Cancha.jpg").getImage());
	    imagePanel.setLayout(new BorderLayout());
	    
	    JPanel teamLabelsPanel = new JPanel();
	    teamLabelsPanel.setOpaque(false);
	    teamLabelsPanel.setLayout(new BoxLayout(teamLabelsPanel,BoxLayout.Y_AXIS));
	    //Adds an spacer
	    teamLabelsPanel.add(new JLabel("                        "));
	    //arqPanel
	    arqPanel = new JPanel(new FlowLayout());
	    arqPanel.setOpaque(false);
	    teamLabelsPanel.add(arqPanel);
	    //defPanel
	    defPanel = new JPanel(new FlowLayout());
	    defPanel.setOpaque(false);
	    teamLabelsPanel.add(defPanel);
	    
	    //volPanel
	    volPanel = new JPanel(new FlowLayout());
	    volPanel.setOpaque(false);
	    teamLabelsPanel.add(volPanel);
	    
	    //delPanel
	    delPanel = new JPanel(new FlowLayout());
	    delPanel.setOpaque(false);
	    teamLabelsPanel.add(delPanel);
	    
	    //Adds an spacer
	    teamLabelsPanel.add(new JLabel("                        "));
	    
	    
	    imagePanel.add(teamLabelsPanel, BorderLayout.CENTER);
	    this.add(imagePanel);
	}
}
