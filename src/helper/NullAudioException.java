package helper;

public class NullAudioException extends Exception{
	public NullAudioException(String string) {
		super(string + " is not found in audio.");
	}
	
}
