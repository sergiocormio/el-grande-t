package model.dto;

public class Skill {

	private String name;
	private int value;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		Skill otherSkill = (Skill)obj;
		return (otherSkill.name.compareToIgnoreCase(this.name)==0);
	}
}
