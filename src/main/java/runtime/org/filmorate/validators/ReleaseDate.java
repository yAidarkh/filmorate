package runtime.org.filmorate.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ReleaseDateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReleaseDate {
    String message() default "Дата релиза не может быть раньше 28 декабря 1895 года.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
