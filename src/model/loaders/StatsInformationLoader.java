package model.loaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import model.Utils;
import model.dto.StatisticalInformation;
import model.dto.UserInputData;
import model.dto.Player.Position;
import model.exceptions.FileBadFormedException;
import model.exceptions.InvalidPlayerPositionException;
import model.validators.FileValidator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

public class StatsInformationLoader {

	public static StatisticalInformation load(BufferedReader bufferedReader) throws FileBadFormedException, IOException {
		
		
		String line = bufferedReader.readLine();
		line = line.trim();
		if(line == null){
			throw new FileBadFormedException(Utils.EXP_FILE_BAD_FORMED_STAT);
		}
		
		String fields[] = line.split(Utils.FIELD_SEPARATOR_REGEXP);
		if(fields.length != 7){
			throw new FileBadFormedException(Utils.EXP_FILE_BAD_FORMED_STAT);
		}
		
		StatisticalInformation information = new StatisticalInformation();
		information.setTotalProfit(Integer.parseInt(fields[0]));
		information.setTime(Integer.parseInt(fields[1]));
		information.setFinalCost(new Long(fields[2]));
		
		information.setUserInputData(loadUserInputData(fields));
		
		return information;
	
	}

	private static UserInputData loadUserInputData(String[] fields) {
		UserInputData userInputData = new UserInputData();
			
		userInputData.setBudget(new Long(fields[3]));
		userInputData.setSkillToMax(fields[4]);
		userInputData.setFormation(fields[5]);
		userInputData.setNumberOfPlayers(Integer.parseInt(fields[6]));
				
		return userInputData;
		
	}
	

	
	
}
