package cn.alphahub.mall.common.jackson;

import cn.alphahub.mall.common.jackson.deserializer.DateDeserializer;
import cn.alphahub.mall.common.jackson.serializer.DateSerializer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Jackson Initializer
 *
 * @author lwj
 * @version 1.0.0
 */
public final class JacksonInitializer {
    /**
     * Object writer
     */
    public static final ObjectWriter OBJECT_WRITER;
    /**
     * Object mapper
     */
    public static final ObjectMapper OBJECT_MAPPER;
    /**
     * HH:mm:ss
     */
    private static final String LOCAL_TIME_PATTERN = "HH:mm:ss";
    /**
     * yyyy-MM-dd
     */
    private static final String LOCAL_DATE_PATTERN = "yyyy-MM-dd";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    private static final String LOCAL_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    static {
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(Date.class, new DateSerializer());
        timeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(LOCAL_TIME_PATTERN)));
        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(LOCAL_DATE_PATTERN)));
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN)));
        timeModule.addDeserializer(Date.class, new DateDeserializer());
        timeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(LOCAL_TIME_PATTERN)));
        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(LOCAL_DATE_PATTERN)));
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN)));
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        OBJECT_MAPPER.registerModules(timeModule).findAndRegisterModules();
        OBJECT_WRITER = OBJECT_MAPPER.writerWithDefaultPrettyPrinter();
    }

    private JacksonInitializer() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * The Jackson original property naming strategy
     *
     * @author weasley
     * @since 1.1.0
     */
    public static class OriginalPropertyNamingStrategy extends PropertyNamingStrategies.NamingBase {

        public static List<AnnotatedField> getAnnotatedFields(AnnotatedMethod method) {
            @SuppressWarnings("deprecation") Iterable<AnnotatedField> fields = ((AnnotatedClass) method.getTypeContext()).fields();
            return StreamSupport.stream(fields.spliterator(), false).collect(Collectors.toList());
        }

        @Override
        public String translate(String propertyName) {
            return propertyName;
        }

        @Override
        public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
            List<AnnotatedField> annotatedFields = getAnnotatedFields(method);
            for (AnnotatedField annotatedField : annotatedFields) {
                if (defaultName.equalsIgnoreCase(annotatedField.getName())) {
                    return annotatedField.getName();
                }
            }
            return super.nameForGetterMethod(config, method, defaultName);
        }

        @Override
        public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
            return super.nameForField(config, field, defaultName);
        }

        @Override
        public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
            List<AnnotatedField> annotatedFields = getAnnotatedFields(method);
            for (AnnotatedField annotatedField : annotatedFields) {
                if (defaultName.equalsIgnoreCase(annotatedField.getName())) {
                    return annotatedField.getName();
                }
            }
            return super.nameForSetterMethod(config, method, defaultName);
        }

        @Override
        public String nameForConstructorParameter(MapperConfig<?> config, AnnotatedParameter ctorParam, String defaultName) {
            return super.nameForConstructorParameter(config, ctorParam, defaultName);
        }
    }
}
