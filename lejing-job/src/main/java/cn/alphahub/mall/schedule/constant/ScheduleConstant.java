package cn.alphahub.mall.schedule.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * 任务调度常量
 *
 * @author lwj
 * @version 1.0
 * @date 2021/08/29
 */
public class ScheduleConstant {
    /**
     * 用来获取JobDataMap参数值的KEY
     */
    public static final String JOB_DATA_PARAM_KEY = "JOB_PARAM";
    /**
     * 失火策略 - 立即执行
     */
    public static final int MISFIRE_HANDLING_IGNORE_MISFIRES = 1;
    /**
     * 失火策略 - 执行一次
     */
    public static final int MISFIRE_HANDLING_FIRE_AND_PROCEED = 2;
    /**
     * 失火策略 - 放弃执行
     */
    public static final int MISFIRE_HANDLING_DO_NOTHING = 3;

    private ScheduleConstant() {
    }

    /**
     * 任务状态枚举
     * 任务状态( 1 正常, 0 暂停)
     */
    @Getter
    @AllArgsConstructor
    public enum JobStatusEnum {
        /**
         * 0  暂停
         */
        PAUSED(0, "暂停"),
        NORMAL(1, "正常"),
        ;

        private final int code;
        private final String name;

        /**
         * 获取枚举名称
         *
         * @param code 枚举值
         * @return 枚举名
         */
        public static String getName(int code) {
            return Stream.of(JobStatusEnum.values())
                    .filter(anEnum -> anEnum.getCode() == code)
                    .map(JobStatusEnum::getName).findFirst().orElse("");

        }

        /**
         * 获取枚举类型
         *
         * @param name JobStatusEnum.XX.toString()
         * @return JobStatusEnum
         */
        public static JobStatusEnum getEnum(String name) {
            return JobStatusEnum.valueOf(name);
        }
    }
}
