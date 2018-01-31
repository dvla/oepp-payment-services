package uk.gov.dvla.payment.broker.core.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Mandatory field length validator for String, Long, Integer and Short.
 *
 */
public class MandatoryFieldLengthCheckValidator implements ConstraintValidator<MandatoryFieldLengthCheck, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MandatoryFieldLengthCheckValidator.class);

    private long                length;

    @Override
    public void initialize(final MandatoryFieldLengthCheck constraintAnnotation) {
        this.length = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext constraintContext) {
        LOGGER.debug("isValid [{}]", object);

        // fields with a MandatoryFieldLengthCheck will also have a NotNull annotation so
        // nulls will be handled there, to avoid returning an invalid field value return true here
        if (object == null) {
            return true;
        }

        if (object instanceof Long || object instanceof Integer || object instanceof Short) {
            return object.toString().length() <= this.length;
        } else if (object instanceof String) {
            return (((String) object).replace(" ", "").length() > 0) && (((String) object).length() <= this.length);
        } else {
            return false;
        }
    }
}