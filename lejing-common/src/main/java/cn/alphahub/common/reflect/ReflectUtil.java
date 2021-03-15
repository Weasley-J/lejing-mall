package cn.alphahub.common.reflect;

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
     * @param propLambdaExpansion 属性名称lambda表达式
     * @param <T>                 Java Bean的类型
     * @return 属性名称
     */
    public static <T> String property(@NotNull FieldFunction<T, Object> propLambdaExpansion) {
        try {
            Method writeReplace = propLambdaExpansion.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(true);
            Object invokeObj = writeReplace.invoke(propLambdaExpansion);
            SerializedLambda serializedLambda = (SerializedLambda) invokeObj;
            // 传入方法名
            String implMethodName = serializedLambda.getImplMethodName();
            // 传入方法类
            String lambdaImplClass = serializedLambda.getImplClass();
            return method2property(Objects.requireNonNull(implMethodName));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("获取Java Bean的属性名失败, 异常信息: {}\n", e.getMessage(), e);
            throw new RuntimeException();
        }
    }

    /**
     * Java方法名转属性名
     *
     * @param methodName Java Bean某个属性的getter或者setter方法
     * @return 返回属性值名称
     */
    public static String method2property(@NotNull String methodName) {
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
}
