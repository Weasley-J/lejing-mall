package cn.alphahub.mall.reserve.app.pojo.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 场地详情-BO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteDetailBO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 发布的信息(关联sys_dict_data表的dict_code)
     */
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private Integer sitePubDictCode;

    /**
     * 发布的信息的名称
     */
    private String sitePubDictName;


    /**
     * 场地信息
     */
    private String sitePubTopic;
}
