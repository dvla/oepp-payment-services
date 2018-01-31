package uk.gov.dvla.oep.payment.broker.client;

import com.google.common.io.Resources;
import org.junit.Test;
import uk.gov.dvla.payment.broker.core.RetrievePaymentResponse;

import java.io.IOException;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PaymentBrokerClientRetrievePaymentSuccessfulResponseTest extends BasePaymentBrokerClientTest {

    @Test
    public void shouldReturnFullyPopulatedOptionalWhenSuccessfulResponseIsReturned() throws IOException {
        byte[] responseFromPaymentBroker = Resources.toByteArray(getClass().getResource("/fixtures/retrieve-payment-response.json"));

        mockSuccessfulResponse(RETRIEVE_PAYMENT_PATH, responseFromPaymentBroker);

        Optional<RetrievePaymentResponse> response = client.retrievePayment(createRetrievePaymentRequest());

        assertThat(response.isPresent(), is(true));
        assertThat(response.get(), is((RetrievePaymentResponse) read(responseFromPaymentBroker, RetrievePaymentResponse.class)));
        // note here that the response from the payment broker client is the same as the response from payment broker.
    }

    @Test
    public void shouldReturnEmptyOptionalWhen404ResponseIsReturned() throws IOException {
        mockServerErrorResponse(RETRIEVE_PAYMENT_PATH, 404);

        Optional<RetrievePaymentResponse> response = client.retrievePayment(createRetrievePaymentRequest());
        assertThat(response.isPresent(), is(false));
    }

}