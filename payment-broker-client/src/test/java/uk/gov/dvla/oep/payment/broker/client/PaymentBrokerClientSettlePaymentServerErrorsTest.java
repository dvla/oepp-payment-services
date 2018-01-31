package uk.gov.dvla.oep.payment.broker.client;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import uk.gov.dvla.core.exception.UnexpectedResponseException;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class PaymentBrokerClientSettlePaymentServerErrorsTest extends BasePaymentBrokerClientTest {

    @Parameterized.Parameter(0)
    public int errorCode;

    @Parameterized.Parameters(name = "errorCode = {0}")
    public static Collection testData() {
        return Arrays.asList(PRECONDITION_FAILED_STATUS_CODE,
                REQUEST_BADLY_FORMED_STATUS_CODE,
                INTERNAL_SERVER_ERROR_STATUS_CODE,
                BAD_GATEWAY_STATUS_CODE,
                SERVICE_UNAVAILABLE_STATUS_CODE,
                GATEWAY_TIMEOUT_STATUS_CODE);
    }

    @Test
    public void shouldThrowUnexpectedResponseExceptionWhenErrorFromPaymentBroker() {

        mockServerErrorResponse(SETTLE_PAYMENT_PATH, errorCode);

        try {
            client.settlePayment(createSettlePaymentRequest());
            fail("Expected an UnexpectedResponseException");
        } catch (UnexpectedResponseException ex) {
            assertThat(ex.getResponseStatus(), Is.is(errorCode));
        } catch (Exception ex) {
            fail("Unexpected exception: " + ex);
        }
    }

}