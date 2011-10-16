package model.exceptions;




public class FileBadFormedException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message = null;
	
	public FileBadFormedException(String message){
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}


}
