package org.cevh;

public class CevhException extends RuntimeException {

	private static final long serialVersionUID = -6081477768026515076L;

	public CevhException(String message) {
		super(message);
	}

	public CevhException(CharSequence message) {
		super(message.toString());
	}

	public CevhException(String message, Throwable cause) {
		super(message, cause);
	}

	public CevhException(CharSequence message, Throwable cause) {
		super(message.toString(), cause);
	}
}
