package cn.alphahub.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Set;

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
     * 个人玩耍环境
     */
    LWJ("lwj", "个人玩耍环境"),
    /**
     * 本地开发环境
     */
    LOCAL("local", "本地开发环境"),
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
    PROD("prod", "生产环境"),
    ;

    /**
     * 环境
     */
    private final String env;

    /**
     * 名称描述
     */
    private final String name;

    /**
     * 可以访问直接API文档的环境
     *
     * @return 可以访问直接API文档的环境
     */
    public static Set<String> getApiDocCanVisitEnv() {
        LinkedHashSet<String> set = new LinkedHashSet<>();
        set.add(AppEnvironmentEnum.LWJ.getEnv());
        set.add(AppEnvironmentEnum.LOCAL.getEnv());
        set.add(AppEnvironmentEnum.DEV.getEnv());
        return set;
    }
}
