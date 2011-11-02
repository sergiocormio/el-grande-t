package model.dto;

import model.Serializable;

public class UserInputData implements Serializable {
	private PlayersDataBase playersDataBase;
	private long budget;
	private String skillToMax;
	private String formation;
	private int numberOfPlayers;
	private int numberOfPlayersPerClub;
	
	
	public PlayersDataBase getPlayersDataBase() {
		return playersDataBase;
	}
	
	public void setPlayersDataBase(PlayersDataBase playersDataBase) {
		this.playersDataBase = playersDataBase;
	}
	public long getBudget() {
		return budget;
	}
	public void setBudget(long budget) {
		this.budget = budget;
	}
	public String getSkillToMax() {
		return skillToMax;
	}
	public void setSkillToMax(String skillToMax) {
		this.skillToMax = skillToMax;
	}
	public String getFormation() {
		return formation;
	}
	public void setFormation(String formation) {
		this.formation = formation;
	}
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}
	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}
	
		
	public int getNumberOfPlayersPerClub() {
		return numberOfPlayersPerClub;
	}

	public void setNumberOfPlayersPerClub(int numberOfPlayersPerClub) {
		this.numberOfPlayersPerClub = numberOfPlayersPerClub;
	}

	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
