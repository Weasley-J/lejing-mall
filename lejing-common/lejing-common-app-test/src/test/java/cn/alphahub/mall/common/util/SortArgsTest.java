package cn.alphahub.mall.common.util;

import cn.alphahub.mall.common.entity.Order;
import cn.alphahub.mall.common.entity.SortArgs;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * 函数式排序测试用例
 *
 * @author weasley
 * @version 1.0.0
 */
class SortArgsTest {

    @Test
    void testOrderBy() {
        System.err.println(SortArgs.getOrderBy(new ArrayList<>() {{
            add(SortArgs.newSortArg(Order::getUseIntegration, true, null));
            add(SortArgs.newSortArg(Order::getCommentTime, true, null));
            add(SortArgs.newSortArg(Order::getDiscountAmount, false, null));
            add(SortArgs.newSortArg(Order::getReceiverDetailAddress, false, "t_"));
        }}));
        // use_integration DESC, comment_time DESC, discount_amount ASC, t_receiver_detail_address ASC

        String orderBy = SortArgs.getOrderBy(SortArgs.newSortArg(Order::getPromotionAmount, false, "f_"),
                SortArgs.newSortArg(Order::getReceiverPostCode, true, "t_"),
                SortArgs.newSortArg(Order::getDeliveryCompany, true, null)
        );
        System.err.println(orderBy);
        // f_promotion_amount ASC, t_receiver_post_code DESC, delivery_company DESC
    }
}
