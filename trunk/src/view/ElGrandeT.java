package view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import model.GrandeTServiceMock;
import model.IGrandeTService;

public class ElGrandeT {
	private JFrame jf;
	private static IGrandeTService grandeTService = new GrandeTServiceMock();
	
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
		JTabbedPane tabPanel = new JTabbedPane();
		//TODO: Add icon
		tabPanel.addTab("Armar Equipo", null, new ArmTeamPanel(), "Armar un equipo en base a información sobre jugadores...");
		tabPanel.addTab("Resultados", null, new ResultsPanel(), "Ver los resultados del armado de un equipo...");
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
