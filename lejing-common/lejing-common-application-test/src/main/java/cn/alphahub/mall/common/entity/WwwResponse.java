package cn.alphahub.mall.common.entity;

import cn.alphahub.mall.common.EscapeResult;
import lombok.Data;

/**
 * 输入类描述
 *
 * @author weasley
 * @version 1.0
 * @date 2022/7/1
 */
@Data
public class WwwResponse implements EscapeResult {
    private Object data;
}
