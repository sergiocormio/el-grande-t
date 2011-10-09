package model.lpsolve;

import java.util.List;

import lpsolve.LpSolve;
import lpsolve.LpSolveException;
import model.Player;
import model.ResultantTeam;
import model.Skill;
import model.StatisticalInformation;
import model.UserInputData;
import model.Player.Position;

public class LPSolveAdapter{

	public static ResultantTeam runModel(UserInputData userInputData, List<Player> playersDataBase) {
		
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
		    solver = addFormationConstraint(solver, playersDataBase, userInputData.getFormation());
		    		    
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
			e.printStackTrace();
		}
	    
	   
		// TODO Auto-generated method stub
		return null;
	}

	
	private static LpSolve addFormationConstraint(LpSolve solver, List<Player> playersDataBase, String formation) throws NumberFormatException, LpSolveException {
		
		// add line-up constraint - goalkeppers
		solver = addFormationConstraint(solver, playersDataBase, Position.ARQ, 1);
		
		String maxDefenders = formation.split("-")[0];
		String maxMidFielders = formation.split("-")[1];
		String maxForwards = formation.split("-")[2];
		
		// add line-up constraint - defenders
		solver = addFormationConstraint(solver, playersDataBase, Position.DEF, Double.valueOf(maxDefenders));
		
		// add line-up constraint - midfielders
		solver = addFormationConstraint(solver, playersDataBase, Position.VOL, Double.valueOf(maxMidFielders));
		
		// add line-up constraint - forwards
		solver = addFormationConstraint(solver, playersDataBase, Position.DEL, Double.valueOf(maxForwards));
		
		return solver;
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
				
		//resultantTeam.saveToFile();

	    // delete the problem and free memory
	    solver.deleteLp();
	    
		return resultantTeam;
	}


	private static StatisticalInformation createStatiticalInformation(LpSolve solver, UserInputData userInputData, long finalCost)
			throws LpSolveException {
		
		StatisticalInformation statisticalInformation = new StatisticalInformation();
		
		statisticalInformation.setUserInputData(userInputData);
		
		String totalProfit = String.valueOf(solver.getObjective());
		statisticalInformation.setTotalProfit(Integer.valueOf(totalProfit));
		
		statisticalInformation.setFinalCost(finalCost);
		
		statisticalInformation.setTime(0);
		
		return statisticalInformation;
	}

	
	
}
