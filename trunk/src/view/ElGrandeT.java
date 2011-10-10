package view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

public class ElGrandeT {
	private JFrame jf;
	
	public ElGrandeT(){
		initializeFrame();
		jf.pack();
		jf.setVisible(true);
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
		tabPanel.addTab("Armar Equipo", null, new ArmTeamPanel(), "I'm sergio");
		tabPanel.addTab("Resultados", null, new JLabel("HOLA"), "I'm sergio");
		tabPanel.addTab("Comparar", null, new JLabel("HOLA"), "I'm sergio");
		tabPanel.addTab("Base de Datos", null, new JLabel("HOLA"), "I'm sergio");
		jf.add(tabPanel);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ElGrandeT();
	}
}
