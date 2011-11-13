package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
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
import javax.swing.filechooser.FileNameExtensionFilter;

import resources.ResourcesFactory;

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
	private JComboBox numberOfPlayersCombo;
	private JSpinner numberOfPlayersPerClubSpinner;
	private JButton armTeamButton;
	private PlayersDataBase currentPlayersDBSelected;
	
    // Create the listener list
    protected javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
	
	public ArmTeamPanel(){
		generatePanel();
	}

	private void generatePanel() {
		this.setLayout(new BorderLayout());
		ImagePanel inputPanel = new ImagePanel(new ImageIcon("src/resources/grandt3_930x625.jpg").getImage());
		inputPanel.setLayout(new BoxLayout(inputPanel,BoxLayout.Y_AXIS));
		
		//adds lines
		addImageLine(inputPanel);
		addDataLine(inputPanel);
		addBudgetLine(inputPanel);
		addSkillToMaxLine(inputPanel);
		addFormationLine(inputPanel);
		addNumberOfPlayersLine(inputPanel);
		addNumberOfPlayersPerClubLine(inputPanel);
		
		addArmTeamButton(inputPanel);
		inputPanel.setAlignmentY(CENTER_ALIGNMENT);
		inputPanel.setBorder(BorderFactory.createEmptyBorder(10,10,80,10));
		this.add(inputPanel,BorderLayout.CENTER);
	}

	private void addImageLine(JPanel parentPanel) {
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon("src/resources/mr-t.png"));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		parentPanel.add(label);
		parentPanel.add(new JLabel(" "));
	}
	
	private void addDataLine(JPanel parentPanel) {
		JPanel dataPanel = new JPanel();
		dataPanel.setOpaque(false);
		JLabel dataLabel = new JLabel("Datos:");
		dataLabel.setForeground(new Color(0));
		dataPanel.add(dataLabel);
		dataFileNameTextField = new JTextField(20);
		dataLabel.setLabelFor(dataFileNameTextField);
		dataFileNameTextField.setText("Seleccione un archivo de Jugadores...");
		//dataFileNameTextField is read only
		dataFileNameTextField.setEditable(false);
		dataPanel.add(dataFileNameTextField);
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
		parentPanel.add(dataPanel);
	}

	private void addBudgetLine(JPanel parentPanel){
		JPanel budgetPanel = new JPanel();
		budgetPanel.setOpaque(false);
		JLabel budgetLabel = new JLabel("Presupuesto: $");
		budgetLabel.setForeground(new Color(0));
		budgetPanel.add(budgetLabel);
		SpinnerModel budgetModel = new SpinnerNumberModel(IGrandeTService.DEFAULT_BUDGET, 1000, Integer.MAX_VALUE, 500000);
		budgetSpinner = new JSpinner();
		budgetLabel.setLabelFor(budgetSpinner);
		budgetSpinner.setModel(budgetModel);
		budgetPanel.add(budgetSpinner);
		parentPanel.add(budgetPanel);
	}
	
	private void addSkillToMaxLine(JPanel parentPanel){
		JPanel skillToMaxPanel = new JPanel();
		skillToMaxPanel.setOpaque(false);
		JLabel skilToMaxLabel = new JLabel("Objetivo a Maximizar:");
		skilToMaxLabel.setForeground(new Color(0));
		skillToMaxPanel.add(skilToMaxLabel);
		skillToMaxCombo = new JComboBox();
		skilToMaxLabel.setLabelFor(skillToMaxCombo);
		skillToMaxPanel.add(skillToMaxCombo);
		parentPanel.add(skillToMaxPanel);
	}
	

	private void addFormationLine(JPanel parentPanel){
		JPanel formationPanel = new JPanel();
		formationPanel.setOpaque(false);
		JLabel formationLabel = new JLabel("Formación:");
		formationLabel.setForeground(new Color(0));
		formationPanel.add(formationLabel);
		formationCombo = new JComboBox(IGrandeTService.FORMATION_LIST);
		formationLabel.setLabelFor(formationCombo);
		formationPanel.add(formationCombo);
		parentPanel.add(formationPanel);
	}
	
	private void addNumberOfPlayersLine(JPanel parentPanel){
		JPanel numberOfPlayersPanel = new JPanel();
		numberOfPlayersPanel.setOpaque(false);
		JLabel numberOfPlayersLabel = new JLabel("Cantidad a Seleccionar:");
		numberOfPlayersLabel.setForeground(new Color(0));
		numberOfPlayersPanel.add(numberOfPlayersLabel);
		numberOfPlayersCombo = new JComboBox(IGrandeTService.NUMBER_OF_PLAYERS_LIST);
		numberOfPlayersPanel.add(numberOfPlayersCombo);
		parentPanel.add(numberOfPlayersPanel);
	}
	
	private void addNumberOfPlayersPerClubLine(JPanel parentPanel){
		JPanel numberPanel = new JPanel();
		numberPanel.setOpaque(false);
		JLabel numberPerClubLabel = new JLabel("Cantidad de Jugadores por Club:");
		numberPerClubLabel.setForeground(new Color(0));
		
		SpinnerModel numberPerClubModel = new SpinnerNumberModel(IGrandeTService.DEFAULT_NUMBER_OF_PLAYERS_PER_CLUB, 1, 22, 1);
		numberOfPlayersPerClubSpinner = new JSpinner();
		
		numberPerClubLabel.setLabelFor(numberPanel);
		numberOfPlayersPerClubSpinner.setModel(numberPerClubModel);
				
		numberPanel.add(numberPerClubLabel);
		numberPanel.add(numberOfPlayersPerClubSpinner);
		parentPanel.add(numberPanel);
		//this.add(numberOfPlayersSpinner);
	}
	
	private void addArmTeamButton(JPanel parentPanel){
		//leaves an empty space
		parentPanel.add(new JLabel(" "));
		armTeamButton = new JButton("Generar Equipo",ResourcesFactory.getGenerateIcon());
		//this button born in disable
		armTeamButton.setEnabled(false);
		armTeamButton.setAlignmentX(CENTER_ALIGNMENT);
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
					userInputData.setNumberOfPlayers(Integer.valueOf((String)numberOfPlayersCombo.getSelectedItem()));
					SpinnerNumberModel nmPerClub = (SpinnerNumberModel) numberOfPlayersPerClubSpinner.getModel();
					userInputData.setNumberOfPlayersPerClub(nmPerClub.getNumber().intValue());
					
					userInputData.setSkillToMax((String)skillToMaxCombo.getSelectedItem());
					ResultantTeam resultantTeam = ElGrandeT.getGrandeTService().armTeam(userInputData);
					//Dispatches an event with the resultantTeam
					fireMyEvent(new NewTeamCreatedEvent(this,resultantTeam));
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		parentPanel.add(armTeamButton);
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
