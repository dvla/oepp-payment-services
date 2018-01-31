package uk.gov.dvla.oepp.domain.payment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.net.URL;

/**
 * Represents an initiate offence payment request.
 * The nested {@link Builder} should be used to create this class.
 * <p>
 * Example JSON
 * <pre>
 *   {
 *     "transactionID": "OEPP-123456789",
 *     "paymentAmount": 60.00,
 *     "language": "EN",
 *     "postAuthorizeCallbackURL": "http://localhost/payment/finish"
 *   }
 * </pre>
 */
public class InitiateOffencePaymentRequest {

    @ApiModelProperty(required = true, example = "OEPP-123456789")
    @NotNull
    @Size(max = 14)
    private final String transactionID;
    @ApiModelProperty(required = true, example = "60.00")
    @NotNull
    @DecimalMin("00.01")
    @DecimalMax("999999.99")
    private final BigDecimal paymentAmount;
    @ApiModelProperty(required = true, allowableValues = "EN,CY", example = "EN")
    @NotNull
    @Pattern(regexp = "EN|CY")
    private final String language;
    @ApiModelProperty(required = true, example = "http://localhost/payment/finish")
    @NotNull
    private final URL postAuthorizeCallbackURL;

    @JsonCreator
    private InitiateOffencePaymentRequest(@JsonProperty("transactionID") String transactionID,
                                          @JsonProperty("paymentAmount") BigDecimal paymentAmount,
                                          @JsonProperty("language") String language,
                                          @JsonProperty("postAuthorizeCallbackURL") URL postAuthorizeCallbackURL) {
        this.transactionID = transactionID;
        this.paymentAmount = paymentAmount;
        this.language = language;
        this.postAuthorizeCallbackURL = postAuthorizeCallbackURL;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public String getLanguage() {
        return language;
    }

    public URL getPostAuthorizeCallbackURL() {
        return postAuthorizeCallbackURL;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("transactionID", transactionID)
                .add("paymentAmount", paymentAmount)
                .add("language", language)
                .add("postAuthorizeCallbackURL", postAuthorizeCallbackURL)
                .toString();
    }

    public static class Builder {
        private String transactionID;
        private BigDecimal paymentAmount;
        private String language;
        private URL postAuthorizeCallbackURL;

        public Builder setTransactionID(String transactionID) {
            this.transactionID = transactionID;
            return this;
        }

        public Builder setPaymentAmount(BigDecimal paymentAmount) {
            this.paymentAmount = paymentAmount;
            return this;
        }

        public Builder setLanguage(String language) {
            this.language = language;
            return this;
        }

        public Builder setPostAuthorizeCallbackURL(URL postAuthorizeCallbackURL) {
            this.postAuthorizeCallbackURL = postAuthorizeCallbackURL;
            return this;
        }

        public InitiateOffencePaymentRequest create() {
            return new InitiateOffencePaymentRequest(transactionID, paymentAmount, language, postAuthorizeCallbackURL);
        }

    }
}
