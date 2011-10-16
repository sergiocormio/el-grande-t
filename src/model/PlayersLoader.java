package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import model.Player.Position;
import model.exceptions.FileBadFormedException;
import model.exceptions.InvalidPlayerPositionException;
import model.validators.FileValidator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

public class PlayersLoader {

	public static List<Player> loadDataBase(String dbFileName) throws FileBadFormedException, IOException {
		
		BufferedReader bufferedReader = Utils.getBufferedReader(dbFileName);
			
		String[] headerFields = processHeader(bufferedReader);
			
		List<Player> playersDataBase = processData(bufferedReader, headerFields);
			
		/**
		 * no hace falta que este ordenada porque puedo generar las constraints validando al posicion de cada player
		 * 
		 * VER LPSolveAdapter
		 */
		//List<Player> clasifiedPlayersDataBase = clasifyPlayer(playersDataBase); 
		
		return playersDataBase;
	}
	
	
	public static List<String> loadHeaders(BufferedReader bufferedReader) throws FileBadFormedException, IOException {
		
		String[] arrHeaderFields = processHeader(bufferedReader);
		
		List<String> headerFields = new ArrayList<String>();
		for (String header : arrHeaderFields) {
			headerFields.add(header);
		}
		
		return headerFields;
	}
	

	public static List<Player> loadData(List<String> headers, BufferedReader bufferedReader) throws IOException {
		
		String[] arrHeaderFields = headers.toArray(new String[0]);
		
		List<Player> playersDataBase = processData(bufferedReader, arrHeaderFields);
		
		return playersDataBase;
	}
	
	

	private static String[] processHeader(BufferedReader bufferedReader) throws IOException, FileBadFormedException {
		
		String line = bufferedReader.readLine();
		String[] headerFields = null;
		
		if(line != null){
			line = line.trim();
			headerFields = validateHeader(line);
		}
		return headerFields;
	}
	

	private static String[] validateHeader(String line) throws FileBadFormedException {
		
		String[] fields = line.split(Utils.FIELD_SEPARATOR_REGEXP);
		
		FileValidator.validateMandatoryColumns(fields);
		
		/**
		 * TODO add more header validators, creating new private methods,
		 * @see FileValidators
		 */
		
		return fields;
	}





	private static List<Player> processData(BufferedReader bufferedReader, String[] headerFields) throws IOException {
		String line = "";
		List<Player> playersDataBase = new ArrayList<Player>();
		
		line = bufferedReader.readLine();
		while (line != null) {
			line = line.trim();
			
			Player player = createPlayer(line, headerFields);
			if(player != null){
				playersDataBase.add( player );
			}
			
			line = bufferedReader.readLine();
		}
		
		return playersDataBase;
	}




	private static Player createPlayer(String line, String[] headerFields) {
				
		String[] fields = line.split(Utils.FIELD_SEPARATOR_REGEXP);
		
		try {
			FileValidator.validateMandatoryColumns(fields);
			FileValidator.validatePlayerPosition(fields);
			FileValidator.validateIntegerFields(fields);
			
			// TODO add more validator if necesary
			
			return createPlayer(fields, headerFields);
			
		} catch (FileBadFormedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (InvalidPlayerPositionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		 catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
				
	}
	
	
	
	private static Player createPlayer(String[] fields, String[] headerFields) {
		
		Player player = new Player(fields[0], Position.valueOf(fields[1]), 
				new Long(fields[2]).longValue());
				
		//adding skills
		for (int i = 3; i < fields.length; i++) {
			Skill skill = new Skill();
			skill.setName(headerFields[i]);
			skill.setValue(Integer.parseInt(fields[i]));
			
			player.addSkill(skill);
		}
		
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
