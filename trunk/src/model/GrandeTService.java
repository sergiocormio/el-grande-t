package model;

import java.io.BufferedReader;
import java.io.File;
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
	public PlayersDataBase loadPlayersDataBase(String dataBaseFileName) throws FileBadFormedException, IOException {
		
		BufferedReader bufferedReader = Utils.getBufferedReader(dataBaseFileName);
		List<String> headers = PlayersLoader.loadHeaders(bufferedReader);
		List<Player> playersDataBase = PlayersLoader.loadData(headers, bufferedReader);
		
		return new PlayersDataBase(headers, playersDataBase, new File(dataBaseFileName));
		
	}

	@Override
	public ResultantTeam retrieveSavedTeam(String resultFileName) throws FileBadFormedException, IOException {
		
		BufferedReader bufferedReader = Utils.getBufferedReader(resultFileName);
		StatisticalInformation information = StatsInformationLoader.load(bufferedReader);
		List<String> headers = PlayersLoader.loadHeaders(bufferedReader);
		List<Player> players = PlayersLoader.loadData(headers, bufferedReader);
		
		return new ResultantTeam(new File(resultFileName), players, information);
	}

	@Override
	// USE: loadPlayersDataBase
	public List<String> getSkillsFromPlayersDBFile(String dataBaseFileName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public void saveResultantTeam(ResultantTeam resultantTeam, String fileName){
		
	}
	
}
