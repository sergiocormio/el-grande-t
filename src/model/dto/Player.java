package model.dto;

import java.util.HashMap;
import java.util.Map;

import model.Serializable;
import model.exceptions.SkillAlreadyExistsException;


public class Player implements Serializable{
	public enum Position{ARQ, DEF, VOL, DEL}

	private String name;
	private Position position;
	private String club;
	private long price;
	
	private Map<String, Skill> skills;
	
	public Player(String name, Position position,String club, long price){
		skills = new HashMap<String, Skill>();
		this.name = name;
		this.position = position;
		this.club = club;
		this.price = price;
	}

	public void addSkill(Skill skill) throws SkillAlreadyExistsException{
		if(skills.containsKey(skill.getName()))
			throw new SkillAlreadyExistsException();
		
		skills.put(skill.getName(), skill);
	}
	
	public void deleteSkill(String skillName){
		skills.remove(skillName);
	}
	
	public Skill getSkill(String skillName){
		return skills.get(skillName);
	}
	
	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}

	public synchronized String getName() {
		return name;
	}

	public synchronized void setName(String name) {
		this.name = name;
	}

	public synchronized long getPrice() {
		return price;
	}

	public synchronized void setPrice(long price) {
		this.price = price;
	}

	public synchronized Position getPosition() {
		return position;
	}

	public synchronized void setPosition(Position position) {
		this.position = position;
	}

	public synchronized Map<String, Skill> getSkills() {
		return skills;
	}

	
	
	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

	@Override
	public boolean equals(Object obj) {
		
		Player anotherPlayer = (Player)obj;
		
		return this.name.equalsIgnoreCase(anotherPlayer.getName());
		
	}
	

	public void updateSkill(String oldSkillName, String newSkillName) {
		if(skills.containsKey(oldSkillName)){
			Skill skill = skills.get(oldSkillName);
			skill.setName(newSkillName);
			skills.put(newSkillName, skill);
			skills.remove(oldSkillName);
		}
	}
	
	
}
