package cn.alphahub.mall.schedule.quartz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * quartz任务调度状态
 *
 * @author lwj
 * @version 1.0
 * @date 2021/08/27
 */
@Getter
@AllArgsConstructor
public enum TriggerStateEnum {
    /**
     * NONE 不存在
     */
    NONE("NONE", "不存在"),
    /**
     * NORMAL 正常
     */
    NORMAL("NORMAL", "正常"),
    /**
     * PAUSED 暂停
     */
    PAUSED("PAUSED", "暂停"),
    /**
     * COMPLETE 完成
     */
    COMPLETE("COMPLETE", "完成"),
    /**
     * ERROR 出错
     */
    ERROR("ERROR", "出错"),
    /**
     * BLOCKED 阻塞
     */
    BLOCKED("BLOCKED", "阻塞"),
    ;


    /**
     * 枚举值
     */
    private final String code;
    /**
     * 枚举名
     */
    private final String name;

    /**
     * 获取quartz任务调度状态名
     *
     * @param code 枚举值
     * @return 枚举名
     */
    public static String getName(String code) {
        return Stream.of(TriggerStateEnum.values())
                .filter(anEnum -> anEnum.getCode().equals(code))
                .map(TriggerStateEnum::getName).findFirst().orElse(null);
    }
}
