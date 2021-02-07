package cn.alphahub.mall.ware.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 仓库信息
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:47:37
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("wms_ware_info")
public class WareInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
    @TableId
    private Long id;

	/**
	 * 仓库名
	 */
    private String name;

	/**
	 * 仓库地址
	 */
    private String address;

	/**
	 * 区域编码
	 */
    private String areacode;

}
