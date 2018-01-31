package uk.gov.dvla.oepp.domain.payment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

/**
 * Represents an initiate offence payment response.
 * The nested {@link Builder} should be used to create this class.
 * <p>
 * Example JSON
 * <pre>
 *   {
 *     "paymentID": 1,
 *     "paymentReference": "9c2e189c19ed",
 *     "paymentPageUrl": "http://localhost/secure-payment-form"
 *   }
 * </pre>
 */
public class InitiateOffencePaymentResponse {

    private final Long paymentID;
    private final String paymentReference;
    private final String paymentPageUrl;

    @JsonCreator
    private InitiateOffencePaymentResponse(@JsonProperty("paymentID") Long paymentID,
                                           @JsonProperty("paymentReference") String paymentReference,
                                           @JsonProperty("paymentPageUrl") String paymentPageUrl) {
        this.paymentID = paymentID;
        this.paymentReference = paymentReference;
        this.paymentPageUrl = paymentPageUrl;
    }

    public Long getPaymentID() {
        return paymentID;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public String getPaymentPageUrl() {
        return paymentPageUrl;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("paymentID", paymentID)
                .add("paymentReference", paymentReference)
                .add("paymentPageUrl", paymentPageUrl)
                .toString();
    }

    public static class Builder {
        private Long paymentID;
        private String paymentReference;
        private String paymentPageUrl;

        public Builder setPaymentID(Long paymentID) {
            this.paymentID = paymentID;
            return this;
        }

        public Builder setPaymentReference(String paymentReference) {
            this.paymentReference = paymentReference;
            return this;
        }

        public Builder setPaymentPageUrl(String paymentPageUrl) {
            this.paymentPageUrl = paymentPageUrl;
            return this;
        }

        public InitiateOffencePaymentResponse create() {
            return new InitiateOffencePaymentResponse(paymentID, paymentReference, paymentPageUrl);
        }

    }
}
