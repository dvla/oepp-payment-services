package uk.gov.dvla.payment.broker.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiatePaymentResponse {

    private final Long paymentId;
    private final String paymentReference;
    private final String paymentPageUrl;

    @JsonCreator
    public InitiatePaymentResponse(@JsonProperty("paymentId") Long paymentId,
                                   @JsonProperty("paymentReference") String paymentReference,
                                   @JsonProperty("paymentPageUrl") String paymentPageUrl) {
        this.paymentId = paymentId;
        this.paymentReference = paymentReference;
        this.paymentPageUrl = paymentPageUrl;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public String getPaymentPageUrl() {
        return paymentPageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InitiatePaymentResponse that = (InitiatePaymentResponse) o;
        return Objects.equals(paymentId, that.paymentId) &&
                Objects.equals(paymentReference, that.paymentReference) &&
                Objects.equals(paymentPageUrl, that.paymentPageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, paymentReference, paymentPageUrl);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("paymentId", paymentId)
                .add("paymentReference", paymentReference)
                .add("paymentPageUrl", paymentPageUrl)
                .toString();
    }
}
