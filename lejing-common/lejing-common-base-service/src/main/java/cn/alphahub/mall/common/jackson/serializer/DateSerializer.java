package cn.alphahub.mall.common.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Jackson 序列化器，以处理 Date 格式的日期字符串
 *
 * @author weasley
 * @version 1.0.0
 */
@JsonComponent
public class DateSerializer extends JsonSerializer<Date> {
    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final Logger logger = LoggerFactory.getLogger(DateSerializer.class);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN);

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (null != date) {
            jsonGenerator.writeString(dateFormat.format(date));
        }
    }
}
