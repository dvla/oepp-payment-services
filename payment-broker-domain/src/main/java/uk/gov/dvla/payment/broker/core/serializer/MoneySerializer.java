package uk.gov.dvla.payment.broker.core.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Serializes a numeric {@link BigDecimal} during JSON marshaling with 2 decimals places even when they are zero (e.g. "10.00",
 * "10.10", "10.01").
 */
public class MoneySerializer extends JsonSerializer<BigDecimal> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MoneySerializer.class);

    @Override
    public void serialize(final BigDecimal value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException,
            JsonProcessingException {
        LOGGER.debug("Serialize [{}].", value);
        // put your desired money style here
        if (value == null) {
            jgen.writeString("null");
        } else {
            jgen.writeString(value.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        }
    }
}