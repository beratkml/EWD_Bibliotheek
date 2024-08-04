package exceptions;

public class BoekNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BoekNotFoundException(Long id) {
		super(String.format("Could not find book %s", id));
	}
}
