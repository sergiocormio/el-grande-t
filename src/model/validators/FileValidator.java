package model.validators;

import model.IGrandeTService;
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
		
		if(fields.length < IGrandeTService.DEFAULT_HEADERS.length){
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
		
		// ignore the first, second and thirst field (name, position and team)
		for (int i = 3; i < fields.length; i++) {
			try{
				Integer.parseInt(fields[i]);	
			}
			catch(NumberFormatException e){
				throw e;
			}
		}		
	}


	public static void validateColumnNames(String[] fields) throws FileBadFormedException {
		
		if(!fields[0].equals(IGrandeTService.DEFAULT_HEADERS[0]) ||
			!fields[1].equals(IGrandeTService.DEFAULT_HEADERS[1]) ||
			!fields[2].equals(IGrandeTService.DEFAULT_HEADERS[2]) ||
			!fields[3].equals(IGrandeTService.DEFAULT_HEADERS[3]))
			
			 throw new FileBadFormedException(Utils.EXP_FILE_BAD_FORMED_MANDATORY);
	}	
	
}
