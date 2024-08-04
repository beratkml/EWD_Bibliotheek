package exceptions;

public class DuplicateBookException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DuplicateBookException(Long id) {
		super(String.format("Duplicate book %s", id));
	}
}
