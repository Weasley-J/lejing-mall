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
    public static final String JOB_PARAM_KEY = "JOB_PARAM";

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
         * @param code 枚举值
         * @return JobStatusEnum
         */
        public static JobStatusEnum getEnum(int code) {
            return Stream.of(JobStatusEnum.values())
                    .filter(anEnum -> anEnum.getCode() == code)
                    .findFirst().orElse(null);
        }
    }

    /**
     * 失火策略枚举
     */
    @Getter
    @AllArgsConstructor
    public enum MisfireHandling {
        /**
         * 1 立即执行
         */
        IGNORE_MISFIRES(1, "立即执行"),
        /**
         * 2 执行一次
         */
        FIRE_AND_PROCEED(2, "执行一次"),
        /**
         * 3 放弃执行
         */
        DO_NOTHING(3, "放弃执行"),
        ;

        /**
         * 枚举值
         */
        private final int code;

        /**
         * 枚举名称
         */
        private final String name;

        /**
         * 获取失火处理枚举
         *
         * @param code 枚举值
         * @return MisfireHandling
         */
        public static MisfireHandling getEnum(int code) {
            return Stream.of(MisfireHandling.values())
                    .filter(anEnum -> anEnum.getCode() == code)
                    .findFirst().orElse(null);
        }
    }

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

        /**
         * 获取quartz任务调度状态枚举
         *
         * @param code 枚举值
         * @return TriggerStateEnum枚举
         */
        public static TriggerStateEnum getEnum(String code) {
            return TriggerStateEnum.valueOf(code);
        }
    }
}
