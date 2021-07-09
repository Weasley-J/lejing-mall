package cn.alphahub.mall.order.excel.easypoi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.mall.order.convertor.BeanUtil;
import cn.alphahub.mall.order.excel.easypoi.dto.OrderEasyPoiDTO;
import cn.alphahub.mall.order.service.OrderService;
import cn.hutool.core.date.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 主订单数据excel导入导出Controller
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/07/09
 */
@Slf4j
@RestController
@RequestMapping("/order/public/easypoi")
public class OrderEasyPoExportController {
    @Resource
    private OrderService orderService;

    /**
     * 导出订单数据
     *
     * @param request  http servlet request
     * @param response http servlet response
     */
    @GetMapping(value = "/download")
    public BaseResult<Void> download(HttpServletRequest request, HttpServletResponse response) {

        String filename = "主订单-" + LocalDateTimeUtil.format(LocalDateTime.now(), "yyyyMMddHHmmss") + ".xlsx";
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename.trim(), StandardCharsets.UTF_8));

        List<OrderEasyPoiDTO> list = orderService.list().stream().map(BeanUtil.INSTANCE::copyToOrderEasyPoiDTO).collect(Collectors.toList());

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            var workbook = ExcelExportUtil.exportExcel(new ExportParams("主订单数据", "订单"), OrderEasyPoiDTO.class, list);
            workbook.write(outputStream);
        } catch (IOException e) {
            log.error("{}", e.getLocalizedMessage(), e);
        }
        return BaseResult.ok();
    }
}
