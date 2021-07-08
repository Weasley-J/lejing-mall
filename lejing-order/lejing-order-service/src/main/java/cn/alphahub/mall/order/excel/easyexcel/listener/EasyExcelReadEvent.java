package cn.alphahub.mall.order.excel.easyexcel.listener;

import java.util.List;

/**
 * easyexcel读取业顶层接口
 * <ul>
 *     easyexcel读取数据顶层接口
 *     <li>读取excel时需要执行业务方法自行实现{@code EasyExcelReadEvent#read(java.util.List)}方法</li>
 *     <li>注入IOC交由spring托管</li>
 * </ul>
 *
 * @author liuwenjing
 * @date 2021年7月8日11:08:50
 */
public interface EasyExcelReadEvent<T> {

    /**
     * 读取excel后的逻辑，自行实现逻辑
     * <ul>
     *     业务逻辑:
     *     <li>将items保存到数据库</li>
     *     <li>对items进行分析等</li>
     * </ul>
     *
     * @param items 数据列表
     */
    void read(List<T> items);

    /**
     * 所有数据解析完成了 都会来调用的方法
     * <ul>
     *     <li>{@code EasyExcelReadEvent#read(java.util.List)}方法执行完了, 在这个方法里面写你的业务逻辑，如: 保存读取的数据到数据库等</li>
     *     <li>需要读数数据后的后置操作就复写这个方法，在里面处理你的业务逻辑</li>
     * </ul>
     */
    default void doAfterAllAnalysed() {
    }
}

