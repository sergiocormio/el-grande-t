package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.dto.ResultantTeam;

public class ResultsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ResultantTeam currentTeam;
	private JTextField fileNameTextField;
	private JFileChooser fileChooser;
	private JButton saveButton; 
	private SoccerFieldPanel soccerFieldPanel;
	private StatisticsPanel statisticsPanel;

	public ResultsPanel(){
		generatePanel();
	}
	
	public void setResultantTeam(ResultantTeam team){
		currentTeam=team;
		loadCurrentTeam();
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
						currentTeam = ElGrandeT.getGrandeTService().retrieveSavedTeam(fileNameTextField.getText());
						loadCurrentTeam();
					}catch (Exception e){
						//TODO: check exception
					}
				}
			}
		});
		toolBarPanel.add(openButton);
		
		saveButton = new JButton("Guardar");
		saveButton.setEnabled(false);
		saveButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Open a dialog and choose an actionObject File!
				int retVal = fileChooser.showSaveDialog(fileNameTextField);
				if(retVal == JFileChooser.APPROVE_OPTION){
					File file = fileChooser.getSelectedFile();
					fileNameTextField.setText(file.getPath());
					try{
						currentTeam.saveToFile(fileNameTextField.getText());
					}catch (Exception e){
						//TODO: check exception
					}
				}
			}
		});
		toolBarPanel.add(saveButton);
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

	private void loadCurrentTeam(){
		soccerFieldPanel.setTeamToDisplay(currentTeam.getPlayers());
		statisticsPanel.setStatisticalInformation(currentTeam.getStatisticalInformation());
		saveButton.setEnabled(true);
	}
}
