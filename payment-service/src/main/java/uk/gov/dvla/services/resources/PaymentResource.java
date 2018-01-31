package uk.gov.dvla.services.resources;

import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.dvla.core.exception.UnexpectedResponseException;
import uk.gov.dvla.core.exception.WebApplicationException;
import uk.gov.dvla.error.PaymentServiceErrors;
import uk.gov.dvla.oep.payment.broker.client.PaymentBrokerClient;
import uk.gov.dvla.oepp.domain.payment.InitiateOffencePaymentRequest;
import uk.gov.dvla.oepp.domain.payment.InitiateOffencePaymentResponse;
import uk.gov.dvla.payment.broker.core.*;
import uk.gov.dvla.services.Configuration.PaymentBrokerServiceConfiguration.ApiConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.PRECONDITION_FAILED;

/**
 * This class holds the RESTful resources that are associated with URIs.
 */
@Path("/payment")
@Api(value = "/payment", description = "Payment service operations")
@Produces(MediaType.APPLICATION_JSON)
public class PaymentResource {

    public static final String SYSTEM = "OECR"; // will vary by penalty type
    public static final String TRANSACTION_TYPE = "CRP"; // will vary by penalty type

    private static final Logger logger = LoggerFactory.getLogger(PaymentResource.class);

    private ApiConfiguration apiConfiguration;
    private PaymentBrokerClient paymentClient;

    public PaymentResource(ApiConfiguration apiConfiguration, PaymentBrokerClient paymentClient) {
        this.apiConfiguration = apiConfiguration;
        this.paymentClient = paymentClient;
    }

    @Timed
    @POST
    @ApiOperation(value = "Initiate an offence payment", response = InitiateOffencePaymentResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Valid request and successful call"),
            @ApiResponse(code = 400, message = "Invalid data in request"),
            @ApiResponse(code = 422, message = "Invalid data in request"),
            @ApiResponse(code = 500, message = "General server error")
    })
    @Path("/initiate")
    public InitiateOffencePaymentResponse initiate(@ApiParam(value = "Valid initiate offence payment request", required = true) @NotNull @Valid InitiateOffencePaymentRequest request) {

        InitiatePaymentResponse response = paymentClient.initiatePayment(new InitiatePaymentRequest.Builder()
                .setDvlaTransactionId(request.getTransactionID())
                .setDvlaOriginatingSystem(SYSTEM)
                .setTransactionType(TRANSACTION_TYPE)
                .setChannel(apiConfiguration.getChannel())
                .setPaymentAmount(request.getPaymentAmount())
                .setPurchaseDescription("DVLA" + request.getTransactionID())
                .setLanguage(request.getLanguage())
                .setResultPageUrl(request.getPostAuthorizeCallbackURL().toString())
                .create()
        );

        return new InitiateOffencePaymentResponse.Builder()
                .setPaymentID(response.getPaymentId())
                .setPaymentPageUrl(response.getPaymentPageUrl())
                .setPaymentReference(response.getPaymentReference())
                .create();
    }

    @Timed
    @PUT
    @ApiOperation("Fulfills an offence payment")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Valid request and successful call"),
            @ApiResponse(code = 400, message = "Invalid data in request"),
            @ApiResponse(code = 404, message = "Payment does not exist"),
            @ApiResponse(code = 422, message = "Invalid data in request"),
            @ApiResponse(code = 500, message = "General server error")
    })
    @Path("/fulfill/{paymentID}")
    public void fulfill(@ApiParam("Payment ID") @PathParam("paymentID") Long paymentID) {

        Optional<RetrievePaymentResponse> payment = paymentClient.retrievePayment(
                new RetrievePaymentRequest.Builder()
                        .setPaymentId(paymentID)
                        .setDvlaRequestingSystem(SYSTEM)
                        .setChannel(apiConfiguration.getChannel())
                        .create()
        );

        if (!payment.isPresent()) {
            logger.warn("Payment {} doesn't exist", paymentID);
            throw new WebApplicationException(Response.Status.NOT_FOUND.getStatusCode(), PaymentServiceErrors.PAYMENT_NOT_FOUND);
        }

        final String paymentStatus = payment.get().getPaymentStatus().toString();

        if (!Objects.equals(paymentStatus, "AUTHORISED")) {
            logger.info("Payment {} is not authorised, payment status is {}", paymentID, paymentStatus);
            throw new WebApplicationException(PRECONDITION_FAILED.getStatusCode(), PaymentServiceErrors.PAYMENT_NOT_AUTHORISED);
        }

        try {
            paymentClient.settlePayment(new SettlePaymentRequest.Builder()
                    .setPaymentId(paymentID)
                    .setDvlaRequestingSystem(SYSTEM)
                    .setChannel(apiConfiguration.getChannel())
                    .create()
            );
        } catch (UnexpectedResponseException ex) {
            if (ex.getResponseStatus() == NOT_FOUND.getStatusCode()) {
                logger.warn("Payment {} doesn't exist", paymentID);
                throw new WebApplicationException(Response.Status.NOT_FOUND.getStatusCode(), PaymentServiceErrors.PAYMENT_NOT_FOUND);
            }
            throw ex;
        }
    }

}
