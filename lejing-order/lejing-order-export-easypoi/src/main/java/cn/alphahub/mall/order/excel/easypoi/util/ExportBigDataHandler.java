package cn.alphahub.mall.order.excel.easypoi.util;

import cn.afterturn.easypoi.handler.inter.IExcelExportServer;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 大数据处理
 * <p>交由Spring IOC管理</p>
 *
 * @author liuwenjing
 */
@Data
@Slf4j
@Accessors(chain = true)
@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ExportBigDataHandler<T> implements IExcelExportServer {
    /**
     * 数据列表
     */
    private List<T> items;

    /**
     * 查询数据接口
     *
     * @param queryParams 查询条件
     * @param page        当前页数从1开始
     * @return List<Object>
     */
    @Override
    @SuppressWarnings({"unchecked"})
    public List<Object> selectListForExcelExport(Object queryParams, int page) {
        if (queryParams instanceof Map) {
            Map<String, String> queryMap = (Map<String, String>) queryParams;
            queryMap.forEach((k, v) -> {
                log.info("查询条件: key=" + k + ", val=" + v);
            });
        }

        List<Object> objects = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(this.getItems())) {
            objects.addAll(this.getItems());
        }

        return page > 1 ? null : objects;
    }
}
