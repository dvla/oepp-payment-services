package uk.gov.dvla.oep.payment.broker.client;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class PaymentBrokerClientHealthcheckTest extends BasePaymentBrokerClientTest {

    @Test
    public void isHealthy_shouldReturnHealthyWhenSuccessfulResponseIsReturned() {
        mockServerClient.when(request().withMethod(GET_METHOD).withPath(HEALTHCHECK_PATH))
                .respond(response().withStatusCode(SUCCESSFUL_STATUS_CODE));

        assertThat(client.isHealthy(), is(true));
    }

    @Test
    public void isHealthy_shouldReturnNotHealthyWhenServerErrorIsReturned() {
        mockServerClient.when(request().withMethod(GET_METHOD).withPath(HEALTHCHECK_PATH))
                .respond(response().withStatusCode(INTERNAL_SERVER_ERROR_STATUS_CODE));

        assertThat(client.isHealthy(), is(false));
    }

    @Test
    public void isHealthy_shouldNotThrowExceptionWhenServiceIsUnavailable() {
        mockServerClient.stop();
        assertThat(client.isHealthy(), is(false));
    }

}