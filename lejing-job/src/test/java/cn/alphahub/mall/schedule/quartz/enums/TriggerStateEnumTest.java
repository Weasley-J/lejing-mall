package cn.alphahub.mall.schedule.quartz.enums;

import cn.alphahub.mall.schedule.core.enums.TriggerStateEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
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
