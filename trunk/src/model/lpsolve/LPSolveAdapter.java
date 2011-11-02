package model.lpsolve;

import java.util.List;

import lpsolve.LpSolve;
import lpsolve.LpSolveException;
import model.dto.Player;
import model.dto.ResultantTeam;
import model.dto.Skill;
import model.dto.StatisticalInformation;
import model.dto.UserInputData;
import model.dto.Player.Position;
import model.exceptions.InvalidModelException;

public class LPSolveAdapter{

	public static ResultantTeam runModel(UserInputData userInputData, List<Player> playersDataBase) throws InvalidModelException {
		
		try {
			// Create a problem with N variables and initially, 0 constraints
			LpSolve solver = LpSolve.makeLp(0, playersDataBase.size());
			
			// set type of all variables as Binary
		    for (int i = 1; i <= playersDataBase.size(); i++) {
		    	solver.setBinary(i, true);
			}
		    
		    // add budget constraint
		    String budgetConstraint = "";
		    for (Player player : playersDataBase) {
		    	budgetConstraint +=  String.valueOf(player.getPrice()) + " ";
			}
		    solver.strAddConstraint(budgetConstraint, LpSolve.LE, userInputData.getBudget());
		   
		    // add formation constraints
		    solver = addFormationConstraint(solver, playersDataBase, userInputData);
		    		   
		    // add clubs constraints
		    solver = addClubsConstraint(solver, playersDataBase, userInputData);
		    
		    // set objective function (MAX some skill)
		    String objFunction = "";
		    String skillToMax = userInputData.getSkillToMax();
		    for (Player player : playersDataBase) {
				Skill skill = player.getSkill(skillToMax);
		    	objFunction += String.valueOf(skill.getValue()) + " ";
			}
		    solver.strSetObjFn(objFunction);
		    
		    //Set objective function to maximize.
			solver.setMaxim();

		    // solve the problem
		    solver.solve();

		    ResultantTeam resultantTeam = buildResult(solver, userInputData, playersDataBase);
		    		   
		    return resultantTeam;
				    
		} catch (LpSolveException e) {
			// TODO Auto-generated catch block
			throw new InvalidModelException();
		}
	   
	}

	
	private static LpSolve addClubsConstraint(LpSolve solver, List<Player> playersDataBase, UserInputData userInputData) throws LpSolveException {

		for (String club : userInputData.getPlayersDataBase().getClubs()) {
			
			solver = addClubConstraint(solver, playersDataBase, club, userInputData);
		}
		
		return solver;
	}


	private static LpSolve addClubConstraint(LpSolve solver, List<Player> playersDataBase, String club, UserInputData userInputData) throws LpSolveException {

		 String playersLine = "";
		 for (Player player : playersDataBase) {
			if(player.getClub().equals(club)){
				playersLine += "1 ";				}
			else{
				playersLine += "0 ";
			}
		
		 }
		 String srtNumberOfPlayersPerClub = String.valueOf(userInputData.getNumberOfPlayersPerClub());
		 Double d = new Double(srtNumberOfPlayersPerClub);
		 
		 solver.strAddConstraint(playersLine, LpSolve.LE, d.doubleValue());
		
		 return solver;
	}


	private static LpSolve addFormationConstraint(LpSolve solver, List<Player> playersDataBase, UserInputData userInputData) throws NumberFormatException, LpSolveException {
		
		String formation = userInputData.getFormation();
		int numberOfPlayers = userInputData.getNumberOfPlayers();
		
		
		double maxGoalKeepers = 1;
		// if the number of players is 15 or 22 it is the same to add one player than to duplicate that quantity
		if(numberOfPlayers != 11)
			maxGoalKeepers = 2;
		// add line-up constraint - goalkeppers
		solver = addFormationConstraint(solver, playersDataBase, Position.ARQ, maxGoalKeepers);
		
		String strMaxDefenders = formation.split("-")[0];
		double maxDefenders = getMaxPerPosition(strMaxDefenders, numberOfPlayers);
		
		String strMaxMidFielders = formation.split("-")[1];
		double maxMidFielders = getMaxPerPosition(strMaxMidFielders, numberOfPlayers);
		
		String strMaxForwards = formation.split("-")[2];
		double maxForwards = getMaxPerPosition(strMaxForwards, numberOfPlayers);
		
		// add line-up constraint - defenders
		solver = addFormationConstraint(solver, playersDataBase, Position.DEF, maxDefenders);
		
		// add line-up constraint - midfielders
		solver = addFormationConstraint(solver, playersDataBase, Position.VOL, maxMidFielders);
		
		// add line-up constraint - forwards
		solver = addFormationConstraint(solver, playersDataBase, Position.DEL, maxForwards);
		
		return solver;
	}

	
	private static double getMaxPerPosition(String strMaxPlayersPerPosition, int numberOfPlayers) {
		
		double maxPlayersPerPosition = Double.valueOf(strMaxPlayersPerPosition);
		
		if(numberOfPlayers == 15)
			maxPlayersPerPosition ++;
		else if(numberOfPlayers == 22)
			maxPlayersPerPosition *= 2;
		
		return maxPlayersPerPosition;
	}


	private static LpSolve addFormationConstraint(LpSolve solver, List<Player> playersDataBase, Position position, double maxPlayersByPosition) throws LpSolveException {

		 String playersLine = "";
		 for (Player player : playersDataBase) {
			if(player.getPosition().equals(position)){
				playersLine += "1 ";				}
			else{
				playersLine += "0 ";
			}
		}
		solver.strAddConstraint(playersLine, LpSolve.LE, maxPlayersByPosition);
		return solver;
	}
	

	private static ResultantTeam buildResult(LpSolve solver, UserInputData userInputData, List<Player> playersDataBase) throws LpSolveException {
		
		ResultantTeam resultantTeam = new ResultantTeam();
		long finalCost = 0L;
		
		// print solution
	    System.out.println("Value of objective function: " + solver.getObjective());
	    double[] var = solver.getPtrVariables();
	    for (int i = 0; i < var.length; i++) {
	    	System.out.println("Value of var[" + i + "] = " + var[i]);
	    	if(var[i] == 1){
	    		Player player = playersDataBase.get(i);
	    		finalCost += player.getPrice(); 
	    		resultantTeam.addPlayer(player);
	    	}
	    }
	    
		StatisticalInformation statisticalInformation = createStatiticalInformation(solver, userInputData, finalCost);
		resultantTeam.setStatisticalInformation(statisticalInformation);
				
	    // delete the problem and free memory
	    solver.deleteLp();
	    
		return resultantTeam;
	}


	private static StatisticalInformation createStatiticalInformation(LpSolve solver, UserInputData userInputData, long finalCost)
			throws LpSolveException {
		
		StatisticalInformation statisticalInformation = new StatisticalInformation();
		
		statisticalInformation.setUserInputData(userInputData);
		
		String totalProfit = String.valueOf(solver.getObjective());
		statisticalInformation.setTotalProfit(Integer.valueOf(totalProfit.split("\\.")[0]).intValue());
		
		statisticalInformation.setFinalCost(finalCost);
		
		double time = solver.timeElapsed();
		time *= 1000;
		String elapsedTime = String.valueOf(time);
		statisticalInformation.setTime(Integer.valueOf(elapsedTime.split("\\.")[0]).intValue());
		
		return statisticalInformation;
	}

	
	
}
