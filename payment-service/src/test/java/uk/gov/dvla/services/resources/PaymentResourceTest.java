package uk.gov.dvla.services.resources;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.dvla.core.error.ErrorResult;
import uk.gov.dvla.core.exception.RequestProcessingException;
import uk.gov.dvla.core.exception.UnexpectedResponseException;
import uk.gov.dvla.error.PaymentServiceErrors;
import uk.gov.dvla.oepp.domain.payment.InitiateOffencePaymentRequest;
import uk.gov.dvla.oepp.domain.payment.InitiateOffencePaymentResponse;
import uk.gov.dvla.payment.broker.core.*;
import uk.gov.dvla.payment.broker.core.enumerations.PaymentStatus;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.Optional;

import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static uk.gov.dvla.TestConstants.*;

/**
 * Test class for {@link PaymentResource}
 */
@RunWith(MockitoJUnitRunner.class)
public class PaymentResourceTest extends BasePaymentResourceTest {

    private static final InitiatePaymentRequest initiatePaymentRequest = initiatePaymentRequest();
    private static final InitiatePaymentResponse initiatePaymentResponse = initiatePaymentResponse();

    @Test
    public void initiate_whenPaymentIsInitiatedCorrectParametersShouldBePassedToUnderlyingService() {
        when(paymentClient.initiatePayment(initiatePaymentRequest)).thenReturn(initiatePaymentResponse);

        resources.client().target(INITIATE_PAYMENT_URI).request().post(entity(initiateOffencePaymentRequest(), APPLICATION_JSON_TYPE));
        ArgumentCaptor<InitiatePaymentRequest> captor = ArgumentCaptor.forClass(InitiatePaymentRequest.class);
        verify(paymentClient).initiatePayment(captor.capture());

        InitiatePaymentRequest capturedValue = captor.getValue();
        assertThat(capturedValue.getDvlaTransactionId(), is(TRANSACTION_ID));
        assertThat(capturedValue.getDvlaOriginatingSystem(), is(PaymentResource.SYSTEM));
        assertThat(capturedValue.getTransactionType(), is(PaymentResource.TRANSACTION_TYPE));
        assertThat(capturedValue.getChannel(), is(CHANNEL));
        assertThat(capturedValue.getPaymentAmount(), is(PAYMENT_AMOUNT));
        assertThat(capturedValue.getPurchaseDescription(), is("DVLA" + TRANSACTION_ID));
        assertThat(capturedValue.getLanguage(), is(LANGUAGE));
        assertThat(capturedValue.getResultPageUrl(), is(POST_AUTHORIZE_CALLBACK_URL.toString()));
    }

    @Test
    public void initiate_whenPaymentIsInitiatedThenSuccessfulResponseAlongWithPaymentDetailsAreReturned() {
        when(paymentClient.initiatePayment(initiatePaymentRequest)).thenReturn(initiatePaymentResponse);

        Response response = resources.client().target(INITIATE_PAYMENT_URI).request().post(entity(initiateOffencePaymentRequest(), APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), equalTo(OK.getStatusCode()));

        InitiateOffencePaymentResponse paymentFromResponse = response.readEntity(InitiateOffencePaymentResponse.class);
        assertThat(paymentFromResponse.getPaymentID(), equalTo(PAYMENT_ID));
        assertThat(paymentFromResponse.getPaymentReference(), equalTo(PAYMENT_REFERENCE));
        assertThat(paymentFromResponse.getPaymentPageUrl(), equalTo(PAYMENT_PAGE_URL.toString()));
    }

    @Test
    public void initiate_whenInitiatePaymentThrowsUnexpectedResponseExceptionThenGeneralErrorResponseIsReturned() {
        doThrow(unexpectedResponseException()).when(paymentClient).initiatePayment(initiatePaymentRequest);

        Response response = resources.client().target(INITIATE_PAYMENT_URI).request().post(entity(initiatePaymentRequest, APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), equalTo(INTERNAL_SERVER_ERROR.getStatusCode()));

        assertThatResponseCarriesCorrectErrorResult(response, INTERNAL_SERVER_ERROR.getStatusCode(), PaymentServiceErrors.GENERAL_ERROR);
    }

    @Test
    public void initiate_whenInitiatePaymentThrowsRequestProcessingExceptionThenGeneralErrorResponseIsReturned() {
        doThrow(requestProcessingException()).when(paymentClient).initiatePayment(initiatePaymentRequest);

        Response response = resources.client().target(INITIATE_PAYMENT_URI).request().post(entity(initiatePaymentRequest, APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), equalTo(INTERNAL_SERVER_ERROR.getStatusCode()));

        assertThatResponseCarriesCorrectErrorResult(response, INTERNAL_SERVER_ERROR.getStatusCode(), PaymentServiceErrors.GENERAL_ERROR);
    }

