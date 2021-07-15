package cn.alphahub.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 应用运行环境
 *
 * @author lwj
 * @version 1.0
 * @date 2021/07/11
 */
@Getter
@AllArgsConstructor
public enum AppEnvironmentEnum {
    /**
     * 开发环境
     */
    DEV("dev", "开发环境"),
    /**
     * 测试环境
     */
    TEST("test", "测试环境"),
    /**
     * 生产环境
     */
    PROD("prod", "生产环境");

    /**
     * 环境
     */
    private final String env;

    /**
     * 名称描述
     */
    private final String name;
}
