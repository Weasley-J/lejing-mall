package cn.alphahub.mall.common.entity;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 排序
 *
 * @author weasley
 * @version 1.0.0
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SortFilter implements Serializable {
    /**
     * 表前缀
     */
    public static final String TABLE_PREFIX = "t_";
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";

    /**
     * 排序规则映射
     */
    private List<SortArg> sortArgs;

    /**
     * 获取排序条件，示例: t_station_name DESC,t_cooperated_before ASC
     */
    public static String getOrderBy(List<SortArg> sortArgs) {
        if (CollectionUtils.isEmpty(sortArgs)) {
            return null;
        }
        StringBuilder orderBy = new StringBuilder();
        for (SortArg sortArg : sortArgs) {
            if (StringUtils.isBlank(sortArg.getColumn())) {
                continue;
            }
            if (!StringUtils.isCamel(sortArg.getColumn())) {
                continue;
            }
            String sortColumn = TABLE_PREFIX + StringUtils.camelToUnderline(sortArg.getColumn());
            String sortRule = (null != sortArg.getIsDesc() && sortArg.getIsDesc()) ? DESC : ASC;
            orderBy.append(sortColumn).append(" ").append(sortRule).append(",");
        }
        if (orderBy.length() > 0) {
            orderBy.deleteCharAt(orderBy.length() - 1);
        }
        return org.apache.commons.lang3.StringUtils.defaultIfBlank(orderBy.toString(), null);
    }

    /**
     * 获取排序条件，示例: t_station_name DESC,t_cooperated_before ASC
     */
    public String getOrderBy() {
        String orderBy = getOrderBy(sortArgs);
        log.info("排序规则: {}", orderBy);
        return orderBy;
    }

}
