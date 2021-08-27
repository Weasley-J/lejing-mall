package cn.alphahub.mall.schedule.quartz.enums;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TriggerStateEnumTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getEnum() {
        TriggerStateEnum triggerStateEnum = TriggerStateEnum.getEnum("BLOCKED");
        String name = triggerStateEnum.getName();
        System.out.println("name = " + name);
    }
}
