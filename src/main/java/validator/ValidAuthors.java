package validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = AuthorsConstraintValidation.class)
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface ValidAuthors {
	String message() default "{boek.auteurs.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	int value() default 3;
}
