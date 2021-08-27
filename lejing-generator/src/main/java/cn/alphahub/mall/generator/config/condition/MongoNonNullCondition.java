package cn.alphahub.mall.generator.config.condition;

import cn.alphahub.mall.generator.enums.DbType;
import org.apache.commons.lang3.ObjectUtils;
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
        return DbType.MONGODB.getCode().equalsIgnoreCase(property);
    }
}
