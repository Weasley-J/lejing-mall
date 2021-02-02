package cn.alphahub.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * 返回数据给前端时将long型ID转为String
 * <li>
 * 用法：@JsonSerialize(using = IdSerializer.class)
 * private Long id;
 * 添加到实体类的主键id上，jackson在序列化是会自动序列化id为string类型
 * </li>
 *
 * @author liuwenjing
 */
public class IdSerializer extends JsonSerializer<Long> {

    @Override
    public void serialize(Long id, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(id.toString());
    }
}
