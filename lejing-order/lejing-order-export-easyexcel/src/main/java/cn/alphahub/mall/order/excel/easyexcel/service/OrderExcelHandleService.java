package cn.alphahub.mall.order.excel.easyexcel.service;

import cn.alphahub.common.util.JSONUtil;
import cn.alphahub.mall.order.convertor.Convertor;
import cn.alphahub.mall.order.excel.easyexcel.dto.OrderItemExcelDTO;
import cn.alphahub.mall.order.excel.easyexcel.listener.EasyExcelEventListener;
import cn.alphahub.mall.order.excel.easyexcel.listener.EasyExcelReadEvent;
import cn.alphahub.mall.order.service.OrderItemService;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
public class OrderExcelHandleService implements EasyExcelReadEvent<OrderItemExcelDTO> {
    @Resource
    private OrderItemService orderItemService;
    @Resource
    private EasyExcelEventListener<OrderItemExcelDTO> orderItemExcelReadListener;
    @Resource
    private Convertor convertor;

    /**
     * 上传订单excel文件
     *
     * @param file multipart file
     */
    public void upload(MultipartFile file, HttpServletResponse response) throws IOException {
        log.info("上传订单excel文件.");
        EasyExcel.read(file.getInputStream(), OrderItemExcelDTO.class, orderItemExcelReadListener).sheet().doRead();
    }

    /**
     * 下载订单的excel文件
     * <ul>
     *     <li>文件下载并且失败的时候返回json（默认失败了会返回一个有部分数据的Excel）</li>
     * </ul>
     *
     * @param request  http servlet request
     * @param response http servlet response
     */
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("下载订单的excel文件.");
        String filename = "子订单项数据-" + LocalDateTimeUtil.format(LocalDateTime.now(), "yyyyMMddHHmmss") + ".xlsx";
        List<OrderItemExcelDTO> orderItems = orderItemService.list().stream().map(convertor::copyToExcelDto).collect(Collectors.toList());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename.trim(), StandardCharsets.UTF_8));
        EasyExcel.write(response.getOutputStream(), OrderItemExcelDTO.class).sheet("子订单项数据").doWrite(orderItems);
    }

    /**
     * 读取excel
     *
     * @param items 读取到的数据列表
     */
    @Override
    public void read(List<OrderItemExcelDTO> items) {
        //读取excel文件后的逻辑
        items.forEach(orderItemExcelDTO -> log.info("读取到数据:{}", JSONUtil.toJsonStr(orderItemExcelDTO)));
    }
}
