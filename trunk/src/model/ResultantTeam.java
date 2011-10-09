package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ResultantTeam implements Fileable, Serializable{

	private File file;
	private List<Player> players;
	private StatisticalInformation statisticalInformation;
	
	public ResultantTeam(){
		players = new ArrayList<Player>();
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
	
	public void addPlayer(Player player){
		this.players.add(player);
	}

}
