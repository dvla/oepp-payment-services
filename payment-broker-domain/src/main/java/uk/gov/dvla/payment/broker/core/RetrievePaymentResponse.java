package uk.gov.dvla.payment.broker.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.MoreObjects;
import uk.gov.dvla.payment.broker.core.enumerations.PaymentStatus;
import uk.gov.dvla.payment.broker.core.serializer.MoneySerializer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RetrievePaymentResponse {

    private final String dvlaOriginatingSystem;
    private final String dvlaTransactionId;
    private final List<AdditionalInformation> dvlaAdditionalInformation;

    private String purchaseDescription;

    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal requestedPaymentAmount;
    private final String transactionType;
    private final String paymentReference;
    private String callReference;
    private final String channel;
    private String agentId;
    private PaymentStatus paymentStatus;
    private String paymentType;
    private String maskedPAN;
    private String expiryDate;

    @JsonSerialize(using = MoneySerializer.class)
    private final BigDecimal transactionAmount;

    @JsonSerialize(using = MoneySerializer.class)
    private final BigDecimal surcharge;

    @JsonSerialize(using = MoneySerializer.class)
    private final BigDecimal totalAmountPaid;

    @JsonCreator
    public RetrievePaymentResponse(@JsonProperty("dvlaTransactionId") String dvlaTransactionId,
                                   @JsonProperty("dvlaOriginatingSystem") String dvlaOriginatingSystem,
                                   @JsonProperty("purchaseDescription") String purchaseDescription,
                                   @JsonProperty("requestedPaymentAmount") BigDecimal requestedPaymentAmount,
                                   @JsonProperty("dvlaAdditionalInformation") List<AdditionalInformation> dvlaAdditionalInformation,
                                   @JsonProperty("channel") String channel,
                                   @JsonProperty("agentId") String agentId,
                                   @JsonProperty("transactionType") String transactionType,
                                   @JsonProperty("paymentReference") String paymentReference,
                                   @JsonProperty("callReference") String callReference,
                                   @JsonProperty("paymentStatus") PaymentStatus paymentStatus,
                                   @JsonProperty("paymentType") String paymentType,
                                   @JsonProperty("maskedPAN") String maskedPAN,
                                   @JsonProperty("expiryDate") String expiryDate,
                                   @JsonProperty("transactionAmount") BigDecimal transactionAmount,
                                   @JsonProperty("surcharge") BigDecimal surcharge,
                                   @JsonProperty("totalAmountPaid") BigDecimal totalAmountPaid) {
        this.dvlaTransactionId = dvlaTransactionId;
        this.dvlaOriginatingSystem = dvlaOriginatingSystem;
        this.purchaseDescription = purchaseDescription;
        this.requestedPaymentAmount = requestedPaymentAmount;
        this.dvlaAdditionalInformation = dvlaAdditionalInformation;
        this.channel = channel;
        this.agentId = agentId;
        this.transactionType = transactionType;
        this.paymentReference = paymentReference;
        this.callReference = callReference;
        this.paymentStatus = paymentStatus;
        this.paymentType = paymentType;
        this.maskedPAN = maskedPAN;
        this.expiryDate = expiryDate;
        this.transactionAmount = transactionAmount;
        this.surcharge = surcharge;
        this.totalAmountPaid = totalAmountPaid;
    }

    public String getDvlaTransactionId() {
        return dvlaTransactionId;
    }

    public String getDvlaOriginatingSystem() {
        return dvlaOriginatingSystem;
    }

    public String getPurchaseDescription() { return purchaseDescription; }

    public BigDecimal getRequestedPaymentAmount() { return requestedPaymentAmount; }

    public List<AdditionalInformation> getDvlaAdditionalInformation() {
        return dvlaAdditionalInformation;
    }

    public String getChannel() {
        return channel;
    }

    public String getAgentId() { return agentId; }

    public String getTransactionType() {
        return transactionType;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public String getCallReference() {
        return callReference;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getMaskedPAN() {
        return maskedPAN;
    }

    public String getExpiryDate() {
        return expiryDate;
    }


    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public BigDecimal getSurcharge() {
        return surcharge;
    }

    public BigDecimal getTotalAmountPaid() {
        return totalAmountPaid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RetrievePaymentResponse that = (RetrievePaymentResponse) o;
        return Objects.equals(dvlaTransactionId, that.dvlaTransactionId) &&
                Objects.equals(dvlaOriginatingSystem, that.dvlaOriginatingSystem) &&
                Objects.equals(purchaseDescription, that.purchaseDescription) &&
                Objects.equals(requestedPaymentAmount, that.requestedPaymentAmount) &&
                Objects.equals(dvlaAdditionalInformation, that.dvlaAdditionalInformation) &&
                Objects.equals(channel, that.channel) &&
                Objects.equals(agentId, that.agentId) &&
                Objects.equals(transactionType, that.transactionType) &&
                Objects.equals(paymentReference, that.paymentReference) &&
                Objects.equals(callReference, that.callReference) &&
                Objects.equals(paymentStatus, that.paymentStatus) &&
                Objects.equals(paymentType, that.paymentType) &&
                Objects.equals(maskedPAN, that.maskedPAN) &&
                Objects.equals(expiryDate, that.expiryDate) &&
                Objects.equals(transactionAmount, that.transactionAmount) &&
                Objects.equals(surcharge, that.surcharge) &&
                Objects.equals(totalAmountPaid, that.totalAmountPaid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dvlaTransactionId, dvlaOriginatingSystem, purchaseDescription, requestedPaymentAmount, dvlaAdditionalInformation,
                channel, agentId, transactionType, paymentReference, callReference, paymentStatus, paymentType, maskedPAN, expiryDate,
                transactionAmount, surcharge, totalAmountPaid);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("dvlaTransactionId", dvlaTransactionId)
                .add("dvlaOriginatingSystem", dvlaOriginatingSystem)
                .add("purchaseDescription", purchaseDescription)
                .add("requestedPaymentAmount", requestedPaymentAmount)
                .add("dvlaAdditionalInformation", dvlaAdditionalInformation)
                .add("channel", channel)
                .add("agentId", agentId)
                .add("transactionType", transactionType)
                .add("paymentReference", paymentReference)
                .add("callReference", callReference)
                .add("paymentStatus", paymentStatus)
                .add("paymentType", paymentType)
                .add("maskedPAN", maskedPAN)
                .add("expiryDate", expiryDate)
                .add("transactionAmount", transactionAmount)
                .add("surcharge", surcharge)
                .add("totalAmountPaid", totalAmountPaid)
                .toString();
    }
}
