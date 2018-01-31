package uk.gov.dvla.payment.broker.core.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ShouldContainOneStringValidator implements ConstraintValidator<ShouldContainOneString, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShouldContainOneStringValidator.class);

    private String[]            acceptedValues;

    @Override
    public void initialize(final ShouldContainOneString constraintAnnotation) {
        this.acceptedValues = constraintAnnotation.acceptedValues();
    }

    @Override
    public boolean isValid(final String object, final ConstraintValidatorContext constraintContext) {
        LOGGER.debug("isValid [{}]", object);

        // fields with a ShouldContainOneString will also have a NotNull annotation so nulls will be handled there, to avoid
        // returning an invalid field value return true here

        if (object == null) {
            return true;
        }
        Arrays.sort(acceptedValues);
        return Arrays.binarySearch(acceptedValues, object) >= 0;
    }
}