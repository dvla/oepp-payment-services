package uk.gov.dvla.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import uk.gov.dvla.core.error.ApplicationError;

import java.util.Objects;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PaymentServiceErrors implements ApplicationError {
    PAYMENT_NOT_FOUND("PE-404-01", "Payment not found"),
    PAYMENT_NOT_AUTHORISED("PE-412-01", "Payment not authorised"),
    GENERAL_ERROR("PE-500-01", "General error");

    private String code;
    private String message;

    PaymentServiceErrors(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @JsonCreator
    @SuppressWarnings("unused")
    public static PaymentServiceErrors fromJson(JsonNode json) {
        for (PaymentServiceErrors value : values()) {
            if (Objects.equals(value.getCode(), json.get("code").textValue())
                    && Objects.equals(value.getMessage(), json.get("message").textValue())) {
                return value;
            }
        }
        return null;
    }
}
