package uk.gov.dvla.services.resources;

import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Rule;
import uk.gov.dvla.core.exception.WebApplicationExceptionMapper;
import uk.gov.dvla.error.PaymentServiceErrors;
import uk.gov.dvla.oep.payment.broker.client.PaymentBrokerClient;
import uk.gov.dvla.services.Configuration;

import static org.mockito.Mockito.mock;
import static uk.gov.dvla.TestConstants.CHANNEL;

public class BasePaymentResourceTest {

    protected PaymentBrokerClient paymentClient = mock(PaymentBrokerClient.class);

    @Rule
    public ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new PaymentResource(prepareConfiguration(), paymentClient))
            .addResource(new WebApplicationExceptionMapper(PaymentServiceErrors.GENERAL_ERROR))
            .build();

    protected BasePaymentResourceTest() {}

    protected Configuration.PaymentBrokerServiceConfiguration.ApiConfiguration prepareConfiguration() {
        return new Configuration.PaymentBrokerServiceConfiguration.ApiConfiguration() {
            @Override
            public String getChannel() {
                return CHANNEL;
            }
        };
    }
}
