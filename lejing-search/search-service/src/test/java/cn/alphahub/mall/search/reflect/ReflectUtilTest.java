package cn.alphahub.mall.search.reflect;

import cn.alphahub.common.reflect.ReflectUtil;
import lombok.Data;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReflectUtilTest {

    @BeforeEach
    void setUp() {
        System.out.println("----------------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("----------------------");
    }

    @Test
    void testGetName() {
        String property = ReflectUtil.propertyName(Person::getName);
        System.out.println("java property = " + property);
        property = ReflectUtil.propertyName(Person::getAge);
        System.out.println("java property = " + property);
        property = ReflectUtil.propertyName(Person::getHobby);
        System.out.println("java property = " + property);
        property = ReflectUtil.propertyName(Person::getIsEnable);
        System.out.println("java property = " + property);
        property = ReflectUtil.propertyName(Person::getIs);
        System.out.println("java property = " + property);
    }

    @Data
    private static class Person {
        private String name;
        private Integer age;
        private String hobby;
        private Boolean isEnable;
        private Boolean is;
    }
}
