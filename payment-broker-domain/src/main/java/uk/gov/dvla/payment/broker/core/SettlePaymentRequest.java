package uk.gov.dvla.payment.broker.core;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public class SettlePaymentRequest {

    private final Long paymentId;
    private final String channel;
    private final String dvlaRequestingSystem;

    private SettlePaymentRequest(Long paymentId, String channel, String dvlaRequestingSystem) {
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
        SettlePaymentRequest that = (SettlePaymentRequest) o;
        return Objects.equals(paymentId, that.paymentId) &&
                Objects.equals(channel, that.channel) &&
                Objects.equals(dvlaRequestingSystem, that.dvlaRequestingSystem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, channel, dvlaRequestingSystem);
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("paymentId", paymentId)
                .add("channel", channel)
                .add("dvlaRequestingSystem", dvlaRequestingSystem)
                .toString();
    }

    public static class Builder {
        private Long paymentId;
        private String channel;
        private String dvlaRequestingSystem;

        public Builder setPaymentId(Long paymentId) {
            this.paymentId = paymentId;
            return this;
        }

        public Builder setChannel(String channel) {
            this.channel = channel;
            return this;
        }

        public Builder setDvlaRequestingSystem(String dvlaRequestingSystem) {
            this.dvlaRequestingSystem = dvlaRequestingSystem;
            return this;
        }

        public SettlePaymentRequest create() {
            return new SettlePaymentRequest(paymentId, channel, dvlaRequestingSystem);
        }
    }

}
