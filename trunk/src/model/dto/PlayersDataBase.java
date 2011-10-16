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
	private List<String> skills;
	
	
	public PlayersDataBase(){
		players = new ArrayList<Player>();
	}
	
	public PlayersDataBase(List<String> skills, List<Player> players, File file){
		this.skills = skills;
		this.players = players;
		this.file = file;
	}
	
	public List<String> getSkillList(){
		List<String> skills = new ArrayList<String>();
		Set<String> skillsSet = new HashSet<String>();
		
		for(Player p : players){
			skillsSet.addAll(p.getSkills().keySet());
		}
		
		for (String s : skillsSet){
			skills.add(s);
		}
		return skills;
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

	public List<String> getSkills() {
		return skills;
	}
	
	
}
