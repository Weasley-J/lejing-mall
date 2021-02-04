package cn.alphahub.mall.member.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 会员收货地址
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:14:36
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("ums_member_receive_address")
public class MemberReceiveAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
    @TableId
    private Long id;

	/**
	 * member_id
	 */
    private Long memberId;

	/**
	 * 收货人姓名
	 */
    private String name;

	/**
	 * 电话
	 */
    private String phone;

	/**
	 * 邮政编码
	 */
    private String postCode;

	/**
	 * 省份/直辖市
	 */
    private String province;

	/**
	 * 城市
	 */
    private String city;

	/**
	 * 区
	 */
    private String region;

	/**
	 * 详细地址(街道)
	 */
    private String detailAddress;

	/**
	 * 省市区代码
	 */
    private String areacode;

	/**
	 * 是否默认
	 */
    private Integer defaultStatus;

}