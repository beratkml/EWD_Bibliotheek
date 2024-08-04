package validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ISBNConstraintValidation implements ConstraintValidator<ValidISBN, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value.length() != 13) {
			return false;
		}

		int sum = 0;
		for (int i = 0; i < value.length() - 1; i++) {
			int digit = Character.getNumericValue(value.charAt(i));
			sum += (i % 2 == 0) ? digit * 1 : digit * 3;
		}

		int checkDigit = (10 - (sum % 10)) % 10;
		int lastDigit = Character.getNumericValue(value.charAt(value.length() - 1));

		return checkDigit == lastDigit;
	}

}
