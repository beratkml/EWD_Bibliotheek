package validator;

import java.util.Set;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AuthorsConstraintValidation implements ConstraintValidator<ValidAuthors, Set<?>> {

	private int maxAuthors;

	@Override
	public void initialize(ValidAuthors constraintAnnotation) {
		this.maxAuthors = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(Set<?> authors, ConstraintValidatorContext context) {
		return authors.size() > 0 && authors.size() <= maxAuthors;
	}

}
