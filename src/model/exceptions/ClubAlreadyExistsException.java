package model.exceptions;

import model.Utils;

public class ClubAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return Utils.EXP_CLUB_ALREADY_EXISTS;
	}
}
