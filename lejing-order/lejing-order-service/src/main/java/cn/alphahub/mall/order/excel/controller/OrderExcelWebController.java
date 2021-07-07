package cn.alphahub.mall.order.excel.controller;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.mall.order.excel.service.OrderExcelHandleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 订单导入导出 Controller
 * <ul>
 *     <li>基于easyexcel导出示例（上传下载）</li>
 * </ul>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/07/07
 */
@RestController
@RequestMapping("/order/public")
public class OrderExcelWebController {
    @Resource
    private OrderExcelHandleService orderExcelHandleService;


    /**
     * 上传订单的excel文件
     *
     * @param request  http servlet request
     * @param response http servlet response
     * @return true
     */
    @GetMapping("/excel/upload")
    public BaseResult<Boolean> upload(HttpServletRequest request, HttpServletResponse response) {
        orderExcelHandleService.upload(request, response);
        return BaseResult.ok();
    }

    /**
     * 下载订单的excel文件
     *
     * @param request  http servlet request
     * @param response http servlet response
     * @return true
     */
    @GetMapping("/excel/download")
    public BaseResult<Boolean> download(HttpServletRequest request, HttpServletResponse response) {
        orderExcelHandleService.download(request, response);
        return BaseResult.ok();
    }
}
