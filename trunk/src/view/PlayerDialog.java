package view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import resources.ResourcesFactory;


import model.dto.Player;
import model.dto.Skill;
import model.dto.Player.Position;

public class PlayerDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8425500129667700319L;
	private Player player;
	private Container content;
	private JTextField playerNameField;
	private JComboBox positionCombo;
	private JSpinner priceSpinner;
	private JSpinner currentSkillValue;
	private JList skillList;
	private Map<String,Integer> skillValuesMap;
	private JComboBox clubCombo;
	private Collection<String> clubs;
	private Boolean userSaved;


	public PlayerDialog(JFrame fr, Player player, Collection<String> clubs){
		super(fr,"Jugador",true);
		this.player = player;
		this.clubs = new HashSet<String>(clubs);
		userSaved = false;
		content = getContentPane();
		content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon("src/resources/user.png"));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		content.add(label);
		addPlayerName();
		addClubs();
		addPosition();
		addPrice();
		addSkills();
		addButtons();
		pack();
		setLocationRelativeTo(fr);
		this.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	private void addPlayerName() {
		JPanel p = new JPanel(new FlowLayout());
		p.add(new JLabel("Nombre del Jugador: "));
		playerNameField = new JTextField(player.getName(), 25);
		p.add(playerNameField);
		p.setAlignmentX(CENTER_ALIGNMENT);
		content.add(p);
	}
	
	private void addPosition() {
		JPanel p = new JPanel(new FlowLayout());
		p.add(new JLabel("Posición: "));
		positionCombo = new JComboBox(Position.values());
		positionCombo.setSelectedItem(player.getPosition());
		p.add(positionCombo);
		p.setAlignmentX(CENTER_ALIGNMENT);
		content.add(p);
	}
	
	private void addPrice() {
		JPanel p = new JPanel(new FlowLayout());
		p.add(new JLabel("Precio: "));
		SpinnerModel priceModel = new SpinnerNumberModel(player.getPrice(), 0, Integer.MAX_VALUE, 1000);
		priceSpinner = new JSpinner();
		priceSpinner.setModel(priceModel);
		p.add(priceSpinner);
		p.setAlignmentX(CENTER_ALIGNMENT);
		content.add(p);
	}
	
	private void addSkills() {
		JPanel p = new JPanel(new FlowLayout());
		p.setBorder(new TitledBorder("Habilidades: "));
		Collection<Skill> skills = player.getSkills().values();
		List<String> skillNames= new ArrayList<String>();
		skillValuesMap = new HashMap<String, Integer>();
		for (Skill s : skills){
			skillNames.add(s.getName());
			skillValuesMap.put(s.getName(), s.getValue());
		}
		skillList = new JList(skillNames.toArray()); // data has type Object[]
		skillList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		skillList.setLayoutOrientation(JList.VERTICAL);
		skillList.setVisibleRowCount(-1);
		skillList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {

					if (skillList.getSelectedIndex() == -1) {
						currentSkillValue.setEnabled(false);
					} else {
						currentSkillValue.setEnabled(true);
						currentSkillValue.setValue(skillValuesMap.get((String)skillList.getSelectedValue()));
					}
				}
			}
		});
		JScrollPane listScroller = new JScrollPane(skillList);
		listScroller.setPreferredSize(new Dimension(150, 80));
		p.add(listScroller);
		
		SpinnerModel skillModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 10);
		currentSkillValue =  new JSpinner();
		currentSkillValue.setEnabled(false);
		currentSkillValue.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				//update skill Values in the map
				skillValuesMap.put((String)skillList.getSelectedValue(),(Integer)currentSkillValue.getValue());
			}
		});
		currentSkillValue.setModel(skillModel);
		p.add(currentSkillValue);
		
		p.setAlignmentX(CENTER_ALIGNMENT);
		content.add(p);
	}
	
	private void addClubs() {
		JPanel p = new JPanel(new FlowLayout());
		p.add(new JLabel("Equipo: "));
		clubCombo = new JComboBox(clubs.toArray());
		clubCombo.setSelectedItem(player.getClub());
		p.add(clubCombo);
		JButton addClubButton = new JButton("Agregar",ResourcesFactory.getAddIcon());
		addClubButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String) JOptionPane.showInputDialog(ElGrandeT.mainJFrame,
						"Nombre del Equipo:", "Nuevo Equipo",
						JOptionPane.PLAIN_MESSAGE);

				try {
					// If a string was returned, say so.
					if ((s != null) && (s.length() > 0)) {
						clubs.add(s.toUpperCase());
						clubCombo.setModel(new DefaultComboBoxModel(clubs.toArray()));
						if(clubCombo.getSelectedIndex()==-1){
							clubCombo.setSelectedItem(s);
						}else{
							clubCombo.setSelectedItem(player.getClub());
						}
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}	
			}
		});
		p.add(addClubButton);
		p.setAlignmentX(CENTER_ALIGNMENT);
		content.add(p);
	}
	
	private void addButtons() {
		JPanel p = new JPanel(new FlowLayout());
		JButton saveButton = new JButton("Guardar",ResourcesFactory.getSaveIcon());
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Saves all information inside player instance
				player.setName(playerNameField.getText());
				player.setClub((String) clubCombo.getSelectedItem());
				player.setPosition((Position) positionCombo.getSelectedItem());
				player.setPrice(((Double)priceSpinner.getValue()).longValue());
				//Sets new skill values
				for (String skillName : skillValuesMap.keySet()){
					player.getSkill(skillName).setValue(skillValuesMap.get(skillName));
				}
				//then mark as saved
				userSaved = true;
				//finally close
				setVisible(false);
				
			}
		});
		p.add(saveButton);
		JButton cancelButton = new JButton("Cancelar");
		cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		p.add(cancelButton);
		p.setAlignmentX(CENTER_ALIGNMENT);
		content.add(p);
	}

	public Boolean getUserSaved() {
		return userSaved;
	}
	
}
