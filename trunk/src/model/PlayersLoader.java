package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import model.Player.Position;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

public class PlayersLoader {

	public static List<Player> loadDataBase(String dbFileName) throws Exception {
		
		try {
			BufferedReader bufferedReader = Utils.getBufferedReader(dbFileName);
			
			processHeader(bufferedReader);
			
			List<Player> playersDataBase = processData(bufferedReader);
			
			/**
			 * no hace falta que este ordenada porque puedo generar las constraints validando al posicion de cada player
			 * 
			 * VER LPSolveAdapter
			 */
			//List<Player> clasifiedPlayersDataBase = clasifyPlayer(playersDataBase); 
			
			return playersDataBase;
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	
	


	private static void processHeader(BufferedReader bufferedReader) throws IOException {
		
		String line = bufferedReader.readLine();
		
		if(line != null){
			line = line.trim();
			validateHeader(line);
		}
		
	}
	

	private static void validateHeader(String line) {
		// TODO apply header validations!!!
		
	}


	private static List<Player> processData(BufferedReader bufferedReader) {
		String line = "";
		List<Player> playersDataBase = new ArrayList<Player>();
		try	{
			line = bufferedReader.readLine();
			while (line != null) {
				line = line.trim();
				
				Player player = createPlayer(line);
				if(player != null){
					playersDataBase.add( createPlayer(line));
				}
				
				line = bufferedReader.readLine();
			}
							
		} catch (IOException e) {
			e.printStackTrace();
		}

		return playersDataBase;
	}




	private static Player createPlayer(String line) {
		Player player = new Player();
		
		String[] fields = line.split("[||]");
		
		// TODO some validations ??
		
		return player;
	}
	
	
	
	@SuppressWarnings("unchecked")
	private static List<Player> clasifyPlayer(List<Player> playersDataBase) {
		
		List<Player> clasifiedPlayers = new ArrayList<Player>();
						
		Collection<Player> goalKeepers =  filterDataBaseByPosition(playersDataBase, Position.ARQ);
		Collection<Player> defenders =  filterDataBaseByPosition(playersDataBase, Position.DEF);
		Collection<Player> midFielders =  filterDataBaseByPosition(playersDataBase, Position.VOL);
		Collection<Player> forwards =  filterDataBaseByPosition(playersDataBase, Position.DEL);
		
		clasifiedPlayers.addAll(goalKeepers);
		clasifiedPlayers.addAll(defenders);
		clasifiedPlayers.addAll(midFielders);
		clasifiedPlayers.addAll(forwards);
		
		return clasifiedPlayers;
	}


	@SuppressWarnings("unchecked")
	private static Collection<Player> filterDataBaseByPosition(List<Player> playersDataBase, final Position position) {
		
		Collection<Player> filteredCollection = CollectionUtils.predicatedCollection(playersDataBase, new Predicate(){
					public boolean evaluate(Object o) {  
						try {  
							Player player = (Player) o;  
							return player.getPosition().equals(position);
						} catch (NumberFormatException e) {  
							return false;  
						}  
					}  
		      });  
	
		return filteredCollection;
	}
	
}
