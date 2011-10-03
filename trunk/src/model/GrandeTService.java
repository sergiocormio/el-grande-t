package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class GrandeTService implements IGrandeTService{

	@Override
	public ResultantTeam armTeam(UserInputData userInputData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayersDataBase loadPlayersDataBase(String dataBaseFileName) {
		// TODO Auto-generated method stub
		loadPlayerDataBaseFile(dataBaseFileName);
		return null;
	}

	@Override
	public ResultantTeam retrieveSavedTeam(String resultFileName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//TODO: Look this method and re-implement it.
	private static void loadPlayerDataBaseFile(String fileName) {

		BufferedReader document = Utils.getBufferedReader(fileName);
		if (document == null) {
			return;
		}

		String line = "";
		try {
			line = document.readLine();
			while (line != null) {
				line = line.trim();
				//TODO: doSomething(line) 
				line = document.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
