package cn.alphahub.mall.common.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateTime Serializer
 *
 * @author lwj
 * @version 1.0
 */
@JsonComponent
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (!StringUtils.hasText(jsonParser.getValueAsString())) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        return LocalDateTime.parse(jsonParser.getValueAsString(), formatter);
    }
}
