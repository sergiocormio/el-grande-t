package model.dto;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import model.Fileable;
import model.Serializable;
import model.Utils;

public class ResultantTeam implements Fileable, Serializable{

	private File file;
	private List<Player> players;
	private StatisticalInformation statisticalInformation;
	
	public ResultantTeam(){
		players = new ArrayList<Player>();
	}
	
	public ResultantTeam(File file, List<Player> players, StatisticalInformation information){
		this.file = file;
		this.players = players;
		this.statisticalInformation = information;
	}
	
	public StatisticalInformation getStatisticalInformation() {
		return statisticalInformation;
	}

	public void setStatisticalInformation(
			StatisticalInformation statisticalInformation) {
		this.statisticalInformation = statisticalInformation;
	}

	public List<Player> getPlayers() {
		return players;
	}

	@Override
	public void saveToFile(String fileName) {
		
		PrintWriter writer = Utils.getPrintWriter(fileName);
		
		String statsLine = createStatsLine();
		writer.println(statsLine);
		
		String headerLine = createHeaderLine();
		writer.println(headerLine);
		
		for (Player player : this.players) {
			
			String playerLine = createPlayerLine(player);
			writer.println(playerLine);
		}
	}

	@Override
	public void saveToFile() {
		
		saveToFile(this.file.getAbsolutePath());		
	}
	
	
	private String createStatsLine() {
		String statsLine = null;
		
		statsLine += statisticalInformation.getTotalProfit();
		statsLine += Utils.FIELD_SEPARATOR;
		statsLine += statisticalInformation.getTime();
		statsLine += Utils.FIELD_SEPARATOR;
		statsLine += statisticalInformation.getFinalCost();
		statsLine += Utils.FIELD_SEPARATOR;
		
		statsLine += createUserInputDataLineFields(statsLine, statisticalInformation.getUserInputData());
		
		return statsLine;
	}
	
	
	private String createUserInputDataLineFields(String statsLine, UserInputData userInputData) {

		statsLine += userInputData.getDbFileName();
		statsLine += Utils.FIELD_SEPARATOR;
		statsLine += userInputData.getBudget();
		statsLine += Utils.FIELD_SEPARATOR;
		statsLine += userInputData.getSkillToMax();
		statsLine += Utils.FIELD_SEPARATOR;
		statsLine += userInputData.getFormation();
		statsLine += Utils.FIELD_SEPARATOR;
		statsLine += userInputData.getNumberOfPlayers();
		statsLine += Utils.FIELD_SEPARATOR;
		
		return statsLine;
	}

	private String createHeaderLine() {
		// TODO Auto-generated method stub
		return null;
	}

	private String createPlayerLine(Player player) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void addPlayer(Player player){
		this.players.add(player);
	}

}
