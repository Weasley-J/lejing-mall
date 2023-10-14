package cn.alphahub.mall.common.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * LocalDate Serializer
 *
 * @author weasley
 * @version 1.0.0
 */
@JsonComponent
public class LocalDateSerializer extends JsonSerializer<LocalDate> {
    public static final String PATTERN = "yyyy-MM-dd";

    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (null != localDate) {
            jsonGenerator.writeString(DateTimeFormatter.ofPattern(PATTERN).format(localDate));
        }
    }
}
