package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import resources.ResourcesFactory;

import model.IGrandeTService;
import model.Utils;
import model.dto.Player;
import model.dto.PlayersDataBase;
import model.dto.Skill;
import model.dto.Player.Position;
import model.exceptions.ClubAlreadyExistsException;
import model.exceptions.SkillAlreadyExistsException;

public class DatabasePanel extends ImagePanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5848292680965264686L;
	private PlayersDataBase currentPlayersDataBase;
	private JFileChooser fileChooser;
	private JTextField fileNameTextField;
	private JButton saveButton;
	private JList skillList;
	private JButton newSkillButton;
	private JButton deleteSkillButton;
	private JButton modifySkillButton;
	private JList teamList;
	private JButton newTeamButton;
	private JButton deleteTeamButton;
	private JButton modifyTeamButton;
	private JTable playersTable;
	private JButton newPlayerButton;
	private JButton deletePlayerButton;
	private JButton modifyPlayerButton;

	public DatabasePanel() {
		super(new ImageIcon("src/resources/grandt2_930x625.jpg").getImage());
		generatePanel();
	}

	private void generatePanel() {
		this.setLayout(new BorderLayout());
		addToolBar(); //Toolbar should place in NORTH of the main panel.
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.setOpaque(false);
		this.add(centerPanel, BorderLayout.CENTER);
		JPanel centerNorthPanel = new JPanel(new FlowLayout());
		centerNorthPanel.setOpaque(false);
		centerPanel.add(centerNorthPanel,BorderLayout.NORTH);
		addSkillsPanel(centerNorthPanel);
		addTeamsPanel(centerNorthPanel);
		addPlayersPanel(centerPanel);

	}
	
	private void checkNewClubOfPlayer(Player newPlayer) throws ClubAlreadyExistsException{
		//Maybe new player has a new Club...
		String playerClub = newPlayer.getClub();
		if(playerClub!=null && !currentPlayersDataBase.getClubs().contains(playerClub)){
			currentPlayersDataBase.addClub(playerClub);
		}
	}

	private void addPlayersPanel(JPanel parentPanel) {
		// Grid Players Panel
		playersTable = new JTable();
		playersTable.setOpaque(false);
		JScrollPane scrollPane = new JScrollPane(playersTable);
		scrollPane.setOpaque(false);
		playersTable.setFillsViewportHeight(true);
		playersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playersTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting() == false) {

							if (playersTable.getSelectedRow() == -1) {
								// No selection, disable buttons.
								modifyPlayerButton.setEnabled(false);
								deletePlayerButton.setEnabled(false);
							} else {
								// Selection, enable the buttons.
								modifyPlayerButton.setEnabled(true);
								deletePlayerButton.setEnabled(true);
							}
						}

					}

				});
		parentPanel.add(scrollPane, BorderLayout.CENTER);

		// South players panel
		JPanel playersToolPanel = new JPanel(new FlowLayout());
		playersToolPanel.setOpaque(false);
		TitledBorder tb = new TitledBorder("Jugadores");
		tb.setTitleColor(new Color(255,255,255));
		playersToolPanel.setBorder(tb);
		newPlayerButton = new JButton("Agregar",ResourcesFactory.getAddIcon());
		newPlayerButton.setEnabled(false);
		newPlayerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Player newPlayer = new Player("Nuevo Jugador", Position.ARQ,
						"Seleccionar un club", 1000000L);
				for (String skillName : currentPlayersDataBase.getSkillList()) {
					Skill skill = new Skill();
					skill.setName(skillName);
					skill.setValue(0);
					try {
						newPlayer.addSkill(skill);
					} catch (SkillAlreadyExistsException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}
				PlayerDialog dialog = new PlayerDialog(ElGrandeT.mainJFrame, newPlayer, currentPlayersDataBase.getClubs());
				dialog.setVisible(true);
				try {
					if(dialog.getUserSaved()){
						checkNewClubOfPlayer(newPlayer);
						currentPlayersDataBase.addPlayer(newPlayer);
						loadCurrentPlayersDataBase();
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		playersToolPanel.add(newPlayerButton);
		modifyPlayerButton = new JButton("Modificar",ResourcesFactory.getEditIcon());
		modifyPlayerButton.setEnabled(false);
		modifyPlayerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Player player = currentPlayersDataBase.getPlayers().get(playersTable.getSelectedRow());
				PlayerDialog dialog = new PlayerDialog(ElGrandeT.mainJFrame,player,currentPlayersDataBase.getClubs());
				dialog.setVisible(true);
				try {
					if(dialog.getUserSaved()){
						checkNewClubOfPlayer(player);
						loadCurrentPlayersDataBase();
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		playersToolPanel.add(modifyPlayerButton);
		deletePlayerButton = new JButton("Eliminar",ResourcesFactory.getRemoveIcon());
		deletePlayerButton.setEnabled(false);
		deletePlayerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(ElGrandeT.mainJFrame,
						"¿Está seguro que desea eliminar a éste jugador de la lista?",
						"Eliminar Jugador", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					currentPlayersDataBase.deletePlayer(currentPlayersDataBase
							.getPlayers().get(playersTable.getSelectedRow()));
					loadCurrentPlayersDataBase();
				}

			}
		});
		playersToolPanel.add(deletePlayerButton);
		parentPanel.add(playersToolPanel, BorderLayout.SOUTH);
	}

	private void addSkillsPanel(JPanel parentPanel) {
		JPanel skillsPanel = new JPanel(new FlowLayout());
		skillsPanel.setOpaque(false);
		TitledBorder tb = new TitledBorder("Habilidades");
		tb.setTitleColor(new Color(255,255,255));
		skillsPanel.setBorder(tb);
		skillList = new JList(new Vector<String>()); // data has type Object[]
		skillList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		skillList.setLayoutOrientation(JList.VERTICAL);
		skillList.setVisibleRowCount(-1);
		skillList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {

					if (skillList.getSelectedIndex() == -1) {
						// No selection, disable buttons.
						modifySkillButton.setEnabled(false);
						deleteSkillButton.setEnabled(false);
					} else {
						// Selection, enable the buttons.
						modifySkillButton.setEnabled(true);
						deleteSkillButton.setEnabled(true);
					}
				}
			}
		});
		JScrollPane listScroller = new JScrollPane(skillList);
		listScroller.setPreferredSize(new Dimension(150, 80));
		skillsPanel.add(listScroller);
		
		JPanel skillButtonPanel = new JPanel();
		skillButtonPanel.setLayout(new BoxLayout(skillButtonPanel,BoxLayout.Y_AXIS));
		skillButtonPanel.setOpaque(false);
		
		newSkillButton = new JButton("Agregar",ResourcesFactory.getAddIcon());
		newSkillButton.setEnabled(false);
		setButtonSize(newSkillButton);
		newSkillButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String) JOptionPane.showInputDialog(ElGrandeT.mainJFrame,
						"Nombre de la Habilidad:", "Nueva Habilidad",
						JOptionPane.PLAIN_MESSAGE);

				try {
					// If a string was returned, say so.
					if ((s != null) && (s.length() > 0)) {
						Skill skill = new Skill();
						skill.setName(s);
						skill.setValue(0);
						currentPlayersDataBase.addSkillToAllPlayers(skill);
						loadCurrentPlayersDataBase();
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}

			}

		});
		skillButtonPanel.add(newSkillButton);
		modifySkillButton = new JButton("Modificar",ResourcesFactory.getEditIcon());
		modifySkillButton.setEnabled(false);
		setButtonSize(modifySkillButton);
		modifySkillButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String oldValue = (String) skillList.getSelectedValue();
				String newValue = (String) JOptionPane.showInputDialog(ElGrandeT.mainJFrame,
						"Nombre de la Habilidad:", "Modificar Habilidad",
						JOptionPane.PLAIN_MESSAGE, null, null, oldValue);

				try {
					// If a string was returned, say so.
					if ((newValue != null) && (newValue.length() > 0)) {
						currentPlayersDataBase.updateSkillNameToAllPlayers(
								oldValue, newValue);
						loadCurrentPlayersDataBase();
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		skillButtonPanel.add(modifySkillButton);
		deleteSkillButton = new JButton("Eliminar",ResourcesFactory.getRemoveIcon());
		deleteSkillButton.setEnabled(false);
		setButtonSize(deleteSkillButton);
		deleteSkillButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane
						.showConfirmDialog(
								ElGrandeT.mainJFrame,
								"¿Está seguro de eliminar ésta habilidad de todos los jugadores?",
								"Eliminar Habilidad", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					String selectedSkill = (String) skillList
							.getSelectedValue();
					currentPlayersDataBase
							.deleteSkillFromAllPlayers(selectedSkill);
					loadCurrentPlayersDataBase();
				}

			}
		});
		skillButtonPanel.add(deleteSkillButton);
		skillsPanel.add(skillButtonPanel);
		parentPanel.add(skillsPanel);
	}
	
	private void setButtonSize(JButton button){
		Dimension size = new Dimension(120, 25);
		button.setMinimumSize(size);
		button.setMaximumSize(size);
		button.setPreferredSize(size);
		button.setSize(size);
	}
	
	private void addTeamsPanel(JPanel parentPanel) {
		JPanel teamsPanel = new JPanel(new FlowLayout());
		teamsPanel.setOpaque(false);
		TitledBorder tb = new TitledBorder("Equipos");
		tb.setTitleColor(new Color(255,255,255));
		teamsPanel.setBorder(tb);
		teamList = new JList(new Vector<String>()); // data has type Object[]
		teamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		teamList.setLayoutOrientation(JList.VERTICAL);
		teamList.setVisibleRowCount(-1);
		teamList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {

					if (teamList.getSelectedIndex() == -1) {
						// No selection, disable buttons.
						modifyTeamButton.setEnabled(false);
						deleteTeamButton.setEnabled(false);
					} else {
						// Selection, enable the buttons.
						modifyTeamButton.setEnabled(true);
						deleteTeamButton.setEnabled(true);
					}
				}
			}
		});
		JScrollPane listScroller = new JScrollPane(teamList);
		listScroller.setPreferredSize(new Dimension(150, 80));
		teamsPanel.add(listScroller);
		
		JPanel teamButtonPanel = new JPanel();
		teamButtonPanel.setLayout(new BoxLayout(teamButtonPanel,BoxLayout.Y_AXIS));
		teamButtonPanel.setOpaque(false);
		//NEW TEAM BUTTON
		newTeamButton = new JButton("Agregar",ResourcesFactory.getAddIcon());
		newTeamButton.setEnabled(false);
		setButtonSize(newTeamButton);
		newTeamButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String) JOptionPane.showInputDialog(ElGrandeT.mainJFrame,
						"Nombre del Equipo:", "Nuevo Equipo",
						JOptionPane.PLAIN_MESSAGE);

				try {
					// If a string was returned, say so.
					if ((s != null) && (s.length() > 0)) {
						currentPlayersDataBase.addClub(s);
						loadCurrentPlayersDataBase();
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}

			}

		});
		teamButtonPanel.add(newTeamButton);
		//MODIFY TEAM BUTTON
		modifyTeamButton = new JButton("Modificar",ResourcesFactory.getEditIcon());
		modifyTeamButton.setEnabled(false);
		setButtonSize(modifyTeamButton);
		modifyTeamButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String oldValue = (String) teamList.getSelectedValue();
				String newValue = (String) JOptionPane.showInputDialog(ElGrandeT.mainJFrame,
						"Nombre del Equipo:", "Modificar Equipo",
						JOptionPane.PLAIN_MESSAGE, null, null, oldValue);

				try {
					// If a string was returned, say so.
					if ((newValue != null) && (newValue.length() > 0)) {
						currentPlayersDataBase.modifyClub(oldValue, newValue);
						loadCurrentPlayersDataBase();
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		teamButtonPanel.add(modifyTeamButton);
		//DELETE TEAM BUTTON
		deleteTeamButton = new JButton("Eliminar",ResourcesFactory.getRemoveIcon());
		deleteTeamButton.setEnabled(false);
		setButtonSize(deleteTeamButton);
		deleteTeamButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane
						.showConfirmDialog(
								ElGrandeT.mainJFrame,
								"¿Está seguro que desea eliminar este equipo?\n(Se eliminarán también todos los jugadores del equipo)",
								"Eliminar Equipo", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					String selectedTeam = (String) teamList.getSelectedValue();
					currentPlayersDataBase.deleteClub(selectedTeam);
					loadCurrentPlayersDataBase();
				}

			}
		});
		teamButtonPanel.add(deleteTeamButton);
		
		teamsPanel.add(teamButtonPanel);
		parentPanel.add(teamsPanel);
	}

	private void addToolBar() {
		JPanel toolBarPanel = new JPanel(new FlowLayout());
		toolBarPanel.setOpaque(false);
		JButton newButton = new JButton("Nuevo",ResourcesFactory.getNewIcon());
		newButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int result = JOptionPane.YES_OPTION;
				if(saveButton.isEnabled()){
					result = JOptionPane.showConfirmDialog(ElGrandeT.mainJFrame,
							"¿Está seguro que desea crear una nueva planilla de Jugadores?\n(Se perderán los datos que no fueron salvados)",
							"Nueva Planilla de Jugadores", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				}
				if (result == JOptionPane.YES_OPTION) {
					try {
						currentPlayersDataBase = new PlayersDataBase();
						loadCurrentPlayersDataBase();
						saveButton.setEnabled(true);
						newSkillButton.setEnabled(true);
						newTeamButton.setEnabled(true);
						newPlayerButton.setEnabled(true);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(ElGrandeT.mainJFrame, e.getMessage(),
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}

		});
		toolBarPanel.add(newButton);

		JButton openButton = new JButton("Abrir",ResourcesFactory.getOpenIcon());
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto (.txt)","txt"));
		fileChooser.setAcceptAllFileFilterUsed(false);
		openButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Open a dialog and choose an actionObject File!
				int retVal = fileChooser.showOpenDialog(ElGrandeT.mainJFrame);
				if (retVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					fileNameTextField.setText(file.getPath());
					try {
						saveButton.setEnabled(false);
						currentPlayersDataBase = ElGrandeT.getGrandeTService()
								.loadPlayersDataBase(
										fileNameTextField.getText());
						loadCurrentPlayersDataBase();
						saveButton.setEnabled(true);
						newSkillButton.setEnabled(true);
						newTeamButton.setEnabled(true);
						newPlayerButton.setEnabled(true);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(ElGrandeT.mainJFrame, e.getMessage(),
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}

		});
		toolBarPanel.add(openButton);

		saveButton = new JButton("Guardar",ResourcesFactory.getSaveIcon());
		saveButton.setEnabled(false);
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Open a dialog and choose an actionObject File!
				int retVal = fileChooser.showSaveDialog(ElGrandeT.mainJFrame);
				if (retVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					fileNameTextField.setText(Utils.getFileNameWithTxtExtension(file.getPath()));
					try {
						currentPlayersDataBase.saveToFile(fileNameTextField
								.getText());
					} catch (Exception e) {
						JOptionPane.showMessageDialog(ElGrandeT.mainJFrame, e.getMessage(),
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		toolBarPanel.add(saveButton);
		fileNameTextField = new JTextField(30);
		fileNameTextField.setText("Seleccione un archivo de Jugadores...");
		// It is read only
		fileNameTextField.setEditable(false);
		toolBarPanel.add(fileNameTextField);
		this.add(toolBarPanel, BorderLayout.NORTH);
	}

	private void loadCurrentPlayersDataBase() {
		//set skill list values
		List<String> skills = currentPlayersDataBase.getSkillList();
		skillList.setListData(skills.toArray());

		//set teamList values
		Collection<String> teams = currentPlayersDataBase.getClubs();
		teamList.setListData(teams.toArray());
		
		//set playersTable
		playersTable.setModel(new DefaultTableModel(getRowsFromPlayers(currentPlayersDataBase.getPlayers()),
				currentPlayersDataBase.getHeaders().toArray()){
				/**
				 * 
				 */
				private static final long serialVersionUID = 8983499024113385242L;
				//set table is read-Only
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
		});
		playersTable.getColumnModel().getColumn(0).setMinWidth(180);
		playersTable.getColumnModel().getColumn(1).setMinWidth(30);
		playersTable.getColumnModel().getColumn(2).setMinWidth(60);
		playersTable.getColumnModel().getColumn(3).setMinWidth(60);
		
	}

	private Object[][] getRowsFromPlayers(List<Player> players) {
		List<String> headers = currentPlayersDataBase.getHeaders();
		Object[][] data = new Object[players.size()][headers.size()];
		for (int i = 0; i < players.size(); i++) {
			data[i] = getRowFromPlayer(players.get(i));
		}
		return data;
	}

	private Object[] getRowFromPlayer(Player p) {
		int minQuantityOfHeaders = IGrandeTService.DEFAULT_HEADERS.length;
		Object[] result = new Object[minQuantityOfHeaders + p.getSkills().size()];
		result[0] = p.getName();
		result[1] = p.getPosition();
		result[2] = p.getClub();
		result[3] = Utils.displayLikeMoney(p.getPrice());

		List<String> skills = currentPlayersDataBase.getSkillList();
		for (int i = minQuantityOfHeaders; i < p.getSkills().size() + minQuantityOfHeaders; i++) {
			result[i] = p.getSkill(skills.get(i - minQuantityOfHeaders)).getValue();
		}

		return result;
	}
}
