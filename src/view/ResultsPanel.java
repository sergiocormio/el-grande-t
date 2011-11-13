package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import resources.ResourcesFactory;

import model.Utils;
import model.dto.ResultantTeam;

public class ResultsPanel extends ImagePanel {

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
		super(new ImageIcon("src/resources/grandt1_930x625.jpg").getImage());
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
		statisticsPanel.setOpaque(true);
		this.add(statisticsPanel,BorderLayout.SOUTH);
		
	}

	private void addToolBar() {
		JPanel toolBarPanel = new JPanel(new FlowLayout());
		toolBarPanel.setOpaque(false);
		JButton openButton = new JButton("Abrir",ResourcesFactory.getOpenIcon());
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto (.txt)","txt"));
		fileChooser.setAcceptAllFileFilterUsed(false);
		openButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Open a dialog and choose an actionObject File!
				int retVal = fileChooser.showOpenDialog(null);
				if(retVal == JFileChooser.APPROVE_OPTION){
					File file = fileChooser.getSelectedFile();
					fileNameTextField.setText(file.getPath());
					try{
						currentTeam = ElGrandeT.getGrandeTService().retrieveSavedTeam(fileNameTextField.getText());
						loadCurrentTeam();
					}catch (Exception e){
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		toolBarPanel.add(openButton);
		
		saveButton = new JButton("Guardar",ResourcesFactory.getSaveIcon());
		saveButton.setEnabled(false);
		saveButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Open a dialog and choose an actionObject File!
				int retVal = fileChooser.showSaveDialog(fileNameTextField);
				if(retVal == JFileChooser.APPROVE_OPTION){
					File file = fileChooser.getSelectedFile();
					fileNameTextField.setText(Utils.getFileNameWithTxtExtension(file.getPath()));
					try{
						currentTeam.saveToFile(fileNameTextField.getText());
					}catch (Exception e){
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		toolBarPanel.add(saveButton);
		fileNameTextField = new JTextField(25);
		fileNameTextField.setText("Seleccione un archivo de Resultados...");
		//It is read only
		fileNameTextField.setEditable(false);
		toolBarPanel.add(fileNameTextField);
		this.add(toolBarPanel,BorderLayout.NORTH);
	}

	private void addSoccerFieldPanel() {
		soccerFieldPanel = new SoccerFieldPanel();
		soccerFieldPanel.setOpaque(false);
		this.add(soccerFieldPanel,BorderLayout.CENTER);
	}

	private void loadCurrentTeam(){
		soccerFieldPanel.setTeamToDisplay(currentTeam);
		statisticsPanel.setStatisticalInformation(currentTeam.getStatisticalInformation());
		saveButton.setEnabled(true);
	}
}
