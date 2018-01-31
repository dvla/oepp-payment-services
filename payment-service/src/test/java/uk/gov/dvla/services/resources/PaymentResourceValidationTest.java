package uk.gov.dvla.services.resources;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.dvla.oepp.domain.payment.InitiateOffencePaymentRequest;

import javax.ws.rs.core.Response;

import java.math.BigDecimal;

import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static uk.gov.dvla.TestConstants.INITIATE_PAYMENT_URI;
import static uk.gov.dvla.TestConstants.url;

/**
 * Test class for validation of {@link PaymentResource}
 */
@RunWith(MockitoJUnitRunner.class)
public class PaymentResourceValidationTest extends BasePaymentResourceTest {

    @Test
    public void initiate_shouldReturn422WhenBodyIsNull() {
        Response response = resources.client().target(INITIATE_PAYMENT_URI).request().post(entity((String) null, APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), equalTo(422));
    }

    @Test
    public void initiate_shouldReturn422WhenBodyIsEmpty() {
        Response response = resources.client().target(INITIATE_PAYMENT_URI).request().post(entity("", APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), equalTo(422));
    }

    @Test
    public void initiate_shouldReturn422WhenBodyIsBlank() {
        Response response = resources.client().target(INITIATE_PAYMENT_URI).request().post(entity("  ", APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), equalTo(422));
    }

    @Test
    public void initiate_shouldReturn422WhenBodyTransactionIDIsNull() {
        InitiateOffencePaymentRequest request = initiateOffencePaymentRequestBuilder().setTransactionID(null).create();

        Response response = resources.client().target(INITIATE_PAYMENT_URI).request().post(entity(request, APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), equalTo(422));
    }

    @Test
    public void initiate_shouldReturn422WhenBodyTransactionIDIsTooLong() {
        InitiateOffencePaymentRequest request = initiateOffencePaymentRequestBuilder().setTransactionID("123456789-12345").create();

        Response response = resources.client().target(INITIATE_PAYMENT_URI).request().post(entity(request, APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), equalTo(422));
    }

    @Test
    public void initiate_shouldReturn422WhenBodyPaymentAmountIsNull() {
        InitiateOffencePaymentRequest request = initiateOffencePaymentRequestBuilder().setPaymentAmount(null).create();

        Response response = resources.client().target(INITIATE_PAYMENT_URI).request().post(entity(request, APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), equalTo(422));
    }

    @Test
    public void initiate_shouldReturn422WhenBodyPaymentAmountIsLessThenMinAllowedValue() {
        InitiateOffencePaymentRequest request = initiateOffencePaymentRequestBuilder().setPaymentAmount(BigDecimal.valueOf(0)).create();

        Response response = resources.client().target(INITIATE_PAYMENT_URI).request().post(entity(request, APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), equalTo(422));
    }

    @Test
    public void initiate_shouldReturn422WhenBodyPaymentAmountIsGreaterThenMaxAllowedValue() {
        InitiateOffencePaymentRequest request = initiateOffencePaymentRequestBuilder().setPaymentAmount(BigDecimal.valueOf(1000000)).create();

        Response response = resources.client().target(INITIATE_PAYMENT_URI).request().post(entity(request, APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), equalTo(422));
    }

    @Test
    public void initiate_shouldReturn422WhenBodyLanguageIsNull() {
        InitiateOffencePaymentRequest request = initiateOffencePaymentRequestBuilder().setLanguage(null).create();

        Response response = resources.client().target(INITIATE_PAYMENT_URI).request().post(entity(request, APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), equalTo(422));
    }

    @Test
    public void initiate_shouldReturn422WhenBodyLanguageIsInvalid() {
        InitiateOffencePaymentRequest request = initiateOffencePaymentRequestBuilder().setLanguage("PL").create();

        Response response = resources.client().target(INITIATE_PAYMENT_URI).request().post(entity(request, APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), equalTo(422));
    }

    @Test
    public void initiate_shouldReturn422WhenBodyPostAuthorizeCallbackURLIsNull() {
        InitiateOffencePaymentRequest request = initiateOffencePaymentRequestBuilder().setPostAuthorizeCallbackURL(null).create();

        Response response = resources.client().target(INITIATE_PAYMENT_URI).request().post(entity(request, APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), equalTo(422));
    }

    private InitiateOffencePaymentRequest.Builder initiateOffencePaymentRequestBuilder() {
        return new InitiateOffencePaymentRequest.Builder()
                .setTransactionID("transaction-001")
                .setPaymentAmount(BigDecimal.ONE)
                .setLanguage("EN")
                .setPostAuthorizeCallbackURL(url("http://localhost"));
    }
}
