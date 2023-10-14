package cn.alphahub.mall.common.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateTime Serializer
 *
 * @author weasley
 * @version 1.0.0
 */
@JsonComponent
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(DateTimeFormatter.ofPattern(PATTERN).format(localDateTime));
    }
}
