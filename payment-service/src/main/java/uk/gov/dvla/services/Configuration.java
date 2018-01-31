package uk.gov.dvla.services;

import io.dropwizard.client.JerseyClientConfiguration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URL;

/**
 * The configuration class for the offence service.
 */
public class Configuration extends io.dropwizard.Configuration {

    @Valid
    private PaymentBrokerServiceConfiguration paymentBrokerService = new PaymentBrokerServiceConfiguration();
    @Valid
    private SwaggerBundleConfiguration swagger = new SwaggerBundleConfiguration();
    @Valid
    private JerseyClientConfiguration httpClient = new JerseyClientConfiguration();

    @NotNull
    private URL paymentProviderHealthCheckURL;

    public PaymentBrokerServiceConfiguration getPaymentBrokerService() {
        return paymentBrokerService;
    }

    public SwaggerBundleConfiguration getSwagger() {
        return swagger;
    }

    public JerseyClientConfiguration getHttpClient() {
        return httpClient;
    }

    public URL getPaymentProviderHealthCheckURL() {
        return paymentProviderHealthCheckURL;
    }

    public static class PaymentBrokerServiceConfiguration {
        @NotNull
        private URL endpoint;
        @NotNull
        private URL adminEndpoint;
        @Valid
        private ApiConfiguration api = new ApiConfiguration();

        public URL getEndpoint() {
            return endpoint;
        }

        public URL getAdminEndpoint() {
            return adminEndpoint;
        }

        public ApiConfiguration getApi() {
            return api;
        }

        public static class ApiConfiguration {
            @NotBlank
            private String channel;

            public String getChannel() {
                return channel;
            }
        }
    }

}
