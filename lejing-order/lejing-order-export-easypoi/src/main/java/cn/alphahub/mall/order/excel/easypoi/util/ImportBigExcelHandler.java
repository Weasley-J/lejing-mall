package cn.alphahub.mall.order.excel.easypoi.util;

import cn.afterturn.easypoi.handler.inter.IReadHandler;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * 输入类描述
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/07/21
 */
@FunctionalInterface
public interface ImportBigExcelHandler<T> extends IReadHandler<T> {
    /**
     * 批处理数据大小
     * <p> 线上调大一点，5000；这里演示用一批读取3条</p>
     */
    Integer BATCH = 3;
    /**
     * 缓存集合，防止读取数据时OOM
     */
    ThreadLocal<List<Object>> CACHE = new ThreadLocal<>();

    /**
     * 处理excel数据，核心业务方法
     *
     * @param list 读取的excel列表
     */
    void processExcel(List<?> list);

    /**
     * 处理解析对象
     *
     * @param object excel对象，读取的excel数据的一行
     */
    @Override
    default void handler(Object object) {
        CACHE.get().add(object);
        if (CACHE.get().size() == BATCH) {
            processExcel(CACHE.get());
            CACHE.get().clear();
        }
    }

    /**
     * 处理完成之后的业务
     */
    @Override
    default void doAfterAll() {
        if (CollectionUtils.isNotEmpty(CACHE.get())) {
            processExcel(CACHE.get());
            CACHE.get().clear();
            CACHE.remove();
        }
    }
}
