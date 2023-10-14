package cn.alphahub.mall.common.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalTime Serializer
 *
 * @author weasley
 * @version 1.0.0
 */
@JsonComponent
public class LocalTimeSerializer extends JsonSerializer<LocalTime> {
    public static final String PATTERN = "HH:mm:ss";

    @Override
    public void serialize(LocalTime localTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (null != localTime) {
            jsonGenerator.writeString(DateTimeFormatter.ofPattern(PATTERN).format(localTime));
        }
    }
}
