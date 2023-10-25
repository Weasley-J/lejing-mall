package cn.alphahub.mall.common.config;

import cn.alphahub.mall.common.jackson.JacksonInitializer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * Feign Client Decoder Configuration
 *
 * @author weasley
 * @version 1.0.0
 * @apiNote 暂时不做全局自动装配，按需指定为 {@code org.springframework.cloud.openfeign.FeignClient#configuration()} 的属性
 */
@Slf4j
public class FeignClientsDecoderConfig {

    @Bean
    @ConditionalOnMissingBean
    public Logger.Level logger() {
        return Logger.Level.FULL;
    }

    @Bean
    public Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new FormEncoder(new SpringEncoder(messageConverters));
    }

    @Bean
    @ConditionalOnMissingBean
    public Decoder feignDecoder() {
        return (response, type) -> {
            if (response.status() == 404) return null;
            try {
                ObjectMapper mapper = JacksonInitializer.OBJECT_MAPPER;
                String readerToString = convertReaderToString(response.body().asReader(StandardCharsets.UTF_8));
                Object value = mapper.readValue(readerToString, new TypeReference<>() {
                    @Override
                    public Type getType() {
                        return type;
                    }
                });
                log.info("Feign RPC解析结果: {}", mapper.writeValueAsString(value));
                return value;
            } catch (IOException e) {
                throw new FeignClientDecoderException("Feign RPC 请求解析响应结果失败", e);
            }
        };
    }

    private String convertReaderToString(Reader reader) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            StringWriter stringWriter = new StringWriter();
            char[] buffer = new char[1024];
            int charsRead;
            while ((charsRead = bufferedReader.read(buffer, 0, buffer.length)) != -1) {
                stringWriter.write(buffer, 0, charsRead);
            }
            return stringWriter.toString();
        }
    }

    /**
     * Feign Client Decoder Exception
     */
    public static class FeignClientDecoderException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        /**
         * 异常消息
         */
        private final String message;

        public FeignClientDecoderException(String msg, IOException e) {
            super(msg);
            this.message = msg;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }
}
