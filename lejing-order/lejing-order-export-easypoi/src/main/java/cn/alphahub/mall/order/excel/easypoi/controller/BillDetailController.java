package cn.alphahub.mall.order.excel.easypoi.controller;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.mall.order.excel.easypoi.dto.request.BillingDetailQueryRequest;
import cn.alphahub.mall.order.service.BillDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 账单明细Controller
 * <p>这个controller主要展示</p>
 *
 * @author lwj
 * @version 1.0
 * @date 2021年7月21日
 */
@Slf4j
@RestController
@RequestMapping("/api/bill/detail")
public class BillDetailController {

    @Resource
    private BillDetailService billDetailService;

    /**
     * 账单明细 - 上传
     *
     * @param file 上传
     */
    @PostMapping("/upload/bills")
    public BaseResult<Void> uploadBillingDetails(@RequestPart(name = "file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        return billDetailService.uploadBillingDetails(file, request, response);
    }

    /**
     * 账单明细 - 下载
     *
     * @param req 查询参数
     * @download
     */
    @GetMapping("/download/bills")
    public BaseResult<String> downloadBillingDetails(@ModelAttribute(name = "req") BillingDetailQueryRequest req, HttpServletRequest request, HttpServletResponse response) {
        return billDetailService.downloadBillingDetails(req, request, response);
    }

    /**
     * 账单明细excel预览
     *
     * @param req      查询参数
     * @param response http servlet response
     * @throws IOException iO Exception
     * @page
     */
    @GetMapping("/preview/html")
    public void previewHtml(@ModelAttribute(name = "req") BillingDetailQueryRequest req, HttpServletResponse response) throws IOException {
        billDetailService.previewHtml(req, response);
    }
}
