package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.dto.Player;
import model.dto.PlayersDataBase;
import model.dto.Skill;
import model.dto.Player.Position;
import model.exceptions.SkillAlreadyExistsException;

public class DatabasePanel extends JPanel {

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
	private JTable playersTable;
	private JButton newPlayerButton;
	private JButton deletePlayerButton;
	private JButton modifyPlayerButton;

	public DatabasePanel() {
		generatePanel();
	}

	private void generatePanel() {
		this.setLayout(new BorderLayout());
		addToolBar(); // Toolbar should place in NORTH of the main panel.
		JPanel auxPanel = new JPanel(new BorderLayout());
		this.add(auxPanel, BorderLayout.CENTER);
		addSkillsPanel(auxPanel);
		addPlayersPanel(auxPanel);

	}

	private void addPlayersPanel(JPanel parentPanel) {
		// Grid Players Panel
		playersTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(playersTable);
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
		playersToolPanel.setBorder(new TitledBorder("Jugadores"));
		newPlayerButton = new JButton("Agregar");
		newPlayerButton.setEnabled(false);
		newPlayerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Player newPlayer = new Player("Nuevo Jugador", Position.ARQ,
						1000000L);
				for (String skillName : currentPlayersDataBase.getSkillList()) {
					Skill skill = new Skill();
					skill.setName(skillName);
					skill.setValue(0);
					try {
						newPlayer.addSkill(skill);
					} catch (SkillAlreadyExistsException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				PlayerDialog dialog = new PlayerDialog(null, newPlayer);
				dialog.setVisible(true);
				try {
					currentPlayersDataBase.addPlayer(newPlayer);
					loadCurrentPlayersDataBase();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		playersToolPanel.add(newPlayerButton);
		modifyPlayerButton = new JButton("Modificar");
		modifyPlayerButton.setEnabled(false);
		modifyPlayerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new PlayerDialog(null, currentPlayersDataBase.getPlayers().get(
						playersTable.getSelectedRow())).setVisible(true);
				loadCurrentPlayersDataBase();
			}
		});
		playersToolPanel.add(modifyPlayerButton);
		deletePlayerButton = new JButton("Eliminar");
		deletePlayerButton.setEnabled(false);
		deletePlayerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null,
						"¿Está seguro a éste jugador de la lista?",
						"Eliminar Jugador", JOptionPane.YES_NO_OPTION);
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
		skillsPanel.setBorder(new TitledBorder("Habilidades"));
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
		newSkillButton = new JButton("Agregar");
		newSkillButton.setEnabled(false);
		newSkillButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String) JOptionPane.showInputDialog(null,
						"Nombre de la Habilidad:", "Nueva Habilidad",
						JOptionPane.PLAIN_MESSAGE);

				try {
					// If a string was returned, say so.
					if ((s != null) && (s.length() > 0)) {
						Skill skill = new Skill();
						skill.setName(s.toUpperCase());
						skill.setValue(0);
						currentPlayersDataBase.addSkillToAllPlayers(skill);
						loadCurrentPlayersDataBase();
					}
				} catch (Exception ex) {
					// TODO: handle exception
				}

			}

		});
		skillsPanel.add(newSkillButton);
		modifySkillButton = new JButton("Modificar");
		modifySkillButton.setEnabled(false);
		modifySkillButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String oldValue = (String) skillList.getSelectedValue();
				String newValue = (String) JOptionPane.showInputDialog(null,
						"Nombre de la Habilidad:", "Modificar Habilidad",
						JOptionPane.PLAIN_MESSAGE, null, null, oldValue);

				try {
					// If a string was returned, say so.
					if ((newValue != null) && (newValue.length() > 0)) {
						currentPlayersDataBase.updateSkillNameToAllPlayers(
								oldValue, newValue.toUpperCase());
						loadCurrentPlayersDataBase();
					}
				} catch (Exception ex) {
					// TODO: handle exception
				}

			}
		});
		skillsPanel.add(modifySkillButton);
		deleteSkillButton = new JButton("Eliminar");
		deleteSkillButton.setEnabled(false);
		deleteSkillButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane
						.showConfirmDialog(
								null,
								"¿Está seguro de eliminar ésta habilidad de todos los jugadores?",
								"Eliminar Habilidad", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					String selectedSkill = (String) skillList
							.getSelectedValue();
					currentPlayersDataBase
							.deleteSkillFromAllPlayers(selectedSkill);
					loadCurrentPlayersDataBase();
				}

			}
		});
		skillsPanel.add(deleteSkillButton);
		parentPanel.add(skillsPanel, BorderLayout.NORTH);
	}

	private void addToolBar() {
		JPanel toolBarPanel = new JPanel(new FlowLayout());
		JButton newButton = new JButton("Nuevo");
		newButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO: Ask if save first
				try {
					currentPlayersDataBase = new PlayersDataBase();
					loadCurrentPlayersDataBase();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		toolBarPanel.add(newButton);

		JButton openButton = new JButton("Abrir");
		fileChooser = new JFileChooser();
		openButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Open a dialog and choose an actionObject File!
				int retVal = fileChooser.showOpenDialog(null);
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
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage(),
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}

		});
		toolBarPanel.add(openButton);

		saveButton = new JButton("Guardar");
		saveButton.setEnabled(false);
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Open a dialog and choose an actionObject File!
				int retVal = fileChooser.showSaveDialog(fileNameTextField);
				if (retVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					fileNameTextField.setText(file.getPath());
					try {
						currentPlayersDataBase.saveToFile(fileNameTextField
								.getText());
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage(),
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		toolBarPanel.add(saveButton);
		fileNameTextField = new JTextField(30);
		fileNameTextField
				.setText("Elige un archivo de Base de Datos de Jugadores...");
		// It is read only
		fileNameTextField.setEditable(false);
		toolBarPanel.add(fileNameTextField);
		this.add(toolBarPanel, BorderLayout.NORTH);
	}

	private void loadCurrentPlayersDataBase() {
		List<String> skills = currentPlayersDataBase.getSkillList();
		skillList.setListData(skills.toArray());

		playersTable.setModel(new DefaultTableModel(
				getRowsFromPlayers(currentPlayersDataBase.getPlayers()),
				currentPlayersDataBase.getHeaders().toArray()));

		newSkillButton.setEnabled(true);
		newPlayerButton.setEnabled(true);
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
		Object[] result = new Object[3 + p.getSkills().size()];
		result[0] = p.getName();
		result[1] = p.getPosition();
		result[2] = p.getPrice();

		List<String> skills = currentPlayersDataBase.getSkillList();
		for (int i = 3; i < p.getSkills().size() + 3; i++) {
			result[i] = p.getSkill(skills.get(i - 3)).getValue();
		}

		return result;
	}
}
