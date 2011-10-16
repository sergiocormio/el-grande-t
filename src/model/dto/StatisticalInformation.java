package model.dto;

import model.Serializable;

public class StatisticalInformation implements Serializable {
	
	private int totalProfit;
	private int time;
	private long finalCost;
	private UserInputData userInputData;
	
	//GETTERS AND SETTERS
	public int getTotalProfit() {
		return totalProfit;
	}
	public void setTotalProfit(int totalProfit) {
		this.totalProfit = totalProfit;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public long getFinalCost() {
		return finalCost;
	}
	public void setFinalCost(long finalCost) {
		this.finalCost = finalCost;
	}
	public UserInputData getUserInputData() {
		return userInputData;
	}
	public void setUserInputData(UserInputData userInputData) {
		this.userInputData = userInputData;
	}
	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
