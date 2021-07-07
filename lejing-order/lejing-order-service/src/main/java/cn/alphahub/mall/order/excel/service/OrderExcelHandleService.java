package cn.alphahub.mall.order.excel.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 输订单导入导出Service
 * <ul>
 *     <li>基于easyexcel导出示例（上传下载）</li>
 * </ul>
 *
 * @author liuwe
 * @version 1.0
 * @date 2021/07/07
 */
@Slf4j
@Service
public class OrderExcelHandleService {

    /**
     * 上传订单excel文件
     *
     * @param request  http servlet request
     * @param response http servlet response
     */
    public void upload(HttpServletRequest request, HttpServletResponse response) {
        log.info("上传订单excel文件.");
    }

    /**
     * 下载订单的excel文件
     *
     * @param request  http servlet request
     * @param response http servlet response
     */
    public void download(HttpServletRequest request, HttpServletResponse response) {
        log.info("下载订单的excel文件.");
    }
}
