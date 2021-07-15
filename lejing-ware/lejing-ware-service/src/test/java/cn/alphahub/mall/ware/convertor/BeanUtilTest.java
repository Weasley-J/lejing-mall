package cn.alphahub.mall.ware.convertor;

import cn.alphahub.common.mq.StockDetailTo;
import cn.alphahub.mall.ware.domain.WareOrderTaskDetail;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BeanUtilTest {

    @BeforeEach
    void setUp() {
        System.err.println("------------------");
    }

    @AfterEach
    void tearDown() {
        System.err.println("------------------");
    }

    @Test
    void copy() {
        WareOrderTaskDetail taskDetail = new WareOrderTaskDetail();
        taskDetail.setId(10086L);
        taskDetail.setSkuId(10010L);
        taskDetail.setSkuName("这是一个示例");
        taskDetail.setSkuNum(56);
        taskDetail.setTaskId(10000L);
        taskDetail.setWareId(1L);
        taskDetail.setLockStatus(2);
        StockDetailTo detailTo = BeanUtil.INSTANCE.copy(taskDetail);

        System.out.println("taskDetail: " + JSONUtil.toJsonStr(taskDetail));
        System.out.println("detailTo: " + JSONUtil.toJsonStr(detailTo));
    }
}
