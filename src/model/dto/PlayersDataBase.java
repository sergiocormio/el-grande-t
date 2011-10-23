package model.dto;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Fileable;
import model.Serializable;

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
		
	}
	
	public void deletePlayer(Player p){
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveToFile() {
		// TODO Auto-generated method stub
		
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
