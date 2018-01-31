package uk.gov.dvla.payment.broker.core.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * Validator for PaymentAmountCheck.
 */
public class PaymentAmountCheckValidator implements ConstraintValidator<PaymentAmountCheck, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentAmountCheckValidator.class);

    private static final BigDecimal MAX = BigDecimal.valueOf(999999.99);

    private static final BigDecimal MIN = BigDecimal.valueOf(00.01);

    @Override
    public void initialize(final PaymentAmountCheck constraintAnnotation) {
    }

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext constraintContext) {
        LOGGER.debug("isValid [{}]", object);

        // fields with a PaymentAmountCheck will also have a NotNull annotation so
        // nulls will be handled there, to avoid returning an invalid field value return true here
        if (object == null) {
            return true;
        }

        if (object instanceof BigDecimal) {
            return (getNumberOfDecimalPlaces(((BigDecimal) object)) <= 2) &&
                    ((BigDecimal) object).compareTo(MIN) >= 0 &&
                    ((BigDecimal) object).compareTo(MAX) <= 0;
        } else {
            return false;
        }
    }


    private int getNumberOfDecimalPlaces(BigDecimal bigDecimal) {
        String string = bigDecimal.toPlainString();
        int index = string.indexOf(".");
        return index < 0 ? 0 : string.length() - index - 1;
    }
}