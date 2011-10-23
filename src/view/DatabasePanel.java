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
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import sun.net.www.content.text.plain;

import model.dto.Player;
import model.dto.PlayersDataBase;

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
	
	public DatabasePanel(){
		generatePanel();
	}

	private void generatePanel() {
		this.setLayout(new BorderLayout());
		addToolBar(); //Toolbar should place in NORTH of the main panel.
		JPanel auxPanel = new JPanel(new BorderLayout());
		this.add(auxPanel,BorderLayout.CENTER);
		addSkillsPanel(auxPanel);
		addPlayersPanel(auxPanel);
		
	}

	private void addPlayersPanel(JPanel parentPanel) {
		//Grid Players Panel
		playersTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(playersTable);
		playersTable.setFillsViewportHeight(true);
		parentPanel.add(scrollPane, BorderLayout.CENTER); 
		
		//South players panel
		JPanel playersToolPanel = new JPanel(new FlowLayout());
		playersToolPanel.setBorder(new TitledBorder("Jugadores"));
		newPlayerButton = new JButton("Agregar");
		playersToolPanel.add(newPlayerButton);
		modifyPlayerButton = new JButton("Modificar");
		playersToolPanel.add(modifyPlayerButton);
		deletePlayerButton = new JButton("Eliminar");
		playersToolPanel.add(deletePlayerButton);
		parentPanel.add(playersToolPanel, BorderLayout.SOUTH);
	}

	private void addSkillsPanel(JPanel parentPanel) {
		JPanel skillsPanel = new JPanel(new FlowLayout());
		skillsPanel.setBorder(new TitledBorder("Habilidades"));
		skillList = new JList(new Vector<String>()); //data has type Object[]
		skillList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		skillList.setLayoutOrientation(JList.VERTICAL);
		skillList.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(skillList);
		listScroller.setPreferredSize(new Dimension(150, 80));
		skillsPanel.add(listScroller);
		newSkillButton = new JButton("Agregar");
		skillsPanel.add(newSkillButton);
		modifySkillButton = new JButton("Modificar");
		skillsPanel.add(modifySkillButton);
		deleteSkillButton = new JButton("Eliminar");
		skillsPanel.add(deleteSkillButton);
		parentPanel.add(skillsPanel,BorderLayout.NORTH);
	}

	private void addToolBar() {
		JPanel toolBarPanel = new JPanel(new FlowLayout());
		JButton newButton = new JButton("Nuevo");
		toolBarPanel.add(newButton);
		
		JButton openButton = new JButton("Abrir");
		fileChooser = new JFileChooser();
		openButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Open a dialog and choose an actionObject File!
				int retVal = fileChooser.showOpenDialog(null);
				if(retVal == JFileChooser.APPROVE_OPTION){
					File file = fileChooser.getSelectedFile();
					fileNameTextField.setText(file.getPath());
					try{
						saveButton.setEnabled(false);
						currentPlayersDataBase = ElGrandeT.getGrandeTService().loadPlayersDataBase(fileNameTextField.getText());
						loadCurrentPlayersDataBase();
						saveButton.setEnabled(true);
					}catch (Exception e){
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}

		});
		toolBarPanel.add(openButton);
		
		saveButton = new JButton("Guardar");
		saveButton.setEnabled(false);
		saveButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Open a dialog and choose an actionObject File!
				int retVal = fileChooser.showSaveDialog(fileNameTextField);
				if(retVal == JFileChooser.APPROVE_OPTION){
					File file = fileChooser.getSelectedFile();
					fileNameTextField.setText(file.getPath());
					try{
						//TODO
					}catch (Exception e){
						//TODO: check exception
					}
				}
			}
		});
		toolBarPanel.add(saveButton);
		fileNameTextField = new JTextField(30);
		fileNameTextField.setText("Elige un archivo de Base de Datos de Jugadores...");
		//It is read only
		fileNameTextField.setEditable(false);
		toolBarPanel.add(fileNameTextField);
		this.add(toolBarPanel,BorderLayout.NORTH);
	}

	private void loadCurrentPlayersDataBase() {
		List<String> skills = currentPlayersDataBase.getSkillList();
		skillList.setListData(skills.toArray());
		
		playersTable.setModel(new DefaultTableModel(getRowsFromPlayers(currentPlayersDataBase.getPlayers()),currentPlayersDataBase.getHeaders().toArray()));
		
	}

	private Object [][] getRowsFromPlayers(List<Player> players) {
		//Object[][] data = {getRowFromPlayer(players.get(0))};
		List<String> headers = currentPlayersDataBase.getHeaders();
		Object[][] data = new Object[players.size()][headers.size()];// {getRowFromPlayer(players.get(0))};
		for (int i=0;i<players.size();i++){
			data[i] = getRowFromPlayer(players.get(i));
		}
		return data;
	}
	
	private Object[] getRowFromPlayer(Player p){
		Object[] result= new Object[3+p.getSkills().size()];
		result[0] = p.getName();
		result[1] = p.getPosition();
		result[2] = p.getPrice();
		
		List<String> skills = currentPlayersDataBase.getSkillList();
		for (int i=3;i<p.getSkills().size()+3;i++){
			result[i] = p.getSkill(skills.get(i-3)).getValue();
		}
		
		return result;
	}
}
