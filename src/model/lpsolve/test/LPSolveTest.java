package model.lpsolve.test;

import lpsolve.*;

public class LPSolveTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try{
			// Create a problem with 400 variables and initially, 0 constraints
		    LpSolve solver = LpSolve.makeLp(0, 400);
		    
		    // set type of all variables as Binary
		    for (int i = 1; i <= 400; i++) {
		    	solver.setBinary(i, true);
			}
		  	          
		    // add budget constraint
		    String budgetConstraint = "";
		    for (int i = 0; i < 400; i++) {
				budgetConstraint += "1 ";
			}
		    solver.strAddConstraint(budgetConstraint, LpSolve.LE, 20);
		      
		
		    
		    // add line up constraint i.e: 4-4-2
		    String goalKeepers = "";
		    for (int i = 0; i < 400; i++) {
		    	if(i < 100)
		    		goalKeepers += "1 ";
		    	else
		    		goalKeepers += "0 ";
			}
		    solver.strAddConstraint(goalKeepers, LpSolve.LE, 1);
			   
		    String defenders = "";
		    for (int i = 0; i < 400; i++) {
		    	if(i >= 100 && i < 200)
		    		defenders += "1 ";
		    	else
		    		defenders += "0 ";
			}
		    solver.strAddConstraint(defenders, LpSolve.LE, 4);
		    
		    String midFielders = "";
		    for (int i = 0; i < 400; i++) {
		    	if(i >= 200 && i < 300)
		    		midFielders += "1 ";
		    	else
		    		midFielders += "0 ";
			}
		    solver.strAddConstraint(midFielders, LpSolve.LE, 4);
		     
		    String forwards = "";
		    for (int i = 0; i < 400; i++) {
		    	if(i >= 300)
		    		forwards += "1 ";
		    	else
		    		forwards += "0 ";
			}
		    solver.strAddConstraint(forwards, LpSolve.LE, 2);
		    

		    // set objective function (MAX some skill)
		    String objFunction = "";
		    for (int i = 0; i < 400; i++) {
		    	// aca toy hardcodeando el resultado indirectamente
		    	if(i == 0 || (i >= 100 && i < 104) || (i >= 200 && i < 204) || (i >= 300 && i < 304))
		    		objFunction += "10 ";
		    	else
		    		objFunction += "1 ";
			}
		    solver.strSetObjFn(objFunction);

	        //Set objective function to maximize.
			solver.setMaxim();

		    // solve the problem
		    solver.solve();

		    // print solution
		    System.out.println("Value of objective function: " + solver.getObjective());
		    double[] var = solver.getPtrVariables();
		    for (int i = 0; i < var.length; i++) {
		    	System.out.println("Value of var[" + i + "] = " + var[i]);
		    }

		    // delete the problem and free memory
		    solver.deleteLp();
	    }
		catch (LpSolveException e) {
			e.printStackTrace();
		}
	}

}
