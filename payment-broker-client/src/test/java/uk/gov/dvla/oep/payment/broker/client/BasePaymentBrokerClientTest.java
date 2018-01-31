package uk.gov.dvla.oep.payment.broker.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.junit.MockServerRule;
import uk.gov.dvla.payment.broker.core.InitiatePaymentRequest;
import uk.gov.dvla.payment.broker.core.RetrievePaymentRequest;
import uk.gov.dvla.payment.broker.core.SettlePaymentRequest;

import javax.ws.rs.client.ClientBuilder;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
public class BasePaymentBrokerClientTest {

    private static final String CHANNEL = "DVLA_WEB";
    private static final String DVLA_REQUESTING_SYSTEM = "EVL";
    private static final long PAYMENT_ID = 1L;
    protected static final String GET_METHOD = "GET";
    protected static final String POST_METHOD = "POST";
    private static final String PAYMENT_BROKER_PATH = "/paymentBroker";
    protected static final String INITIATE_PAYMENT_PATH = PAYMENT_BROKER_PATH + "/initiatePayment";
    protected static final String RETRIEVE_PAYMENT_PATH = PAYMENT_BROKER_PATH + "/retrievePayment";
    protected static final String SETTLE_PAYMENT_PATH = PAYMENT_BROKER_PATH + "/settlePayment";
    protected static final String HEALTHCHECK_PATH = PAYMENT_BROKER_PATH + "/healthcheck";
    protected static final int SUCCESSFUL_STATUS_CODE = 200;
    protected static final int PRECONDITION_FAILED_STATUS_CODE = 412;
    protected static final int REQUEST_BADLY_FORMED_STATUS_CODE = 422;
    protected static final int INTERNAL_SERVER_ERROR_STATUS_CODE = 500;
    protected static final int BAD_GATEWAY_STATUS_CODE = 502;
    protected static final int SERVICE_UNAVAILABLE_STATUS_CODE = 503;
    protected static final int GATEWAY_TIMEOUT_STATUS_CODE = 504;
    protected static final String APPLICATION_JSON = "application/json";
    protected static final String CONTENT_TYPE = "Content-Type";

     @Rule
    public MockServerRule mockServerRule = new MockServerRule(this);

    protected MockServerClient mockServerClient;
    protected PaymentBrokerClient client;

    BasePaymentBrokerClientTest() {}

    @Before
    public void init() throws MalformedURLException {
        mockServerClient = new MockServerClient("localhost", mockServerRule.getPort());

        URL endpoint = new URL("http://localhost:" + mockServerRule.getPort());
        URL adminEndpoint = new URL("http://localhost:" + mockServerRule.getPort());
        this.client = new PaymentBrokerClient(ClientBuilder.newClient(), endpoint, adminEndpoint);
    }

    protected SettlePaymentRequest createSettlePaymentRequest() {
        return new SettlePaymentRequest.Builder()
                .setChannel(CHANNEL)
                .setDvlaRequestingSystem(DVLA_REQUESTING_SYSTEM)
                .setPaymentId(PAYMENT_ID)
                .create();
    }

    protected RetrievePaymentRequest createRetrievePaymentRequest() {
        return new RetrievePaymentRequest.Builder()
                .setChannel(CHANNEL)
                .setDvlaRequestingSystem(DVLA_REQUESTING_SYSTEM)
                .setPaymentId(PAYMENT_ID)
                .create();
    }

    protected InitiatePaymentRequest createInitiatePaymentRequest() {
        return new InitiatePaymentRequest.Builder().setDvlaTransactionId("123999")
                .setDvlaOriginatingSystem(DVLA_REQUESTING_SYSTEM)
                .setChannel(CHANNEL)
                .setLanguage("EN")
                .setResultPageUrl("http://localhost:1234/blah")
                .setPaymentAmount(new BigDecimal("91.3"))
                .setTransactionType("1")
                .create();
    }

    protected Object read(byte[] fixture, Class responseClass) throws IOException {
        return new ObjectMapper().readValue(fixture, responseClass);
    }

    protected void mockSuccessfulResponse(String path, byte[] responseFromPaymentBroker) {
        mockServerClient.when(
                request().withMethod(POST_METHOD).withPath(path))
                .respond(response().withStatusCode(SUCCESSFUL_STATUS_CODE).withHeader(CONTENT_TYPE, APPLICATION_JSON).withBody(responseFromPaymentBroker));
    }

    protected void mockServerErrorResponse(String requestPath, int errorCode) {
        mockServerClient.when(
                request().withMethod(POST_METHOD).withPath(requestPath))
                .respond(response().withStatusCode(errorCode));
    }

}