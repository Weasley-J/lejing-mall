package cn.alphahub.mall.member.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 会员收藏的专题活动
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:14:36
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("ums_member_collect_subject")
public class MemberCollectSubject implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
    @TableId
    private Long id;

	/**
	 * subject_id
	 */
    private Long subjectId;

	/**
	 * subject_name
	 */
    private String subjectName;

	/**
	 * subject_img
	 */
    private String subjectImg;

	/**
	 * 活动url
	 */
    private String subjectUrll;

}