package cn.alphahub.mall.order.excel.read;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CommonExcelListener<T> extends AnalysisEventListener<T> {
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        System.out.println(t);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println(analysisContext.currentReadHolder());
    }
}
