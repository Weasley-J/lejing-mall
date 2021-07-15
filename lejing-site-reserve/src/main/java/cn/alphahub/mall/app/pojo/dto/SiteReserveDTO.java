package cn.alphahub.mall.app.pojo.dto;


import cn.alphahub.mall.site.domain.SiteReserve;
import cn.alphahub.mall.site.domain.SiteReserveDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 场地预约（包含场地详情）
 *
 * @author liuwenjing
 * @date 2021-01-28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SiteReserveDTO extends SiteReserve {
    /**
     * 场地详情
     */
    private SiteReserveDetail siteReserveDetail;
}
