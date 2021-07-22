package cn.alphahub.mall.order.excel.easypoi.controller;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.handler.inter.IReadHandler;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.util.JSONUtil;
import cn.alphahub.mall.order.convertor.Convertor;
import cn.alphahub.mall.order.domain.OrderItem;
import cn.alphahub.mall.order.excel.easypoi.dto.OrderItemExcelDTO;
import cn.alphahub.mall.order.excel.easypoi.util.ExcelUtil;
import cn.alphahub.mall.order.excel.easypoi.util.ExportBigDataHandler;
import cn.alphahub.mall.order.service.OrderItemService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 子订单订单导入导出Controller
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/07/12
 */
@Slf4j
@RestController
@RequestMapping("/order/public/easypoi")
public class OrderItemEasypoiController {
    @Resource
    ExportBigDataHandler<OrderItemExcelDTO> bigDataHandler;
    @Resource
    private OrderItemService orderItemService;
    @Resource
    private Convertor convertor;

    /**
     * 下载订单的excel文件
     *
     * @param request  http servlet request
     * @param response http servlet response
     * @return true
     * @download 文件下载
     */
    @GetMapping("/download/order/item")
    public BaseResult<Void> download(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {

        String filename = "子订单项数据-" + LocalDateTimeUtil.format(LocalDateTime.now(), "yyyyMMddHHmmss");

        List<OrderItemExcelDTO> itemExcelDTOS = orderItemService.list(new QueryWrapper<OrderItem>().lambda().last(" LIMIT 20000"))
                .stream().map(convertor::copyToOrderItemExcelDto).collect(Collectors.toList());

        int size = itemExcelDTOS.size();
        int batchSize = 100;

        if (CollectionUtils.isNotEmpty(itemExcelDTOS)) {
            OrderItemExcelDTO lastOne = itemExcelDTOS.get(itemExcelDTOS.size() - 1);
            List<OrderItemExcelDTO> bigDataList = new ArrayList<>(itemExcelDTOS);
            Map<String, String> queryMap = Maps.newLinkedHashMap();
            queryMap.put("turn", "1");
            for (int i = 1; i <= (1000000 - size); i++) {
                OrderItemExcelDTO dto = new OrderItemExcelDTO();
                BeanUtils.copyProperties(lastOne, dto);
                dto.setId(lastOne.getId() + i);
                System.out.println("id=" + dto.getId() + "; i=" + i + "; size=" + size);
                bigDataList.add(dto);
            }
            ExcelUtil.exportBigExcel(modelMap, request, response, queryMap,
                    bigDataHandler.setItems(bigDataList), OrderItemExcelDTO.class,
                    filename, "子订单项数据", "子订单项"
            );
        }
        return BaseResult.ok();
    }

    /**
     * 上传订单的excel文件
     *
     * @param file multipart file
     * @return true
     */
    @PostMapping("/upload/order/item")
    public BaseResult<String> upload(@RequestPart(name = "file") MultipartFile file, HttpServletResponse response) {
        Date start = new Date();
        try {
            var params = new ImportParams();
            //标题有一行（如果要读取的文件没有就不用设置）
            params.setTitleRows(1);
            params.setSheetNum(2);
            ExcelUtil.importExcelBySax(file.getInputStream(), OrderItemExcelDTO.class, params, new IReadHandler<>() {
                /**
                 * 缓存集合，防止读取数据时OOM
                 */
                private List<OrderItemExcelDTO> cacheList = Lists.newArrayList();
                /**
                 * 批处理数据大小, 线上调大一点
                 */
                private int batchSize = 5000;

                /**
                 * 处理解析对象
                 */
                @Override
                public void handler(OrderItemExcelDTO orderExcelDTO) {
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
                private void handleData(List<OrderItemExcelDTO> orders) {
                    System.err.println("开始处理一批数据，数据量：" + orders.size());
                    orders.forEach(JSONUtil::printJsonStr);
                }

            });
        } catch (Exception e) {
            log.error("{}", e.getLocalizedMessage(), e);
        }
        Date end = new Date();
        return BaseResult.ok("解析耗时：" + DateUtil.format(start, "yyyy-MM-dd HH:mm:ss") + "至" + DateUtil.format(end, "yyyy-MM-dd HH:mm:ss"));
    }
}
