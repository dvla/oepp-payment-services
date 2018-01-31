package uk.gov.dvla.payment.broker.core;

import com.wordnik.swagger.annotations.ApiModel;
import uk.gov.dvla.payment.broker.core.validation.MandatoryFieldLengthCheck;

import javax.validation.constraints.NotNull;

@ApiModel(value = "Placeholder for additional information")
public class AdditionalInformation {

    @NotNull(message = "Mandatory input field missing")
    @MandatoryFieldLengthCheck(value = 32)
    private String     key;

    @NotNull(message = "Mandatory input field missing")
    @MandatoryFieldLengthCheck(value = 32)
    private String     value;

    public AdditionalInformation() {
    }

    public AdditionalInformation(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AdditionalInformation other = (AdditionalInformation) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AdditionalInformation [key=" + key + ", value=" + value + "]";
    }

}