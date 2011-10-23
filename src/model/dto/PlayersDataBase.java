package model.dto;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import model.Fileable;
import model.Serializable;
import model.Utils;

public class PlayersDataBase implements Fileable, Serializable {

	private File file;
	private List<Player> players;
	private List<String> headers;
	
	
	public PlayersDataBase(){
		players = new ArrayList<Player>();
	}
	
	public PlayersDataBase(List<String> headers, List<Player> players, File file){
		this.headers = headers;
		this.players = players;
		this.file = file;
	}
	
	public List<String> getSkillList(){
		return headers.subList(3, headers.size());
	}
	
	public void addPlayer(Player p){
		players.add(p);
	}
	
	public void deletePlayer(Player p){
		players.remove(p);
	}
	
	public void addSkillToAllPlayers(Skill s){
		for(Player p : players){
			p.addSkill(s);
		}
	}
	
	public void deleteSkillFromAllPlayers(String skillName){
		for(Player p : players){
			p.deleteSkill(skillName);
		}
	}
	
	public List<Player> getPlayers(){
		return players;
	}
	
	@Override
	public void saveToFile(String fileName) {
		
		PrintWriter writer = Utils.getPrintWriter(fileName);
		
		String headerLine = createHeaderLine();
		writer.println(headerLine);
		
		for (Player player : this.players) {
			
			String playerLine = createPlayerLine(player, this.getSkillList());
			writer.println(playerLine);
		}
		
		writer.close();
		
	}
	
	private String createHeaderLine() {
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
	public void saveToFile() {
		saveToFile(this.file.getAbsolutePath());		
	}

	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getHeaders() {
		return headers;
	}
	
	
}
