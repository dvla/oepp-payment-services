package uk.gov.dvla.oep.payment.broker.client;

import uk.gov.dvla.core.client.AbstractServiceClient;
import uk.gov.dvla.core.exception.RequestProcessingException;
import uk.gov.dvla.core.exception.UnexpectedResponseException;
import uk.gov.dvla.payment.broker.core.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.Optional;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.OK;

/**
 * Controls calling the payment broker.
 */
public class PaymentBrokerClient extends AbstractServiceClient {

    private final URL paymentBrokerEndpoint;
    private final URL paymentBrokerAdminEndpoint;

    private final InitiatePaymentServerCommand initiatePaymentServerCommand = new InitiatePaymentServerCommand();
    private final RetrievePaymentServerCommand retrievePaymentServerCommand = new RetrievePaymentServerCommand();
    private final SettlePaymentServerCommand settlePaymentServerCommand = new SettlePaymentServerCommand();

    public PaymentBrokerClient(Client client, URL paymentBrokerEndpoint, URL paymentBrokerAdminEndpoint) {
        super(client);
        this.paymentBrokerEndpoint = paymentBrokerEndpoint;
        this.paymentBrokerAdminEndpoint = paymentBrokerAdminEndpoint;
    }

    public boolean isHealthy() {
        Response response=null;
        try {
            response = client.target(appendPath(paymentBrokerAdminEndpoint, "paymentBroker/healthcheck")).request().get();
            return response.getStatus() == OK.getStatusCode();
        } catch (Exception ex) {
            return false;
        } finally {
            if (response != null)
                response.close();
        }
    }

    /**
     * Initiates payment
     * @param request initiate payment request
     * @return initiate payment response
     * @throws UnexpectedResponseException in case the response status code of the response returned by the server is not successful
     * @throws RequestProcessingException in case the request processing or subsequent I/O operation fails
     */
    public InitiatePaymentResponse initiatePayment(InitiatePaymentRequest request) {
        return callServerForMandatoryResource(initiatePaymentServerCommand, request);
    }

    /**
     * Retrieves payment details
     * @param request retrieve payment request
     * @return retrieve payment response or an empty optional if underlying service returned 404 (NOT FOUND) response
     * @throws UnexpectedResponseException in case the response status code of the response returned by the server is not successful
     * @throws RequestProcessingException in case the request processing or subsequent I/O operation fails
     */
    public Optional<RetrievePaymentResponse> retrievePayment(RetrievePaymentRequest request) {
        return callServerForOptionalResource(retrievePaymentServerCommand, request);
    }

    /**
     * Settles payment
     * @param request settle payment request
     * @throws UnexpectedResponseException in case the response status code of the response returned by the server is not successful
     * @throws RequestProcessingException in case the request processing or subsequent I/O operation fails
     */
    public Void settlePayment(SettlePaymentRequest request) {
        return callServerForMandatoryResource(settlePaymentServerCommand, request);
    }

    private class InitiatePaymentServerCommand implements ServerCommand<InitiatePaymentRequest, InitiatePaymentResponse> {

        @Override
        public InitiatePaymentResponse makeServerCall(InitiatePaymentRequest request) {
            return client.target(appendPath(paymentBrokerEndpoint, "paymentBroker/initiatePayment"))
                    .request().post(Entity.entity(request, APPLICATION_JSON_TYPE), InitiatePaymentResponse.class);
        }

    }

    private class RetrievePaymentServerCommand implements ServerCommand<RetrievePaymentRequest, RetrievePaymentResponse> {

        @Override
        public RetrievePaymentResponse makeServerCall(RetrievePaymentRequest request) {
            return client.target(appendPath(paymentBrokerEndpoint, "paymentBroker/retrievePayment"))
                    .request().post(Entity.entity(request, APPLICATION_JSON_TYPE), RetrievePaymentResponse.class);
        }

    }

    private class SettlePaymentServerCommand implements ServerCommand<SettlePaymentRequest, Void> {

        @Override
        public Void makeServerCall(SettlePaymentRequest request) {
            return client.target(appendPath(paymentBrokerEndpoint, "paymentBroker/settlePayment"))
                    .request().post(Entity.entity(request, APPLICATION_JSON_TYPE), Void.class);
        }

    }

}
