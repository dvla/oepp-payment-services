package uk.gov.dvla.oepp.domain.payment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Represents a fulfill offence payment request.
 * The nested {@link Builder} should be used to create this class.
 * <p>
 * Example JSON
 * <pre>
 *   {
 *     "caseNumber": "12345678",
 *     "paidPenaltyAmount": 40.00,
 *     "paidArrearsAmount": 21.00
 *   }
 * </pre>
 */
public class FulfillOffencePaymentRequest {

    @ApiModelProperty(required = true, example = "12345678")
    @NotNull
    private final Long caseNumber;
    @ApiModelProperty(required = true, example = "40.00")
    @NotNull
    @Min(0)
    private final BigDecimal paidPenaltyAmount;
    @ApiModelProperty(required = true, example = "21.00")
    @NotNull
    @Min(0)
    private final BigDecimal paidArrearsAmount;

    @JsonCreator
    private FulfillOffencePaymentRequest(@JsonProperty("caseNumber") Long caseNumber,
                                         @JsonProperty("paidPenaltyAmount") BigDecimal paidPenaltyAmount,
                                         @JsonProperty("paidArrearsAmount") BigDecimal paidArrearsAmount) {
        this.caseNumber = caseNumber;
        this.paidPenaltyAmount = paidPenaltyAmount;
        this.paidArrearsAmount = paidArrearsAmount;
    }

    public Long getCaseNumber() {
        return caseNumber;
    }

    public BigDecimal getPaidPenaltyAmount() {
        return paidPenaltyAmount;
    }

    public BigDecimal getPaidArrearsAmount() {
        return paidArrearsAmount;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("caseNumber", caseNumber)
                .add("paidPenaltyAmount", paidPenaltyAmount)
                .add("paidArrearsAmount", paidArrearsAmount)
                .toString();
    }

    public static class Builder {
        private Long caseNumber;
        private BigDecimal paidPenaltyAmount;
        private BigDecimal paidArrearsAmount;

        public Builder setCaseNumber(Long caseNumber) {
            this.caseNumber = caseNumber;
            return this;
        }

        public Builder setPaidPenaltyAmount(BigDecimal paidPenaltyAmount) {
            this.paidPenaltyAmount = paidPenaltyAmount;
            return this;
        }

        public Builder setPaidArrearsAmount(BigDecimal paidArrearsAmount) {
            this.paidArrearsAmount = paidArrearsAmount;
            return this;
        }

        public FulfillOffencePaymentRequest create() {
            return new FulfillOffencePaymentRequest(caseNumber, paidPenaltyAmount, paidArrearsAmount);
        }

    }
}
