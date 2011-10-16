package model.dto;

import java.util.HashMap;
import java.util.Map;

import model.Serializable;


public class Player implements Serializable{
	public enum Position{ARQ, DEF, VOL, DEL}

	private String name;
	private Position position;
	private long price;
	
	private Map<String, Skill> skills;
	
	public Player(String name, Position position, long price){
		skills = new HashMap<String, Skill>();
		this.name = name;
		this.position = position;
		this.price = price;
	}

	public void addSkill(Skill skill){
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
}
