package uk.gov.dvla.services.health;

import com.codahale.metrics.health.HealthCheck.Result;
import org.junit.Rule;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.junit.MockServerRule;

import javax.ws.rs.client.ClientBuilder;
import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class PaymentProviderHealthCheckTest {

    @Rule
    public MockServerRule mockServerRule = new MockServerRule(this);

    private MockServerClient mockServerClient;
    private PaymentProviderHealthCheck healthCheck;

    public PaymentProviderHealthCheckTest() throws MalformedURLException {
        mockServerClient = new MockServerClient("localhost", mockServerRule.getPort());
        this.healthCheck = new PaymentProviderHealthCheck(ClientBuilder.newClient(), new URL("http://localhost:" + mockServerRule.getPort()));
    }

    @Test
    public void isHealthy_shouldReturnHealthyWhenSuccessfulResponseIsReturned() {
        mockServerClient.when(request().withMethod("GET").withPath("/"))
                .respond(response().withStatusCode(200));

        assertThat(healthCheck.check(), is(Result.healthy()));
    }

    @Test
    public void isHealthy_shouldReturnNotHealthyWhenServerErrorIsReturned() {
        mockServerClient.when(request().withMethod("GET").withPath("/"))
                .respond(response().withStatusCode(500));

        assertThat(healthCheck.check().isHealthy(), is(false));
    }

    @Test
    public void isHealthy_shouldNotThrowExceptionWhenServiceIsUnavailable() {
        mockServerClient.stop();
        assertThat(healthCheck.check().isHealthy(), is(false));
    }

}