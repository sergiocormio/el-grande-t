package model;

import java.util.List;

public interface IGrandeTService {
	public static String[] FORMATION_LIST = { "4-4-2","4-3-3","4-2-4","5-3-2"};
	public static int DEFAULT_BUDGET = 60000000;
	public static int DEFAULT_NUMBER_OF_PLAYERS = 11;
	
	/**
	* generates the optimized team according to the user selection
	*
	*/
	public ResultantTeam armTeam(UserInputData userInputData) throws Exception;
	
	/**
	* Retrieves a resultant opmitized team already saved
	* This can be used for both Resultados and Comparar use cases
	*
	*/
	public ResultantTeam retrieveSavedTeam(String resultFileName);
	
	/**
	* Loads a file  (for ABM purpose)
	*
	*/
	public PlayersDataBase loadPlayersDataBase(String dataBaseFileName);
	
	/**
	 * Returns a list of skill names from a Players Database file name.
	 * 
	 */
	public List<String> getSkillsFromPlayersDBFile(String dataBaseFileName) throws Exception;
}
