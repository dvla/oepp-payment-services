package uk.gov.dvla.payment.broker.core.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * Validator for MaskedPanCheck.
 */
public class MaskedPanCheckValidator implements ConstraintValidator<MaskedPanCheck, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaskedPanCheckValidator.class);

    @Override
    public void initialize(final MaskedPanCheck constraintAnnotation) {
    }

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext constraintContext) {
        LOGGER.debug("isValid [{}]", object);

        // If it is null, we do not care, we are not going to use the value
        if (object == null) {
            return true;
        }

        if (!(object instanceof String)) {
            return false;
        }

        if (((String) object).trim().length() == 0) {
            return true;
        }

        int positionOfStar = ((String) object).indexOf('*');
        if (positionOfStar != 6) {
            return false;
        }

        if (((String) object).length() != 11) {
            return false;
        }

        for (int i = 0; i < 6; i++) {
            if (!Character.isDigit((((String) object).charAt(i)))) {
                return false;
            }
        }
        for (int i = 7; i < ((String) object).length(); i++) {
            if (!Character.isDigit((((String) object).charAt(i)))) {
                return false;
            }
        }

        return true;
    }

    private int getNumberOfDecimalPlaces(final BigDecimal bigDecimal) {
        String string = bigDecimal.toPlainString();
        int index = string.indexOf(".");
        return index < 0 ? 0 : string.length() - index - 1;
    }
}