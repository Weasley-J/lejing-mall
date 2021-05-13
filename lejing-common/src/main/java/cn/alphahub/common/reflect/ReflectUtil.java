package cn.alphahub.common.reflect;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Objects;

/**
 * <b>Java反射工具类</b>
 * <p>通过Java实体类的get方法名获取对应的属性名</p>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/13
 */
public class ReflectUtil {
    private final static Logger log = LoggerFactory.getLogger(ReflectUtil.class);
    private final static String IS = "is";
    private final static String GET = "get";
    private final static String SET = "set";

    /**
     * <p>通过Java实体类某个属性的getter方法获取该属性的属性名称</p>
     *
     * <b>Java实体:</b>
     * <pre>
     * public class Person {
     *     private String name;
     *     private Integer age;
     *     private String hobby;
     *     private Boolean is;
     *     private Boolean isEnable;
     *     // 省略getter, setter
     * }
     * </pre>
     * <b>使用实例:</b>
     * <pre>
     * ReflectUtil.property(Person::getName)      = name
     * ReflectUtil.property(Person::getAge)       = age
     * ReflectUtil.property(Person::getHobby)     = hobby
     * ReflectUtil.property(Person::getIs)        = is
     * ReflectUtil.property(Person::getIsEnable)  = isEnable
     * </pre>
     *
     * @param domainLambdaExpression 属性名称lambda表达式
     * @param <T>                    Java Bean的类型
     * @return 属性名称
     */
    public static <T> String property(@NotNull FieldFunction<T, Object> domainLambdaExpression) {
        try {
            Method writeReplace = domainLambdaExpression.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(true);
            Object invokeObj = writeReplace.invoke(domainLambdaExpression);
            SerializedLambda serializedLambda = (SerializedLambda) invokeObj;
            // 传入方法名
            String implMethodName = serializedLambda.getImplMethodName();
            // 传入方法类
            String lambdaImplClass = serializedLambda.getImplClass();
            return methodToProperty(Objects.requireNonNull(implMethodName));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("获取Java Bean属性名失败, 异常信息: {}\n", e.getMessage(), e);
            throw new RuntimeException("获取Java Bean属性名失败");
        }
    }

    /**
     * <b>将实体类里指定字段属性由"驼峰"命名风格转为"下划线"命名风格</b>
     * <p>该方法比写死在 @JsonProperty 里面的灵活</p>
     * <b>Java实体:</b>
     * <pre>
     * public static class SocialUser implements Serializable {
     *     private static final long serialVersionUID = 1L;
     *
     *     private String accessToken;
     *     private Long expiresIn;
     *     private String uid;
     *     private String isRealName;
     *     // 省略getter, setter, 构造方法, 建议使用lombok
     * }
     * </pre>
     * <b>使用示例:</b>
     * <pre>
     * ReflectUtil.propertyToUnderline(SocialUser::getAccessToken) = access_token
     * ReflectUtil.propertyToUnderline(SocialUser::getExpiresIn)   = expires_in
     * ReflectUtil.propertyToUnderline(SocialUser::getUid)         = uid
     * ReflectUtil.propertyToUnderline(SocialUser::getIsRealName)  = is_real_name
     * </pre>
     *
     * @param domainLambdaExpression 属性名称lambda表达式
     * @param <T>                    Java Bean的类型
     * @return 实体类属性名的下划线风格
     */
    public static <T> String propertyToUnderline(@NotNull FieldFunction<T, Object> domainLambdaExpression) {
        String property = property(domainLambdaExpression);
        return translate(property);
    }

    /**
     * Java方法名转属性名
     *
     * @param methodName Java Bean某个属性的getter或者setter方法
     * @return 返回属性值名称
     */
    public static String methodToProperty(@NotNull String methodName) {
        if (methodName.startsWith(IS)) {
            methodName = methodName.substring(2);
        } else {
            if (methodName.startsWith(GET) || methodName.startsWith(SET)) {
                methodName = methodName.substring(3);
            } else {
                throw new RuntimeException("Error parsing property name '" + methodName + "'.  Didn't start with 'is', 'get' or 'set'.");
            }
        }
        if (methodName.length() == 1 || (methodName.length() > 1 && !Character.isUpperCase(methodName.charAt(1)))) {
            methodName = methodName.substring(0, 1).toLowerCase(Locale.ENGLISH) + methodName.substring(1);
        }
        return methodName;
    }

