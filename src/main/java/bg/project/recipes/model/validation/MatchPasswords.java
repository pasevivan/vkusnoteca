package bg.project.recipes.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = MatchPasswordsValidator.class)
public @interface MatchPasswords {
    String first();

    String second();

    String message() default "Паролите не съвпадат";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
