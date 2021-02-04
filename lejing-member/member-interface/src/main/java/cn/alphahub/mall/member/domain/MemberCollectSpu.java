package cn.alphahub.mall.member.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员收藏的商品
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:14:36
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("ums_member_collect_spu")
public class MemberCollectSpu implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
    @TableId
    private Long id;

	/**
	 * 会员id
	 */
    private Long memberId;

	/**
	 * spu_id
	 */
    private Long spuId;

	/**
	 * spu_name
	 */
    private String spuName;

	/**
	 * spu_img
	 */
    private String spuImg;

	/**
	 * create_time
	 */
    private Date createTime;

}