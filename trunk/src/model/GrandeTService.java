package model;

import java.util.List;

import model.lpsolve.LPSolveAdapter;

public class GrandeTService implements IGrandeTService{

	@Override
	public ResultantTeam armTeam(UserInputData userInputData) throws Exception {
		
		try{
			// loads and validate file format
			List<Player> playersDataBase = PlayersLoader.loadDataBase(userInputData.getDbFileName());
			
			ResultantTeam resultantTeam = LPSolveAdapter.runModel(userInputData, playersDataBase);
			return resultantTeam;
		}
		catch (Exception e) {
			throw e;
		}
		
		
		
		
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
	
	
	
}
