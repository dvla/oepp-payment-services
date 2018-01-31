package uk.gov.dvla.services.health;

import com.codahale.metrics.health.HealthCheck;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import java.net.URL;

import static javax.ws.rs.core.Response.Status.OK;

public class PaymentProviderHealthCheck extends HealthCheck {

    private final Client client;
    private final URL healthCheckEndpoint;

    public PaymentProviderHealthCheck(Client client, URL healthCheckEndpoint) {
        this.client = client;
        this.healthCheckEndpoint = healthCheckEndpoint;
    }

    @Override
    protected Result check() {
        Response response = null;
        try {
            response = client.target(healthCheckEndpoint.toString()).request().get();
            return response.getStatus() == OK.getStatusCode() ? Result.healthy() : Result.unhealthy("Payment Provider returned non 200 response");
        } catch (Exception ex) {
            return Result.unhealthy("Health check of underlying service has reported some problems : " + ex.getMessage());
        }   finally {
            if (response != null)
                response.close();
        }

    }
}
