package uk.gov.dvla.services;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import uk.gov.dvla.core.exception.ExceptionMappersBundle;
import uk.gov.dvla.core.health.ServiceHealthCheck;
import uk.gov.dvla.error.PaymentServiceErrors;
import uk.gov.dvla.oep.payment.broker.client.PaymentBrokerClient;
import uk.gov.dvla.services.health.PaymentProviderHealthCheck;
import uk.gov.dvla.services.resources.PaymentResource;

import javax.ws.rs.client.Client;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * The core Dropwizard application class.
 */
public class Application extends io.dropwizard.Application<Configuration> {
    public static void main(String[] args) throws Exception {
        new Application().run(args);
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<Configuration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(Configuration configuration) {
                SwaggerBundleConfiguration swagger = configuration.getSwagger();
                if (isNullOrEmpty(swagger.getResourcePackage())) {
                    swagger.setResourcePackage(getClass().getPackage().getName());
                }
                return swagger;
            }
        });

        bootstrap.addBundle(new ExceptionMappersBundle<>(PaymentServiceErrors.GENERAL_ERROR));
    }

    @Override
    public void run(Configuration configuration, Environment environment) {

        final Client client = new JerseyClientBuilder(environment).using(configuration.getHttpClient()).build(getName());

        final PaymentBrokerClient paymentBrokerClient = new PaymentBrokerClient(client, configuration.getPaymentBrokerService().getEndpoint(), configuration.getPaymentBrokerService().getAdminEndpoint());
        final PaymentResource paymentResource = new PaymentResource(configuration.getPaymentBrokerService().getApi(), paymentBrokerClient);
        environment.jersey().register(paymentResource);

        environment.healthChecks().register("payment broker", new ServiceHealthCheck(paymentBrokerClient));
        environment.healthChecks().register("payment provider (TLG)", new PaymentProviderHealthCheck(client, configuration.getPaymentProviderHealthCheckURL()));
    }

}
