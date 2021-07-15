package cn.alphahub.mall.app.pojo.vo;

import cn.alphahub.mall.app.pojo.bo.SiteBookBO;
import cn.alphahub.mall.app.pojo.bo.SiteDetailBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 场地预约详情-值对象
 *
 * @author lwj
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SiteReserveDetailVO extends SiteReserveVO {
    private static final long serialVersionUID = 1L;
    /**
     * 预约价格（单位：分），默认价格
     */
    private Integer sitePrice;
    /**
     * 开始营业时间（规则: 24H制，时分中间用":"号隔开；如: 08:00, 09:30，可精确到具体分钟）
     */
    private String siteOpenTime;

    /**
     * 结束营业时间（规则: 24H制，时分中间用":"号隔开；如: 19:00, 22:30，可精确到具体分钟）
     */
    private String siteCloseTime;

    /**
     * 营运状态（0-不可用，不营运；1-可用，可运营；默认：1）
     */
    private Integer siteOpenStatus;

    /**
     * 场地管理员电话
     */
    private String siteAdministratorPhone;

    /**
     * 场地场地预定列表
     */
    private List<SiteBookBO> siteBookList;

    /**
     * 场地其他信息 eb_site_reserve_detail表
     */
    private List<SiteDetailBO> siteDetailList;
}
