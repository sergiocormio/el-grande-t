package model;

public interface IGrandeTService {
	/**
	* generates the optimized team according to the user selection
	*
	*/
	public ResultantTeam armTeam(UserInputData userInputData);
	
	/**
	* Retrieves a resultant opmitized team already saved
	* This can be used for both “Resultados” and “Comparar” use cases
	*
	*/
	public ResultantTeam retrieveSavedTeam(String resultFileName);
	
	/**
	* Loads a file  (for ABM purpose)
	*
	*/
	public PlayersDataBase loadPlayersDataBase(String dataBaseFileName);
}
