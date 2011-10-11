package view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import view.ArmTeamPanel.NewTeamCreatedEvent;

import model.GrandeTServiceMock;
import model.IGrandeTService;

public class ElGrandeT {
	private JFrame jf;
	private JTabbedPane tabPanel;
	private static IGrandeTService grandeTService = new GrandeTServiceMock();
	private ArmTeamPanel armTeamPanel;
	private ResultsPanel resultsPanel;
	
	public ElGrandeT(){
		initializeFrame();
		jf.pack();
		jf.setVisible(true);
	}
	
	//returns the representation of the model
	public static IGrandeTService getGrandeTService(){
		return grandeTService;
	}
	
	private void initializeFrame() {
		jf = new JFrame("El Grande T");
		jf.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
		});
		tabPanel = new JTabbedPane();
		armTeamPanel = new ArmTeamPanel();
		armTeamPanel.addMyEventListener(new ArmTeamPanel.NewTeamCreatedEventListener(){

			@Override
			public void newTeamCreated(NewTeamCreatedEvent evt) {
				//Takes the resultant team and shows the results tab
				resultsPanel.setResultantTeam(evt.getNewTeamCreated());
				tabPanel.setSelectedIndex(1);
			}
			
		});
		
		resultsPanel = new ResultsPanel();
		tabPanel.addTab("Armar Equipo", null, armTeamPanel , "Armar un equipo en base a información sobre jugadores...");
		tabPanel.addTab("Resultados", null,resultsPanel, "Ver los resultados del armado de un equipo...");
		tabPanel.addTab("Comparar", null, new ComparisonPanel(), "Comparar resultados de diferentes equipos...");
		tabPanel.addTab("Base de Datos", null, new JLabel("HOLA"), "Administración de Bases de Datos de jugadores...");
		jf.add(tabPanel);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ElGrandeT();
	}
}
