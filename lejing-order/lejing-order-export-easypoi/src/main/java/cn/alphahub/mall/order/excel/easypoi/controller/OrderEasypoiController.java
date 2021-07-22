package cn.alphahub.mall.order.excel.easypoi.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.handler.inter.IReadHandler;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.util.JSONUtil;
import cn.alphahub.mall.order.convertor.Convertor;
import cn.alphahub.mall.order.excel.easypoi.dto.OrderExcelDTO;
import cn.alphahub.mall.order.excel.easypoi.util.ExcelUtil;
import cn.alphahub.mall.order.service.OrderService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
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
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
public class OrderEasypoiController {
    @Resource
    private Convertor convertor;
    @Resource
    private OrderService orderService;

    /**
     * 导出订单数据
     *
     * @param request  http servlet request
     * @param response http servlet response
     * @download
     */
    @GetMapping(value = "/download/order")
    public BaseResult<Void> downloadOrder(HttpServletRequest request, HttpServletResponse response) {
        String filename = "主订单-" + LocalDateTimeUtil.format(LocalDateTime.now(), "yyyyMMddHHmmss") + ".xlsx";
        List<OrderExcelDTO> list = orderService.list().stream().map(order -> {
            var orderExcelDTO = convertor.copyToOrderExcelDTO(order);
            orderExcelDTO.setDiscountRate(new BigDecimal("0.0116"));
            return orderExcelDTO;
        }).collect(Collectors.toList());
        try {
            ExcelUtil.exportExcel(request, response, filename, new ExportParams("主订单数据", "订单"), OrderExcelDTO.class, list);
        } catch (IOException e) {
            log.error("{}", e.getLocalizedMessage(), e);
        }
        return BaseResult.ok();
    }

    /**
     * 导入订单数据
     *
     * @param file     前端提交的表单文件
     * @param request  http servlet request
     * @param response http servlet response
     */
    @PostMapping(value = "/upload/order")
    public BaseResult<Void> uploadOrder(@RequestPart(name = "file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        try {
            var params = new ImportParams();
            //标题有一行（如果要读取的文件没有就不用设置）
            params.setTitleRows(1);
            params.setSheetNum(1);
            ExcelUtil.importExcelBySax(file.getInputStream(), OrderExcelDTO.class, params, new IReadHandler<>() {
                /**
                 * 缓存集合，防止读取数据时OOM
                 */
                private List<OrderExcelDTO> cacheList = new ArrayList<>();
                /**
                 * 批处理数据大小, 线上调大一点
                 */
                private int batchSize = 10;

                /**
                 * 处理解析对象
                 */
                @Override
                public void handler(OrderExcelDTO orderExcelDTO) {
                    cacheList.add(orderExcelDTO);
                    if (cacheList.size() == batchSize) {
                        handleData(cacheList);
                        cacheList.clear();
                    }
                }

                /**
                 * 处理完成之后的业务
                 */
                @Override
                public void doAfterAll() {
                    if (CollUtil.isNotEmpty(cacheList)) {
                        handleData(cacheList);
                        cacheList.clear();
                    }
                }

                /**
                 * 处理数据，业务方法
                 */
                private void handleData(List<OrderExcelDTO> orders) {
                    System.out.println("开始处理一批数据，数据量：" + orders.size());
                    orders.forEach(JSONUtil::printJsonStr);
                }

            });
        } catch (Exception e) {
            log.error("{}", e.getLocalizedMessage(), e);
        }
        return BaseResult.ok();
    }
}
