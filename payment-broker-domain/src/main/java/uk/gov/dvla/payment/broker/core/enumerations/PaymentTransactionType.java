package uk.gov.dvla.payment.broker.core.enumerations;

import com.wordnik.swagger.annotations.ApiModel;

@ApiModel(value = "Payment transaction type enum")
public enum PaymentTransactionType {
    All,
    Payment,
    Refund
}
