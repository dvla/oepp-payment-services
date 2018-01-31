package uk.gov.dvla.payment.broker.core.enumerations;

import com.wordnik.swagger.annotations.ApiModel;

@ApiModel(value = "Payment status enum")
public enum PaymentStatus {
    INITIATED,
    IN_PROGRESS,
    AUTHORISED,
    DECLINED,
    CANCELLED_BEFORE_AUTHORISATION,
    CANCELLED_AFTER_AUTHORISATION,
    PAYMENT_FAILED,
    SETTLEMENT_REQUESTED,
    SETTLEMENT_REQUEST_FAILED,
    CANCEL_FAILED,
    REFUNDED,
    REFUND_FAILED,
    SETTLED,
    SETTLEMENT_EXCEPTION,
    SETTLEMENT_FAILED,
    REFUNDED_SETTLED,
    REFUND_SETTLEMENT_FAILED,
    CANCELLED_PARTIAL_REFUND
}
