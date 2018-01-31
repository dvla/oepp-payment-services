package uk.gov.dvla;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Constants used for testing.
 */
public final class TestConstants {

    private TestConstants() {
        throw new AssertionError("This class should not be instantiated");
    }

    public static final Long CASE_NUMBER = 12345678L;

    public static final Long PAYMENT_ID = 1234567890L;
    public static final String PAYMENT_REFERENCE = "ABC123";
    public static final BigDecimal PAYMENT_AMOUNT = BigDecimal.valueOf(12.31);

    public static final BigDecimal PENALTY_AMOUNT = BigDecimal.valueOf(10.00);
    public static final BigDecimal ARREARS_AMOUNT = BigDecimal.valueOf(2.31);

    public static final String INITIATE_PAYMENT_URI = "/payment/initiate";
    public static final String FULFILL_PAYMENT_URI = "/payment/fulfill/" + PAYMENT_ID;

    public static final URL PAYMENT_PAGE_URL = url("http://localhost:9999/payment/start");
    public static final URL POST_AUTHORIZE_CALLBACK_URL = url("http://localhost:9999/payment/finish");

    public static final String TRANSACTION_ID = "999888";
    public static final String CHANNEL = "XXXXX";
    public static final String LANGUAGE = "EN";

    public static URL url(String value) {
        try {
            return new URL(value);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
