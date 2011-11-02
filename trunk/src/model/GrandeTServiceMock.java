package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.dto.Player;
import model.dto.PlayersDataBase;
import model.dto.ResultantTeam;
import model.dto.StatisticalInformation;
import model.dto.UserInputData;
import model.exceptions.FileBadFormedException;
import model.exceptions.InvalidModelException;

/**
 * 
 * @author sergiocormio
 * This Class is just for testing purpose.
 */
public class GrandeTServiceMock implements IGrandeTService {

	@Override
	public ResultantTeam armTeam(UserInputData userInputData) throws  FileBadFormedException, IOException, InvalidModelException{
		
		//return retrieveSavedTeam(null);
		IGrandeTService realService = new GrandeTService();
		return realService.armTeam(userInputData);
		
	}

	@Override
	public PlayersDataBase loadPlayersDataBase(String dataBaseFileName) throws FileBadFormedException, IOException{
		
		IGrandeTService realService = new GrandeTService();
		return realService.loadPlayersDataBase(dataBaseFileName);
	}

	@Override
	public ResultantTeam retrieveSavedTeam(String resultFileName) {
		ResultantTeam result = new ResultantTeam();
		for (Player p : getTeamMock()){
			result.addPlayer(p);
		}
		StatisticalInformation si = new StatisticalInformation();
		si.setFinalCost(4000000L);
		si.setTime(12);
		si.setTotalProfit(900);
		UserInputData usd = new UserInputData();
		usd.setBudget(6000000L);
		usd.setFormation("4-3-3");
		usd.setNumberOfPlayers(11);
		usd.setSkillToMax("Ataque");
		si.setUserInputData(usd);
		
		result.setStatisticalInformation(si);
		return result;
	}

	
	// USE: loadPlayersDataBase
	/*
	@Override
	public List<String> getSkillsFromPlayersDBFile(String dataBaseFileName)
			throws Exception {
		List<String> resultList = new ArrayList<String>();
		resultList.add("Ataque");
		resultList.add("Puntos de la fecha pasada");
		resultList.add("Defensa");
		return resultList;
	}
	*/
	
	private static List<Player> getTeamMock(){
		List<Player> team = new ArrayList<Player>();
		Player p = new Player("EL ARQUERO LOCO",Player.Position.ARQ,"El CLUB LOCO", 10000L);
		team.add(p);
		
		p = new Player("EL Defensa LOCO",Player.Position.DEF,"El CLUB LOCO", 10000L);
		team.add(p);
		
		p = new Player("EL Volante LOCO",Player.Position.VOL,"El CLUB LOCO", 10000L);
		team.add(p);
		
		p = new Player("EL Delantero LOCO",Player.Position.DEL,"El CLUB LOCO", 10000L);
		team.add(p);
		
		return team;
	}
	
	
	

}
