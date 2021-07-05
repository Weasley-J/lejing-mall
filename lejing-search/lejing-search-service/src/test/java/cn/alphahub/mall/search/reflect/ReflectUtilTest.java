package cn.alphahub.mall.search.reflect;

import cn.alphahub.common.reflect.ReflectUtil;
import cn.alphahub.mall.search.domain.SkuModel;
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
    void testGetName1() {
        String property = ReflectUtil.property(Person::getName);
        System.out.println("java property = " + property);
        property = ReflectUtil.property(Person::getAge);
        System.out.println("java property = " + property);
        property = ReflectUtil.property(Person::getHobby);
        System.out.println("java property = " + property);
        property = ReflectUtil.property(Person::getIsEnable);
        System.out.println("java property = " + property);
        property = ReflectUtil.property(Person::getIs);
        System.out.println("java property = " + property);
    }

    @Test
    void testGetName2() {
        System.out.println(ReflectUtil.property(SkuModel::getHasStock));
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
