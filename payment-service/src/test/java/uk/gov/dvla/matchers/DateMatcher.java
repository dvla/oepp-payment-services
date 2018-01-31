package uk.gov.dvla.matchers;

import org.apache.commons.lang3.time.DateUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DateMatcher extends TypeSafeMatcher<Date> {

    private Date expectedValue;

    private DateMatcher(Date expectedValue) {
        this.expectedValue = expectedValue;
    }

    @Override
    protected boolean matchesSafely(Date actualValue) {
        return Objects.equals(truncateToMinute(expectedValue), truncateToMinute(actualValue));
    }

    private Date truncateToMinute(Date value) {
        return DateUtils.truncate(value, Calendar.MINUTE);
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(expectedValue);
    }

    public static DateMatcher datesEqualTo(Date expectedValue) {
        return new DateMatcher(expectedValue);
    }

}
