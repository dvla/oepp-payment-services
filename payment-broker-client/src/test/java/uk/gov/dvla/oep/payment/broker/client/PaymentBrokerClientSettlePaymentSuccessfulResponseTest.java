package uk.gov.dvla.oep.payment.broker.client;

import org.junit.Test;

import java.io.IOException;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class PaymentBrokerClientSettlePaymentSuccessfulResponseTest extends BasePaymentBrokerClientTest {

    @Test
    public void shouldReturnSuccessfulResponse() throws IOException {

        mockServerClient.when(
                request().withMethod(POST_METHOD).withPath(SETTLE_PAYMENT_PATH))
                .respond(response().withStatusCode(SUCCESSFUL_STATUS_CODE).withHeader(CONTENT_TYPE, APPLICATION_JSON));
                // Note that no body is set on the response here.

        client.settlePayment(createSettlePaymentRequest());
    }

}