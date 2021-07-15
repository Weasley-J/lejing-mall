package cn.alphahub.mall.app.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 提交预约-BO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteSessionOrderBO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户名
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户手机号码
     */
    private String userPhone;

    /**
     * 场次集合各个场次价格相加之和
     */
    private Integer totalPrice;

    /**
     * 用户备注
     */
    private String remark;


    /**
     * 场地预约场次集合
     */
    private List<SiteSessionBO> sessions;
}
