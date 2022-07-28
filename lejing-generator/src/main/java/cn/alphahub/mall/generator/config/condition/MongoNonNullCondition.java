package cn.alphahub.mall.generator.config.condition;

import cn.alphahub.mall.generator.enums.DbType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * mongo condition
 *
 * @author gxz gongxuanzhang@foxmail.com,lwj
 */
public class MongoNonNullCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String property = context.getEnvironment().getProperty("code.generator.db-type", String.class);
        return StringUtils.equalsIgnoreCase(DbType.MONGODB.getCode(), property);
    }
}
