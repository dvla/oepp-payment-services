package uk.gov.dvla.oep.payment.broker.client;

import com.google.common.io.Resources;
import org.junit.Test;
import uk.gov.dvla.payment.broker.core.InitiatePaymentResponse;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PaymentBrokerClientInitiatePaymentSuccessfulResponseTest extends BasePaymentBrokerClientTest {

    @Test
    public void shouldReturnFullyPopulatedPaymentResponse() throws IOException {
        byte[] responseFromPaymentBroker = Resources.toByteArray(getClass().getResource("/fixtures/initiate-payment-response.json"));

        mockSuccessfulResponse(INITIATE_PAYMENT_PATH, responseFromPaymentBroker);

        InitiatePaymentResponse response = client.initiatePayment(createInitiatePaymentRequest());

        assertThat(response, is((InitiatePaymentResponse) read(responseFromPaymentBroker, InitiatePaymentResponse.class)));
        // note here that the response from the payment broker client is the same as the response from payment broker.
    }

}