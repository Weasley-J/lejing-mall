package cn.alphahub.mall.order.service;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.util.JSONUtil;
import cn.alphahub.mall.order.excel.easypoi.dto.request.BillingDetailQueryRequest;
import cn.alphahub.mall.order.excel.easypoi.dto.response.BillingDetailQueryResponse;
import cn.alphahub.mall.order.excel.easypoi.util.ExcelUtil;
import cn.alphahub.mall.order.excel.easypoi.util.style.ExcelExportStylerBorderImpl;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 账单明细业务类
 * <p>
 *
 * @author lwj
 * @version 1.0
 * @date 2021年7月21日
 */
@Slf4j
@Service
public class BillDetailService {

    /**
     * 账单明细 - excel导出
     *
     * @param request  http servlet request
     * @param response http servlet response
     */
    public BaseResult<String> downloadBillingDetails(BillingDetailQueryRequest req, HttpServletRequest request, HttpServletResponse response) {
        log.info("下载账单明细excel，入参：{}", JSONUtil.toJsonStr(req));
        List<BillingDetailQueryResponse> responses = billingDetailList(req);
        if (CollectionUtils.isEmpty(responses)) {
            return BaseResult.error("未查询到账单明细数据");
        }
        String excelTitle = "账单明细";
        String filename = excelTitle + "-" + DateUtil.format(LocalDateTime.now(), "yyyyMMddHHmmss") + ".xlsx";
        ExportParams exportParams = new ExportParams(excelTitle, excelTitle);
        // 设置导出excel的边框的样式
        exportParams.setStyle(ExcelExportStylerBorderImpl.class);
        try {
            ExcelUtil.exportExcel(request, response, filename, exportParams, BillingDetailQueryResponse.class, responses);
        } catch (IOException e) {
            log.error("io exception: {}", e.getLocalizedMessage(), e);
        }
        return BaseResult.success();
    }

    /**
     * 账单明细excel预览
     *
     * @param req      查询参数
     * @param response http servlet response
     */
    public void previewHtml(BillingDetailQueryRequest req, HttpServletResponse response) throws IOException {
        log.info("账单明细excel预览，入参：{}", JSONUtil.toJsonStr(req));
        List<BillingDetailQueryResponse> responses = billingDetailList(req);
        ExcelUtil.previewHtml(response, new ExportParams("账单明细预览", "账单明细"), BillingDetailQueryResponse.class, responses);
    }

    /**
     * 账单明细列表
     * <p>伪方法：从数据库查询</p>
     *
     * @param req 入参
     * @return 账单明细查询列表
     */
    private List<BillingDetailQueryResponse> billingDetailList(BillingDetailQueryRequest req) {
        log.info("查询账单明细列表:{}", JSONUtil.toJsonStr(req));
        List<BillingDetailQueryResponse> responses = new ArrayList<>();

        // 这中间的了逻辑是你去数据库查数据，将数据返回

        if (CollectionUtils.isEmpty(responses)) {
            return Lists.newArrayList();
        }

        return responses;
    }
}
