package model.exceptions;

import model.Utils;

public class InvalidModelException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return Utils.EXP_INVALID_MODEL;
	}
	
}
