package cn.alphahub.mall.order.excel.easyexcel.controller;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.mall.order.excel.easyexcel.service.OrderExcelHandleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
@Slf4j
@RestController
@RequestMapping("/order/public/easyexcel")
public class OrderItemExcelWebController {
    @Resource
    private OrderExcelHandleService orderExcelHandleService;

    /**
     * 上传订单的excel文件
     *
     * @param file multipart file
     * @return true
     */
    @PostMapping("/excel/upload")
    public BaseResult<Void> upload(@RequestPart(name = "file") MultipartFile file, HttpServletResponse response) {
        try {
            orderExcelHandleService.upload(file, response);
            return BaseResult.ok();
        } catch (Exception e) {
            log.error("{}", e.getLocalizedMessage(), e);
            return BaseResult.error();
        }
    }

    /**
     * 下载订单的excel文件
     *
     * @param request  http servlet request
     * @param response http servlet response
     * @return true
     */
    @GetMapping("/excel/download")
    public BaseResult<Void> download(HttpServletRequest request, HttpServletResponse response) {
        try {
            orderExcelHandleService.download(request, response);
            return BaseResult.ok();
        } catch (Exception e) {
            log.error("{}", e.getLocalizedMessage(), e);
            return BaseResult.error();
        }
    }
}
