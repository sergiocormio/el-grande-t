package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import model.IGrandeTService;
import model.dto.PlayersDataBase;
import model.dto.ResultantTeam;
import model.dto.UserInputData;

public class ArmTeamPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField dataFileNameTextField;
	private JFileChooser fileChooser;
	private JComboBox skillToMaxCombo;
	private JComboBox formationCombo;
	private JSpinner budgetSpinner;
	private JSpinner numberOfPlayersSpinner;
	private JButton armTeamButton;
	private PlayersDataBase currentPlayersDBSelected;
	
    // Create the listener list
    protected javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
	
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
		dataFileNameTextField.setText("Elige un archivo de Base de Datos...");
		//dataFileNameTextField is read only
		dataFileNameTextField.setEditable(false);
		dataPanel.add(dataFileNameTextField);
		JButton openButton = new JButton("Abrir");
		fileChooser = new JFileChooser();
		openButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Open a dialog and choose an actionObject File!
				int retVal = fileChooser.showOpenDialog(null);
				if(retVal == JFileChooser.APPROVE_OPTION){
					File file = fileChooser.getSelectedFile();
					dataFileNameTextField.setText(file.getPath());
					try{
						//disable armTeam button first
						armTeamButton.setEnabled(false);
						//Load Skills from file!
						currentPlayersDBSelected = ElGrandeT.getGrandeTService().loadPlayersDataBase(dataFileNameTextField.getText());
						List<String> skills = currentPlayersDBSelected.getSkillList();
						
						skillToMaxCombo.removeAllItems();
						skillToMaxCombo.setModel(new DefaultComboBoxModel(skills.toArray()));
						skillToMaxCombo.setSelectedIndex(0);
						//turn back armTeamButton to enable
						armTeamButton.setEnabled(true);
					}catch (Exception e){
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			
		});
		dataPanel.add(openButton);
		this.add(dataPanel);
	}

	private void addBudgetLine(){
		JPanel budgetPanel = new JPanel();
		this.add(new JLabel("Presupuesto:"));
		SpinnerModel budgetModel = new SpinnerNumberModel(IGrandeTService.DEFAULT_BUDGET, 1000, Integer.MAX_VALUE, 500000);
		budgetSpinner = new JSpinner();
		budgetSpinner.setModel(budgetModel);
		budgetPanel.add(budgetSpinner);
		this.add(budgetPanel);
	}
	
	private void addSkillToMaxLine(){
		JPanel skillToMaxPanel = new JPanel();
		this.add(new JLabel("Objetivo a Maximizar:"));
		skillToMaxCombo = new JComboBox();
		skillToMaxPanel.add(skillToMaxCombo);
		this.add(skillToMaxPanel);
	}
	

	private void addFormationLine(){
		JPanel formationPanel = new JPanel();

		this.add(new JLabel("Formación:"));
		formationCombo = new JComboBox(IGrandeTService.FORMATION_LIST);
		formationPanel.add(formationCombo);
		this.add(formationPanel);
	}
	
	private void addNumberOfPlayersLine(){
		JPanel numberPanel = new JPanel();
		this.add(new JLabel("Cantidad a Seleccionar:"));
		SpinnerModel numberModel = new SpinnerNumberModel(IGrandeTService.DEFAULT_NUMBER_OF_PLAYERS, 1, 30, 1);      
		numberOfPlayersSpinner = new JSpinner();
		numberOfPlayersSpinner.setModel(numberModel);
		numberPanel.add(numberOfPlayersSpinner);
		this.add(numberPanel);
	}
	
	private void addArmTeamButton(){
		//leaves an empty space
		this.add(new JLabel());
		armTeamButton = new JButton("Generar Equipo");
		//this button born in disable
		armTeamButton.setEnabled(false);
		armTeamButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//ArmTeam!
				try {
					//set a userInputData first
					UserInputData userInputData = new UserInputData();
					SpinnerNumberModel bm = (SpinnerNumberModel) budgetSpinner.getModel(); 
					userInputData.setBudget(bm.getNumber().longValue());
					userInputData.setPlayersDataBase(currentPlayersDBSelected);
					userInputData.setFormation((String)formationCombo.getSelectedItem());
					SpinnerNumberModel nm = (SpinnerNumberModel) numberOfPlayersSpinner.getModel();
					userInputData.setNumberOfPlayers(nm.getNumber().intValue());
					userInputData.setSkillToMax((String)skillToMaxCombo.getSelectedItem());
					ResultantTeam resultantTeam = ElGrandeT.getGrandeTService().armTeam(userInputData);
					//Dispatches an event with the resultantTeam
					fireMyEvent(new NewTeamCreatedEvent(this,resultantTeam));
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
		});
		this.add(armTeamButton);
	}
	
	// Declare the event. It must extend EventObject.
	public class NewTeamCreatedEvent extends EventObject {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6008270570472295875L;
		private ResultantTeam newTeam;
	    public NewTeamCreatedEvent(Object source,ResultantTeam team) {
	        super(source);
	        newTeam = team;
	    }
	    public ResultantTeam getNewTeamCreated(){
	    	return newTeam;
	    }
	}

	// Declare the listener class. It must extend EventListener.
	// A class must implement this interface to get MyEvents.
	public interface NewTeamCreatedEventListener extends EventListener {
	    public void newTeamCreated(NewTeamCreatedEvent evt);
	}

	// Add the event registration and notification code to a class.

    // This methods allows classes to register for MyEvents
    public void addMyEventListener(NewTeamCreatedEventListener listener) {
        listenerList.add(NewTeamCreatedEventListener.class, listener);
    }

    // This methods allows classes to unregister for MyEvents
    public void removeMyEventListener(NewTeamCreatedEventListener listener) {
        listenerList.remove(NewTeamCreatedEventListener.class, listener);
    }

    // This private class is used to fire MyEvents
    void fireMyEvent(NewTeamCreatedEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i=0; i<listeners.length; i+=2) {
            if (listeners[i]==NewTeamCreatedEventListener.class) {
                ((NewTeamCreatedEventListener)listeners[i+1]).newTeamCreated(evt);
            }
        }
    }
	
}