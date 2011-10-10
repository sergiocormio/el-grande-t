package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class ArmTeamPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int DEFAULT_BUDGET = 60000000;
	public static int DEFAULT_NUMBER_OF_PLAYERS = 11;
	
	
	private JTextField dataFileNameTextField;
	private JFileChooser fileChooser;
	private JComboBox skillToMaxCombo;
	private JComboBox formationCombo;
	private JSpinner numberOfPlayersSpinner;
	private JButton armTeamButton;
	
	public ArmTeamPanel(){
		generatePanel();
	}

	private void generatePanel() {
		this.setLayout(new GridLayout(0,2));
		//adds lines
		addDataLine();
		addBudgetLine();
		addSkillToMaxLine();
		addFormationLine();
		addNumberOfPlayersLine();
		addArmTeamButton();
	}

	private void addDataLine() {
		JPanel dataPanel = new JPanel();
		this.add(new JLabel("Datos:"));
		dataFileNameTextField = new JTextField(25);
		dataPanel.add(dataFileNameTextField);
		JButton openButton = new JButton("Open");
		fileChooser = new JFileChooser();
		openButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Open a dialog and choose an actionObject File!
				int retVal = fileChooser.showSaveDialog(dataFileNameTextField);
				if(retVal == JFileChooser.APPROVE_OPTION){
					File file = fileChooser.getSelectedFile();
					dataFileNameTextField.setText(file.getPath());
					//TODO: Load Skills from file!
				}
			}
			
		});
		dataPanel.add(openButton);
		this.add(dataPanel);
	}

	private void addBudgetLine(){
		JPanel budgetPanel = new JPanel();
		this.add(new JLabel("Presupuesto:"));
		SpinnerModel budgetModel = new SpinnerNumberModel(DEFAULT_BUDGET, 1000, Integer.MAX_VALUE, 500000);
		JSpinner budgetSpinner = new JSpinner();
		budgetSpinner.setModel(budgetModel);
		budgetPanel.add(budgetSpinner);
		this.add(budgetPanel);
	}
	
	private void addSkillToMaxLine(){
		JPanel skillToMaxPanel = new JPanel();
		this.add(new JLabel("Objetivo a Maximizar:"));
		String[] skillList = { "Ataque", "Defensa", "Puntos Pokemon"};
		skillToMaxCombo = new JComboBox(skillList);
		skillToMaxPanel.add(skillToMaxCombo);
		this.add(skillToMaxPanel);
	}
	

	private void addFormationLine(){
		JPanel formationPanel = new JPanel();

		this.add(new JLabel("Formación:"));
		String[] formationList = { "4-4-2","4-3-3","4-2-4","5-3-2"};
		formationCombo = new JComboBox(formationList);
		formationPanel.add(formationCombo);
		this.add(formationPanel);
	}
	
	private void addNumberOfPlayersLine(){
		JPanel numberPanel = new JPanel();
		this.add(new JLabel("Cantidad a Seleccionar:"));
		SpinnerModel numberModel = new SpinnerNumberModel(DEFAULT_NUMBER_OF_PLAYERS, 1, 30, 1);      
		numberOfPlayersSpinner = new JSpinner();
		numberOfPlayersSpinner.setModel(numberModel);
		numberPanel.add(numberOfPlayersSpinner);
		this.add(numberPanel);
	}
	
	private void addArmTeamButton(){
		this.add(new JLabel());
		armTeamButton = new JButton("Generar Equipo");
		armTeamButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: Implement this method
			}
			
		});
		this.add(armTeamButton);
	}
}
