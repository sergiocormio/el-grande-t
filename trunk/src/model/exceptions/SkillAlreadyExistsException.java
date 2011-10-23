package model.exceptions;

import model.Utils;

public class SkillAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return Utils.EXP_SKILL_ALREADY_EXISTS;
	}
}
