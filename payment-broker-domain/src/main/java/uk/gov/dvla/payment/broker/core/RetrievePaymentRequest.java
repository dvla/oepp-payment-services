package uk.gov.dvla.payment.broker.core;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public class RetrievePaymentRequest {

    private final Long paymentId;
    private final String dvlaRequestingSystem;
    private final String channel;

    private RetrievePaymentRequest(Long paymentId, String dvlaRequestingSystem, String channel) {
        this.paymentId = paymentId;
        this.channel = channel;
        this.dvlaRequestingSystem = dvlaRequestingSystem;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public String getChannel() {
        return channel;
    }

    public String getDvlaRequestingSystem() {
        return dvlaRequestingSystem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RetrievePaymentRequest that = (RetrievePaymentRequest) o;
        return Objects.equals(paymentId, that.paymentId) &&
                Objects.equals(dvlaRequestingSystem, that.dvlaRequestingSystem) &&
                Objects.equals(channel, that.channel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, dvlaRequestingSystem, channel);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("paymentId", paymentId)
                .add("dvlaRequestingSystem", dvlaRequestingSystem)
                .add("channel", channel)
                .toString();
    }

    public static class Builder {
        private Long paymentId;
        private String dvlaRequestingSystem;
        private String channel;

        public Builder setPaymentId(Long paymentId) {
            this.paymentId = paymentId;
            return this;
        }

        public Builder setDvlaRequestingSystem(String dvlaRequestingSystem) {
            this.dvlaRequestingSystem = dvlaRequestingSystem;
            return this;
        }

        public Builder setChannel(String channel) {
            this.channel = channel;
            return this;
        }

        public RetrievePaymentRequest create() {
            return new RetrievePaymentRequest(paymentId, dvlaRequestingSystem, channel);
        }
    }

}
