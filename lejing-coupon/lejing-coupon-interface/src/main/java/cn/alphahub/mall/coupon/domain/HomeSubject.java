package cn.alphahub.mall.coupon.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:41:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sms_home_subject")
public class HomeSubject implements Serializable {
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
	 * 专题标题
	 */
    private String title;

	/**
	 * 专题副标题
	 */
    private String subTitle;

	/**
	 * 显示状态
	 */
    private Integer status;

	/**
	 * 详情连接
	 */
    private String url;

	/**
	 * 排序
	 */
    private Integer sort;

	/**
	 * 专题图片地址
	 */
    private String img;

}
