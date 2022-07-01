package cn.alphahub.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Long型id序列化器
 * <p>
 * 将返回给前端的Long型id转为String，防止精度丢失，添加到实体类的主键id上，
 * jackson在序列化是会自动序列化id为string类型
 * </p>
 * <b>用法示例</b>
 * <pre>
 *    {@code @Data}
 *     public class SkuModel implements Serializable {
 *         private static final long serialVersionUID = 1L;
 *
 *        {@code @JsonSerialize(using = IdSerializer.class)}
 *         private Long skuId;
 *     }
 * </pre>
 *
 * @author liuwenjing
 * @date 2021年2月28日
 * @see JsonSerializer
 * @see com.fasterxml.jackson.databind.ser.std.ToStringSerializer
 */
public class IdSerializer extends JsonSerializer<Long> {

    @Override
    public void serialize(Long id, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(id.toString());
    }
}
