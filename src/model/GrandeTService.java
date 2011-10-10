package model;

import java.io.IOException;
import java.util.List;

import model.exceptions.FileBadFormedException;
import model.exceptions.InvalidModelException;
import model.lpsolve.LPSolveAdapter;

public class GrandeTService implements IGrandeTService{

	@Override
	public ResultantTeam armTeam(UserInputData userInputData) throws FileBadFormedException, IOException, InvalidModelException {
		
		// loads and validate file format
		List<Player> playersDataBase = PlayersLoader.loadDataBase(userInputData.getDbFileName());
		
		ResultantTeam resultantTeam = LPSolveAdapter.runModel(userInputData, playersDataBase);
		return resultantTeam;
		
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
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
