package model;

import java.io.IOException;

import model.dto.PlayersDataBase;
import model.dto.ResultantTeam;
import model.dto.UserInputData;
import model.exceptions.FileBadFormedException;
import model.exceptions.InvalidModelException;

public interface IGrandeTService {
	public static String[] FORMATION_LIST = { "4-4-2","4-3-3","4-2-4","5-3-2"};
	public static String[] NUMBER_OF_PLAYERS_LIST = { "11","15","22"};
	public static int DEFAULT_BUDGET = 60000000;
	public static int DEFAULT_NUMBER_OF_PLAYERS = 11;
	public static String[] DEFAULT_HEADERS = {"NOMBRE","POSICION","EQUIPO","PRECIO"};
	public static Number DEFAULT_NUMBER_OF_PLAYERS_PER_CLUB = 3;
	
	
	/**
	* generates the optimized team according to the user selection
	*
	*/
	public ResultantTeam armTeam(UserInputData userInputData) throws FileBadFormedException, IOException, InvalidModelException ;
	
	
	/**
	* Retrieves a resultant opmitized team already saved
	* This can be used for both Resultados and Comparar use cases
	*
	*/
	public ResultantTeam retrieveSavedTeam(String resultFileName)throws FileBadFormedException, IOException;
	
	
	/**
	* Loads a file  (for ABM purpose)
	*
	*/
	public PlayersDataBase loadPlayersDataBase(String dataBaseFileName) throws FileBadFormedException, IOException;
	
			
	

}
