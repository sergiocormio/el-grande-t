package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.GrandeTServiceMock;
import model.Player;
import model.ResultantTeam;

public class ResultsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField fileNameTextField;
	private JFileChooser fileChooser;
	private SoccerFieldPanel soccerFieldPanel;
	private StatisticsPanel statisticsPanel;

	public ResultsPanel(){
		generatePanel();
	}

	private void generatePanel() {
		this.setLayout(new BorderLayout());
		addToolBar();
		addSoccerFieldPanel();
		addStatisticsPanel();
	}

	private void addStatisticsPanel() {
		statisticsPanel = new StatisticsPanel();
		this.add(statisticsPanel,BorderLayout.SOUTH);
		
	}

	private void addToolBar() {
		JPanel toolBarPanel = new JPanel(new FlowLayout());
		JButton openButton = new JButton("Abrir");
		fileChooser = new JFileChooser();
		openButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Open a dialog and choose an actionObject File!
				int retVal = fileChooser.showSaveDialog(fileNameTextField);
				if(retVal == JFileChooser.APPROVE_OPTION){
					File file = fileChooser.getSelectedFile();
					fileNameTextField.setText(file.getPath());
					try{
						ResultantTeam team = ElGrandeT.getGrandeTService().retrieveSavedTeam(fileNameTextField.getText());
						soccerFieldPanel.setTeamToDisplay(team.getPlayers());
						statisticsPanel.setStatisticalInformation(team.getStatisticalInformation());
					}catch (Exception e){
						
					}
				}
			}
		});
		toolBarPanel.add(openButton);
		toolBarPanel.add(new JButton("Guardar"));
		fileNameTextField = new JTextField(25);
		fileNameTextField.setText("Elige un archivo de Resultados...");
		//It is read only
		fileNameTextField.setEditable(false);
		toolBarPanel.add(fileNameTextField);
		this.add(toolBarPanel,BorderLayout.NORTH);
	}

	private void addSoccerFieldPanel() {
		soccerFieldPanel = new SoccerFieldPanel();
		this.add(soccerFieldPanel,BorderLayout.CENTER);
	}

}
