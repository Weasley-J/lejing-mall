package cn.alphahub.mall.valid.domain;

import cn.alphahub.common.valid.annotation.DecimalRange;
import cn.alphahub.common.valid.annotation.IncludeValue;
import cn.alphahub.common.valid.annotation.ListValue;
import cn.alphahub.common.valid.group.EditGroup;
import cn.alphahub.common.valid.group.EditStatusGroup;
import cn.alphahub.common.valid.group.InsertGroup;
import cn.alphahub.common.valid.group.QueryGroup;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 虚拟币 - 数据对象
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/08/04
 */
@Data
@Accessors(chain = true)
public class VirtualCoin implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 会员id
     */
    @NotNull(message = "会员id不能为空", groups = {InsertGroup.class, EditGroup.class, EditStatusGroup.class, QueryGroup.class})
    private Long memberId;
    /**
     * 虚拟币金额: -12000 ~ 11000 之间
     */
    @DecimalRange(min = "-12000", max = "11000", message = "虚拟币金额必须在区间[-12000,11000]内", groups = {InsertGroup.class, EditGroup.class})
    private BigDecimal virtualValue;
    /**
     * 虚拟币收支类型:  INCOME 收入, EXPENDITURE 支出
     */
    @IncludeValue(value = {"INCOME", "EXPENDITURE"}, message = "只能提交{INCOME,EXPENDITURE}内的字典值", groups = {InsertGroup.class, EditGroup.class})
    private String incomeExpenditureType;
    /**
     * 虚拟币状态: -1 失效, 0 冻结, 1 正常
     */
    @ListValue(value = {-1, 0, 1}, message = "只能提交{-1, 0, 1}内的值", groups = {InsertGroup.class, EditGroup.class, EditStatusGroup.class})
    private Integer status;
}
