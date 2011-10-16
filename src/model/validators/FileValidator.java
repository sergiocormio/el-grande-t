package model.validators;

import model.Utils;
import model.dto.Player.Position;
import model.exceptions.FileBadFormedException;
import model.exceptions.InvalidPlayerPositionException;

public class FileValidator {

	/**
	 * validates if the line has the three mandatory fields (only the quantity)
	 * 
	 * @param fields
	 * @throws FileBadFormedException
	 */
	public static void validateMandatoryColumns(String[] fields) throws FileBadFormedException {
		
		if(fields.length < 3){
			throw new FileBadFormedException(Utils.EXP_FILE_BAD_FORMED_MANDATORY);
		}
	}
	
	
	/**
	 * validates player position
	 * 
	 * @param fields
	 * @throws InvalidPlayerPositionException
	 */
	public static void validatePlayerPosition(String[] fields) throws InvalidPlayerPositionException {
		
		String position = fields[1];
		
		if(!position.equalsIgnoreCase(Position.ARQ.toString()) && 
		   !position.equalsIgnoreCase(Position.DEF.toString()) &&
		   !position.equalsIgnoreCase(Position.VOL.toString()) &&
		   !position.equalsIgnoreCase(Position.DEL.toString())){
			
			throw new InvalidPlayerPositionException();
		}
	}
	
	/**
	 * validates integer data type from field 2 to N
	 * 
	 * @param fields
	 * @throws NumberFormatException
	 */
	public static void validateIntegerFields(String[] fields) throws NumberFormatException {
		
		// ignore the first and the second field (name and position)
		for (int i = 2; i < fields.length; i++) {
			try{
				Integer.parseInt(fields[i]);	
			}
			catch(NumberFormatException e){
				throw e;
			}
		}		
	}	
	
}
