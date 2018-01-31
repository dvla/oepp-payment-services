package uk.gov.dvla.payment.broker.core.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Optional field length validator for String, Long, Integer and Short.
 *
 */
public class OptionalFieldLengthCheckValidator implements ConstraintValidator<OptionalFieldLengthCheck, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OptionalFieldLengthCheckValidator.class);

    private long                length;

    @Override
    public void initialize(final OptionalFieldLengthCheck constraintAnnotation) {
        this.length = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext constraintContext) {
        LOGGER.debug("isValid [{}]", object);

        if (object == null) {
            return true;
        }

        if (object instanceof Long || object instanceof Integer || object instanceof Short) {
            return object.toString().length() <= this.length;
        } else if (object instanceof String) {
            return ((String) object).length() <= this.length;
        } else {
            return false;
        }
    }
}