    @Test
    public void fulfill_whenPaymentIsFulfilledCorrectParametersShouldBePassedToPaymentService() {
        when(paymentClient.retrievePayment(retrievePaymentRequest(PAYMENT_ID))).thenReturn(Optional.of(retrievePaymentResponse(PaymentStatus.AUTHORISED)));

        resources.client().target(FULFILL_PAYMENT_URI).request().put(entity("", APPLICATION_JSON));
        ArgumentCaptor<RetrievePaymentRequest> retrievePaymentCaptor = ArgumentCaptor.forClass(RetrievePaymentRequest.class);
        verify(paymentClient).retrievePayment(retrievePaymentCaptor.capture());
        ArgumentCaptor<SettlePaymentRequest> settlePaymentCaptor = ArgumentCaptor.forClass(SettlePaymentRequest.class);
        verify(paymentClient).settlePayment(settlePaymentCaptor.capture());

        RetrievePaymentRequest capturedRetrievePaymentRequest = retrievePaymentCaptor.getValue();
        assertThat(capturedRetrievePaymentRequest.getPaymentId(), is(PAYMENT_ID));
        assertThat(capturedRetrievePaymentRequest.getDvlaRequestingSystem(), is(PaymentResource.SYSTEM));
        assertThat(capturedRetrievePaymentRequest.getChannel(), is(CHANNEL));
        SettlePaymentRequest capturedSettlePaymentRequest = settlePaymentCaptor.getValue();
        assertThat(capturedSettlePaymentRequest.getPaymentId(), is(PAYMENT_ID));
        assertThat(capturedSettlePaymentRequest.getDvlaRequestingSystem(), is(PaymentResource.SYSTEM));
        assertThat(capturedSettlePaymentRequest.getChannel(), is(CHANNEL));
    }

    @Test
    public void fulfill_whenRetrievedPaymentDoesNotExistThenBothPaymentAndLegacySystemMustNotBeCalledAndNotFoundErrorResponseIsReturned() {
        when(paymentClient.retrievePayment(retrievePaymentRequest(PAYMENT_ID))).thenReturn(Optional.<RetrievePaymentResponse>empty());

        Response response = resources.client().target(FULFILL_PAYMENT_URI).request().put(entity("", APPLICATION_JSON));
        assertThatResponseCarriesCorrectErrorResult(response, NOT_FOUND.getStatusCode(), PaymentServiceErrors.PAYMENT_NOT_FOUND);

        verify(paymentClient, never()).settlePayment(any());
    }

    @Test
    public void fulfill_whenPaymentAuthorisationFailedThenBothPaymentAndLegacySystemMustNotBeCalledAndNotAuthorizedErrorResponseIsReturned() {
        when(paymentClient.retrievePayment(retrievePaymentRequest(PAYMENT_ID))).thenReturn(Optional.of(retrievePaymentResponse(PaymentStatus.CANCELLED_AFTER_AUTHORISATION)));

        Response response = resources.client().target(FULFILL_PAYMENT_URI).request().put(entity("", APPLICATION_JSON));
        assertThatResponseCarriesCorrectErrorResult(response, PRECONDITION_FAILED.getStatusCode(), PaymentServiceErrors.PAYMENT_NOT_AUTHORISED);

        verify(paymentClient, never()).settlePayment(any());
    }

    @Test
    public void fulfill_whenRetrievingPaymentThrowsUnexpectedResponseExceptionThenBothPaymentAndLegacySystemMustNotBeCalledAndGeneralErrorResponseIsReturned() {
        doThrow(unexpectedResponseException()).when(paymentClient).retrievePayment(retrievePaymentRequest(PAYMENT_ID));

        Response response = resources.client().target(FULFILL_PAYMENT_URI).request().put(entity("", APPLICATION_JSON));
        assertThatResponseCarriesCorrectErrorResult(response, INTERNAL_SERVER_ERROR.getStatusCode(), PaymentServiceErrors.GENERAL_ERROR);

        verify(paymentClient, never()).settlePayment(any());
    }

    @Test
    public void fulfill_whenRetrievingPaymentThrowsRequestProcessingExceptionThenBothPaymentAndLegacySystemMustNotBeCalledAndGeneralErrorResponseIsReturned() {
        doThrow(requestProcessingException()).when(paymentClient).retrievePayment(retrievePaymentRequest(PAYMENT_ID));

        Response response = resources.client().target(FULFILL_PAYMENT_URI).request().put(entity("", APPLICATION_JSON));
        assertThatResponseCarriesCorrectErrorResult(response, INTERNAL_SERVER_ERROR.getStatusCode(), PaymentServiceErrors.GENERAL_ERROR);

        verify(paymentClient, never()).settlePayment(any());
    }

    @Test
    public void fulfill_whenPaymentSettlementThrowsUnexpectedResponseExceptionFor404ResponseThenLegacySystemMustNotBeCalledAndNotFoundErrorResponseIsReturned() {
        when(paymentClient.retrievePayment(retrievePaymentRequest(PAYMENT_ID))).thenReturn(Optional.of(retrievePaymentResponse(PaymentStatus.AUTHORISED)));
        when(paymentClient.settlePayment(settlePaymentRequest(PAYMENT_ID))).thenThrow(unexpectedResponseException(NOT_FOUND.getStatusCode()));

        Response response = resources.client().target(FULFILL_PAYMENT_URI).request().put(entity("", APPLICATION_JSON));
        assertThatResponseCarriesCorrectErrorResult(response, NOT_FOUND.getStatusCode(), PaymentServiceErrors.PAYMENT_NOT_FOUND);
    }

