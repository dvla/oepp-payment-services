package uk.gov.dvla.payment.broker.core;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;
import java.util.Objects;

public class InitiatePaymentRequest {

    // transaction metadata
    private final String dvlaTransactionId;
    private final String dvlaOriginatingSystem;
    private final String transactionType;
    private final String channel;
    // payment details
    private final BigDecimal paymentAmount;
    private final String purchaseDescription;
    // web tier details
    private final String language;
    private final String resultPageUrl;

    private InitiatePaymentRequest(String dvlaTransactionId, String dvlaOriginatingSystem, String transactionType, String channel, BigDecimal paymentAmount, String purchaseDescription, String language, String resultPageUrl) {
        this.dvlaTransactionId = dvlaTransactionId;
        this.dvlaOriginatingSystem = dvlaOriginatingSystem;
        this.channel = channel;
        this.language = language;
        this.paymentAmount = paymentAmount;
        this.purchaseDescription = purchaseDescription;
        this.transactionType = transactionType;
        this.resultPageUrl = resultPageUrl;
    }

    public String getDvlaTransactionId() {
        return dvlaTransactionId;
    }

    public String getDvlaOriginatingSystem() {
        return dvlaOriginatingSystem;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getChannel() {
        return channel;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public String getPurchaseDescription() {
        return purchaseDescription;
    }

    public String getLanguage() {
        return language;
    }

    public String getResultPageUrl() {
        return resultPageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InitiatePaymentRequest that = (InitiatePaymentRequest) o;
        return Objects.equals(dvlaTransactionId, that.dvlaTransactionId) &&
                Objects.equals(dvlaOriginatingSystem, that.dvlaOriginatingSystem) &&
                Objects.equals(transactionType, that.transactionType) &&
                Objects.equals(channel, that.channel) &&
                Objects.equals(paymentAmount, that.paymentAmount) &&
                Objects.equals(purchaseDescription, that.purchaseDescription) &&
                Objects.equals(language, that.language) &&
                Objects.equals(resultPageUrl, that.resultPageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dvlaTransactionId, dvlaOriginatingSystem, transactionType, channel, paymentAmount, purchaseDescription, language, resultPageUrl);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("dvlaTransactionId", dvlaTransactionId)
                .add("dvlaOriginatingSystem", dvlaOriginatingSystem)
                .add("transactionType", transactionType)
                .add("channel", channel)
                .add("paymentAmount", paymentAmount)
                .add("purchaseDescription", purchaseDescription)
                .add("language", language)
                .add("resultPageUrl", resultPageUrl)
                .toString();
    }

    public static class Builder {
        // transaction metadata
        private String dvlaTransactionId;
        private String dvlaOriginatingSystem;
        private String transactionType;
        private String channel;
        // payment details
        private BigDecimal paymentAmount;
        private String purchaseDescription;
        // web tier details
        private String language;
        private String resultPageUrl;

        public Builder setDvlaTransactionId(String dvlaTransactionId) {
            this.dvlaTransactionId = dvlaTransactionId;
            return this;
        }

        public Builder setDvlaOriginatingSystem(String dvlaOriginatingSystem) {
            this.dvlaOriginatingSystem = dvlaOriginatingSystem;
            return this;
        }

        public Builder setTransactionType(String transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public Builder setChannel(String channel) {
            this.channel = channel;
            return this;
        }

        public Builder setPaymentAmount(BigDecimal paymentAmount) {
            this.paymentAmount = paymentAmount;
            return this;
        }

        public Builder setPurchaseDescription(String purchaseDescription) {
            this.purchaseDescription = purchaseDescription;
            return this;
        }

        public Builder setLanguage(String language) {
            this.language = language;
            return this;
        }

        public Builder setResultPageUrl(String resultPageUrl) {
            this.resultPageUrl = resultPageUrl;
            return this;
        }

        public InitiatePaymentRequest create() {
            return new InitiatePaymentRequest(dvlaTransactionId, dvlaOriginatingSystem, transactionType, channel, paymentAmount, purchaseDescription, language, resultPageUrl);
        }

    }
}
