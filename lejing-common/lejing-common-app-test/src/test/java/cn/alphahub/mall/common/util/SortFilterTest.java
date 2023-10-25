package cn.alphahub.mall.common.util;

import cn.alphahub.mall.common.entity.Order;
import cn.alphahub.mall.common.entity.SortArgs;
import cn.alphahub.mall.common.entity.SortArgs.SortArg;

import java.util.ArrayList;

/**
 * Enter the description of this class here
 *
 * @author weasley
 * @version 1.0.0
 */
public class SortFilterTest {
    public static void main(String[] args) {
        System.err.println(SortArgs.getOrderBy(new ArrayList<SortArg>() {{
            add(SortArgs.newSortArg(Order::getUseIntegration, true, null));
            add(SortArgs.newSortArg(Order::getCommentTime, true, null));
            add(SortArgs.newSortArg(Order::getDiscountAmount, false, null));
            add(SortArgs.newSortArg(Order::getReceiverDetailAddress, false, "t_"));
        }}));
    }
}
