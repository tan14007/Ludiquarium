package helper;

public class NullPictureException extends Exception{
	public NullPictureException(String string) {
		super(string + " is not found in images.");
	}
	
}
