package cn.alphahub.mall.common.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Jackson 反序列化器，以处理不同格式的日期字符串
 *
 * @author weasley
 * @version 1.0.0
 */
@JsonComponent
public class DateDeserializer extends JsonDeserializer<Date> {
    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final Logger logger = LoggerFactory.getLogger(DateDeserializer.class);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN);

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (!StringUtils.hasText(jsonParser.getValueAsString())) {
            return null;
        }
        String dateAsString = jsonParser.getText();
        try {
            return dateFormat.parse(dateAsString);
        } catch (ParseException e) {
            logger.error("Date反序列化出错, text: {}", dateAsString);
            return null;
        }
    }
}
