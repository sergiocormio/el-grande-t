package model.dto;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import model.Fileable;
import model.Serializable;
import model.Utils;
import model.dto.Player.Position;

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
	
	public List<Player> getSubstitutes() {
		//TODO: Players minus Starters
		List<Player> resultList = new ArrayList<Player>();
		resultList.addAll(players);
		for(Player p : getStarters()){
			resultList.remove(p);
		}
		return resultList;
	}
	
	public List<Player> getStarters() {
		List<Player> resultList = new ArrayList<Player>();
		//sorts players by Skill DESC!
		Collections.sort(players, new Comparator<Player>(){
			@Override
			public int compare(Player o1, Player o2) {
				Skill skill1 = o1.getSkill(getStatisticalInformation().getUserInputData().getSkillToMax());
				Skill skill2 = o2.getSkill(getStatisticalInformation().getUserInputData().getSkillToMax());
				return skill2.getValue()-skill1.getValue();
			}
		});
		String formation = getStatisticalInformation().getUserInputData().getFormation();
		if(players.size()>11){
			resultList.addAll(getFirstPlayersByPosition(players,Utils.getQuantityOfPlayersByPosition(Position.ARQ, formation),Position.ARQ));
			resultList.addAll(getFirstPlayersByPosition(players,Utils.getQuantityOfPlayersByPosition(Position.VOL, formation),Position.VOL));
			resultList.addAll(getFirstPlayersByPosition(players,Utils.getQuantityOfPlayersByPosition(Position.DEF, formation),Position.DEF));
			resultList.addAll(getFirstPlayersByPosition(players,Utils.getQuantityOfPlayersByPosition(Position.DEL, formation),Position.DEL));
			return resultList;
		}else{
			return players;
		}
	}
	
	private List<Player> getFirstPlayersByPosition(List<Player> list,int quantity,Position pos){
		int i = 0;
		List<Player> resultList = new ArrayList<Player>();
		while (resultList.size()<quantity && i<list.size()){
			Player auxPlayer = list.get(i);
			if(auxPlayer.getPosition()==pos){
				resultList.add(auxPlayer);
			}
			i++;
		}
		return resultList;
	}

	@Override
	public void saveToFile(String fileName) {
		
		PrintWriter writer = Utils.getPrintWriter(fileName);
		
		String statsLine = createStatsLine();
		writer.println(statsLine);
		
		PlayersDataBase playersDB = statisticalInformation.getUserInputData().getPlayersDataBase();
		String headerLine = createHeaderLine(playersDB.getHeaders());
		writer.println(headerLine);
		
		for (Player player : this.players) {
			
			String playerLine = createPlayerLine(player, playersDB.getSkillList());
			writer.println(playerLine);
		}
		
		writer.close();
	}

	@Override
	public void saveToFile() {
		
		saveToFile(this.file.getAbsolutePath());		
	}
	
	
	private String createStatsLine() {
		
		String statsLine = String.valueOf(statisticalInformation.getTotalProfit());
		statsLine += Utils.FIELD_SEPARATOR;
		statsLine += statisticalInformation.getTime();
		statsLine += Utils.FIELD_SEPARATOR;
		statsLine += statisticalInformation.getFinalCost();
		statsLine += Utils.FIELD_SEPARATOR;
		
		statsLine = createUserInputDataLineFields(statsLine, statisticalInformation.getUserInputData());
		
		return statsLine;
	}
	
	
	private String createUserInputDataLineFields(String statsLine, UserInputData userInputData) {

		statsLine += userInputData.getBudget();
		statsLine += Utils.FIELD_SEPARATOR;
		statsLine += userInputData.getSkillToMax();
		statsLine += Utils.FIELD_SEPARATOR;
		statsLine += userInputData.getFormation();
		statsLine += Utils.FIELD_SEPARATOR;
		statsLine += userInputData.getNumberOfPlayers();
		//el ultimo no va
		//statsLine += Utils.FIELD_SEPARATOR;
		
		return statsLine;
	}

	private String createHeaderLine(List<String> headers) {
		String headersLine = "";
		
		for (String header : headers) {
			
			headersLine += header;
			headersLine += Utils.FIELD_SEPARATOR;
		}
		
		return headersLine;
	}

	private String createPlayerLine(Player player, List<String> skills) {
		String playerLine = "";
		
		playerLine += player.getName();
		playerLine += Utils.FIELD_SEPARATOR;
		playerLine += player.getPosition().name();
		playerLine += Utils.FIELD_SEPARATOR;
		playerLine += player.getClub();
		playerLine += Utils.FIELD_SEPARATOR;
		playerLine += player.getPrice();
		playerLine += Utils.FIELD_SEPARATOR;
		
		for (String strSkill : skills) {
			Skill skill = player.getSkill(strSkill);
			String skillValue = "0";
			if(skill != null)
				skillValue = String.valueOf(skill.getValue());
			
			playerLine += skillValue;
			playerLine += Utils.FIELD_SEPARATOR;
			
		}
		
		return playerLine;
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