    @Test
    public void fulfill_whenPaymentSettlementThrowsUnexpectedResponseExceptionThenLegacySystemMustNotBeCalledAndGeneralErrorResponseIsReturned() {
        when(paymentClient.retrievePayment(retrievePaymentRequest(PAYMENT_ID))).thenReturn(Optional.of(retrievePaymentResponse(PaymentStatus.AUTHORISED)));
        when(paymentClient.settlePayment(settlePaymentRequest(PAYMENT_ID))).thenThrow(unexpectedResponseException());

        Response response = resources.client().target(FULFILL_PAYMENT_URI).request().put(entity("", APPLICATION_JSON));
        assertThatResponseCarriesCorrectErrorResult(response, INTERNAL_SERVER_ERROR.getStatusCode(), PaymentServiceErrors.GENERAL_ERROR);
    }

    @Test
    public void fulfill_whenPaymentSettlementThrowsRequestProcessingExceptionThenLegacySystemMustNotBeCalledAndGeneralErrorResponseIsReturned() {
        when(paymentClient.retrievePayment(retrievePaymentRequest(PAYMENT_ID))).thenReturn(Optional.of(retrievePaymentResponse(PaymentStatus.AUTHORISED)));
        when(paymentClient.settlePayment(settlePaymentRequest(PAYMENT_ID))).thenThrow(requestProcessingException());

        Response response = resources.client().target(FULFILL_PAYMENT_URI).request().put(entity("", APPLICATION_JSON));
        assertThatResponseCarriesCorrectErrorResult(response, INTERNAL_SERVER_ERROR.getStatusCode(), PaymentServiceErrors.GENERAL_ERROR);
    }

    private InitiateOffencePaymentRequest initiateOffencePaymentRequest() {
        return new InitiateOffencePaymentRequest.Builder()
                .setTransactionID(TRANSACTION_ID)
                .setPaymentAmount(PAYMENT_AMOUNT)
                .setLanguage(LANGUAGE)
                .setPostAuthorizeCallbackURL(POST_AUTHORIZE_CALLBACK_URL)
                .create();
    }

    private static InitiatePaymentRequest initiatePaymentRequest() {
        return new InitiatePaymentRequest.Builder()
                .setDvlaTransactionId(TRANSACTION_ID)
                .setDvlaOriginatingSystem(PaymentResource.SYSTEM)
                .setTransactionType(PaymentResource.TRANSACTION_TYPE)
                .setChannel(CHANNEL)
                .setPaymentAmount(PAYMENT_AMOUNT)
                .setPurchaseDescription("DVLA" + TRANSACTION_ID)
                .setLanguage(LANGUAGE)
                .setResultPageUrl(POST_AUTHORIZE_CALLBACK_URL.toString())
                .create();
    }

    private static InitiatePaymentResponse initiatePaymentResponse() {
        return new InitiatePaymentResponse(PAYMENT_ID, PAYMENT_REFERENCE, PAYMENT_PAGE_URL.toString());
    }

    private RetrievePaymentRequest retrievePaymentRequest(Long paymentID) {
        return new RetrievePaymentRequest.Builder()
                .setPaymentId(paymentID)
                .setDvlaRequestingSystem(PaymentResource.SYSTEM)
                .setChannel(CHANNEL)
                .create();
    }

    private RetrievePaymentResponse retrievePaymentResponse(PaymentStatus paymentStatus) {
        return new RetrievePaymentResponse(null, null, null, null, null, null, null, null, null, null, paymentStatus, null, null, null, PAYMENT_AMOUNT, null, null);
    }

    private SettlePaymentRequest settlePaymentRequest(Long paymentID) {
        return new SettlePaymentRequest.Builder()
                .setPaymentId(paymentID)
                .setDvlaRequestingSystem(PaymentResource.SYSTEM)
                .setChannel(CHANNEL)
                .create();
    }

    private UnexpectedResponseException unexpectedResponseException() {
        return unexpectedResponseException(INTERNAL_SERVER_ERROR.getStatusCode());
    }

    private UnexpectedResponseException unexpectedResponseException(int responseStatusCode) {
        return new UnexpectedResponseException(responseStatusCode, null, null);
    }

    private RequestProcessingException requestProcessingException() {
        return new RequestProcessingException(null);
    }

    private void assertThatResponseCarriesCorrectErrorResult(Response response, int responseStatusCode, PaymentServiceErrors errorExtractedFromErrorResult) {
        ErrorResult<PaymentServiceErrors> errorResult = response.readEntity(new GenericType<ErrorResult<PaymentServiceErrors>>() {});
        assertThat(errorResult.getStatus(), is(responseStatusCode));
        assertThat(errorResult.getError(), is(errorExtractedFromErrorResult));
    }

}
