package uk.gov.dvla.payment.broker.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = ShouldContainOneStringValidator.class)
@Documented
public @interface ShouldContainOneString {

    String message() default "Invalid input data";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] acceptedValues();

}