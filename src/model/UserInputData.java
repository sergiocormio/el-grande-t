package model;

public class UserInputData implements Serializable {
	private String dbFileName;
	private long budget;
	private String skillToMax;
	private String formation;
	private int numberOfPlayers;
	
	
	public String getDbFileName() {
		return dbFileName;
	}
	public void setDbFileName(String dbFileName) {
		this.dbFileName = dbFileName;
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
	
	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
