package model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author sergiocormio
 * This Class is just for testing purpose.
 */
public class GrandeTServiceMock implements IGrandeTService {

	@Override
	public ResultantTeam armTeam(UserInputData userInputData) throws Exception {
		
		return null;
		
	}

	@Override
	public PlayersDataBase loadPlayersDataBase(String dataBaseFileName) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultantTeam retrieveSavedTeam(String resultFileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getSkillsFromPlayersDBFile(String dataBaseFileName)
			throws Exception {
		List<String> resultList = new ArrayList<String>();
		resultList.add("Ataque");
		resultList.add("Puntos de la fecha pasada");
		resultList.add("Defensa");
		return resultList;
	}
	
	

}
