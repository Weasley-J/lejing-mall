package cn.alphahub.mall.coupon.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 专题商品
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:10:59
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sms_home_subject_spu")
public class HomeSubjectSpu implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
    @TableId
    private Long id;

	/**
	 * 专题名字
	 */
    private String name;

	/**
	 * 专题id
	 */
    private Long subjectId;

	/**
	 * spu_id
	 */
    private Long spuId;

	/**
	 * 排序
	 */
    private Integer sort;

}