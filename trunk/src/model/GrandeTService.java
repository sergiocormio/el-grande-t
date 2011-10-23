package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;

import model.dto.Player;
import model.dto.PlayersDataBase;
import model.dto.ResultantTeam;
import model.dto.StatisticalInformation;
import model.dto.UserInputData;
import model.exceptions.FileBadFormedException;
import model.exceptions.InvalidModelException;
import model.loaders.PlayersLoader;
import model.loaders.StatsInformationLoader;
import model.lpsolve.LPSolveAdapter;

public class GrandeTService implements IGrandeTService{

	@Override
	public ResultantTeam armTeam(UserInputData userInputData) throws FileBadFormedException, IOException, InvalidModelException {
		
		// loads and validate file format
		List<Player> players = userInputData.getPlayersDataBase().getPlayers();
		
		ResultantTeam resultantTeam = LPSolveAdapter.runModel(userInputData, players);
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

	
	
		
	
}
