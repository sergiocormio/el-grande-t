package model.exceptions;

import model.Utils;



public class FileBadFormedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return Utils.MSG_FILE_BAD_FORMED_EXCEPTION;
	}


}