    /**
     * <p>属性名转下划线</p>
     * A {@link PropertyNamingStrategy} that translates typical camel case Java
     * property names to lower case JSON element names, separated by
     * underscores.  This implementation is somewhat lenient, in that it
     * provides some additional translations beyond strictly translating from
     * camel case only.  In particular, the following translations are applied
     * by this PropertyNamingStrategy.
     *
     * <ul><li>Every upper case letter in the Java property name is translated
     * into two characters, an underscore and the lower case equivalent of the
     * target character, with three exceptions.
     * <ol><li>For contiguous sequences of upper case letters, characters after
     * the first character are replaced only by their lower case equivalent,
     * and are not preceded by an underscore.
     * <ul><li>This provides for reasonable translations of upper case acronyms,
     * e.g., &quot;theWWW&quot; is translated to &quot;the_www&quot;.</li></ul></li>
     * <li>An upper case character in the first position of the Java property
     * name is not preceded by an underscore character, and is translated only
     * to its lower case equivalent.
     * <ul><li>For example, &quot;Results&quot; is translated to &quot;results&quot;,
     * and not to &quot;_results&quot;.</li></ul></li>
     * <li>An upper case character in the Java property name that is already
     * preceded by an underscore character is translated only to its lower case
     * equivalent, and is not preceded by an additional underscore.
     * <ul><li>For example, &quot;user_Name&quot; is translated to
     * &quot;user_name&quot;, and not to &quot;user__name&quot; (with two
     * underscore characters).</li></ul></li></ol></li>
     * <li>If the Java property name starts with an underscore, then that
     * underscore is not included in the translated name, unless the Java
     * property name is just one character in length, i.e., it is the
     * underscore character.  This applies only to the first character of the
     * Java property name.</li></ul>
     * <p>
     * These rules result in the following additional example translations from
     * Java property names to JSON element names.
     * <ul><li>&quot;userName&quot; is translated to &quot;user_name&quot;</li>
     * <li>&quot;UserName&quot; is translated to &quot;user_name&quot;</li>
     * <li>&quot;USER_NAME&quot; is translated to &quot;user_name&quot;</li>
     * <li>&quot;user_name&quot; is translated to &quot;user_name&quot; (unchanged)</li>
     * <li>&quot;user&quot; is translated to &quot;user&quot; (unchanged)</li>
     * <li>&quot;User&quot; is translated to &quot;user&quot;</li>
     * <li>&quot;USER&quot; is translated to &quot;user&quot;</li>
     * <li>&quot;_user&quot; is translated to &quot;user&quot;</li>
     * <li>&quot;_User&quot; is translated to &quot;user&quot;</li>
     * <li>&quot;__user&quot; is translated to &quot;_user&quot;
     * (the first of two underscores was removed)</li>
     * <li>&quot;user__name&quot; is translated to &quot;user__name&quot;
     * (unchanged, with two underscores)</li></ul>
     *
     * @since 2.7 (was previously called }
     */
    public static String translate(String input) {
        // garbage in, garbage out
        if (input == null) {
            return null;
        }
        int length = input.length();
        StringBuilder result = new StringBuilder(length * 2);
        int resultLength = 0;
        boolean wasPrevTranslated = false;
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            // skip first starting underscore
            if (i > 0 || c != '_') {
                if (Character.isUpperCase(c)) {
                    if (!wasPrevTranslated && resultLength > 0 && result.charAt(resultLength - 1) != '_') {
                        result.append('_');
                        resultLength++;
                    }
                    c = Character.toLowerCase(c);
                    wasPrevTranslated = true;
                } else {
                    wasPrevTranslated = false;
                }
                result.append(c);
                resultLength++;
            }
        }
        return resultLength > 0 ? result.toString() : input;
    }
}
