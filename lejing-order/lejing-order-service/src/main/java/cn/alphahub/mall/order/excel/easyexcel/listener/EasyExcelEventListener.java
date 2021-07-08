package cn.alphahub.mall.order.excel.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel读取监听器
 *
 * @author liuwenjing
 * @date 2021年7月8日11:08:50
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EasyExcelEventListener<T> extends AnalysisEventListener<T> {

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final Integer BATCH_SIZE = 5;

    List<T> cacheList = new ArrayList<>();

    @Resource
    private EasyExcelReadEvent readEvent;

    /**
     * When analysis one row trigger invoke function.
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context 上下文是 Excel 阅读器的主要定位点
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        cacheList.add(data);
        if (cacheList.size() >= BATCH_SIZE) {
            readEvent.read(cacheList);
            // 执行完业务逻辑清理 list
            cacheList.clear();
        }
    }

    /**
     * <p>所有数据解析完成了 都会来调用的方法</p>
     * if have something to do after all analysis
     *
     * @param context 上下文是 Excel 阅读器的主要定位点
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        readEvent.doAfterAllAnalysed();
    }
}
