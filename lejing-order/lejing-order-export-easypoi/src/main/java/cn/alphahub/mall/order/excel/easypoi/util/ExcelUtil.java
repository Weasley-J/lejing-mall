package cn.alphahub.mall.order.excel.easypoi.util;

import cn.afterturn.easypoi.csv.entity.CsvImportParams;
import cn.afterturn.easypoi.csv.imports.CsvImportService;
import cn.afterturn.easypoi.entity.vo.BigExcelConstants;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelXorHtmlUtil;
import cn.afterturn.easypoi.excel.entity.ExcelToHtmlParams;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.imports.ExcelImportService;
import cn.afterturn.easypoi.excel.imports.sax.SaxReadExcel;
import cn.afterturn.easypoi.exception.excel.ExcelImportException;
import cn.afterturn.easypoi.handler.inter.IExcelExportServer;
import cn.afterturn.easypoi.handler.inter.IReadHandler;
import cn.afterturn.easypoi.view.PoiBaseView;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Easypoi Excel工具类
 * <ul>
 *     <li>excel导入</li>
 *     <li>excel导出</li>
 *     <li><a href="http://doc.wupaas.com/docs/easypoi/easypoi-1c0u8l21lfgh8">帮助文档</a></li>
 * </ul>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/07/10
 */
@Slf4j
@SuppressWarnings({"unchecked"})
public class ExcelUtil {

    private ExcelUtil() {
    }

    /**
     * 大数据文件下载
     * <ul>
     *     <li>适用于数据量5W-100W</li>
     *     <li>执行完自动关闭流</li>
     * </ul>
     *
     * @param modelMap          充当Servlet MVC的通用模型持有者，通过MVC直接写出数据到客户端
     * @param request           http servlet request
     * @param response          http servlet response
     * @param dataParams        供IExcelExportServer接口使用的查询参数，会带到接口中，从queryParams中取用
     * @param excelExportServer 這是你的业务实现接口
     * @param pojoClass         excel type class
     * @param filename          下载文件名（文件名不需要文件后缀，easypoi底层已处理过）
     * @param excelTitle        excel title
     * @param sheetName         sheet name
     * @param <T>               type
     */
    public static <T> void exportBigExcel(
            ModelMap modelMap,
            HttpServletRequest request,
            HttpServletResponse response,
            Map<String, String> dataParams,
            IExcelExportServer excelExportServer,
            Class<T> pojoClass,
            String filename,
            String excelTitle,
            String sheetName
    ) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        ExportParams params = new ExportParams(excelTitle, sheetName, ExcelType.XSSF);
        params.setFreezeCol(2);
        modelMap.put(BigExcelConstants.FILE_NAME, filename);
        modelMap.put(BigExcelConstants.CLASS, pojoClass);
        modelMap.put(BigExcelConstants.PARAMS, params);
        modelMap.put(BigExcelConstants.DATA_PARAMS, dataParams);
        modelMap.put(BigExcelConstants.DATA_INTER, excelExportServer);
        PoiBaseView.render(modelMap, request, response, BigExcelConstants.EASYPOI_BIG_EXCEL_VIEW);
    }

    /**
     * 正规excel导出
     * <ul>
     *     <li>格式简单，数据量5W以内，注解方式</li>
     *     <li>方法执行完关闭流</li>
     * </ul>
     *
     * @param outputStream 输出流
     * @param entity       Excel导出参数
     * @param pojoClass    excel实例类class
     * @param list         excel实体类集合
     * @throws IOException IO异常
     */
    public static <T> void exportExcel(OutputStream outputStream, ExportParams entity, Class<T> pojoClass, List<T> list) throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(entity, pojoClass, list);
        workbook.write(outputStream);
        workbook.close();
    }

    /**
     * 大数据量导出
     * <ul>
     *     <li>数据量大超过5W,还在100W以内，注解方式</li>
     *     <li>方法执行完关闭流</li>
     * </ul>
     *
     * @param outputStream 输出流
     * @param entity       Excel 导出参数
     * @param pojoClass    excel实例类class
     * @param server       导出数据接口（自行实现）
     * @param queryParams  查询参数
     * @throws IOException IO异常
     */
    public static <T> void exportBigExcel(OutputStream outputStream, ExportParams entity, Class<T> pojoClass, IExcelExportServer server, Object queryParams) throws IOException {
        Workbook workbook = ExcelExportUtil.exportBigExcel(entity, pojoClass, server, queryParams);
        workbook.write(outputStream);
        workbook.close();
    }

    /**
     * web大数据量excel下载导出
     * <ul>
     *     <li>数据量大超过5W,还在100W以内，注解方式</li>
     *     <li>方法执行完关闭流</li>
     * </ul>
     *
     * @param request     http servlet request
     * @param response    http servlet response
     * @param filename    下载的文件名（已对文件名进行编码,不包含文件后缀）
     * @param entity      Excel 导出参数
     * @param pojoClass   excel实例类class
     * @param server      导出数据接口
     * @param queryParams 查询参数
     * @throws IOException IO异常
     */
    public static <T> void exportBigExcel(HttpServletRequest request, HttpServletResponse response, String filename, ExportParams entity, Class<T> pojoClass, IExcelExportServer server, Object queryParams) throws IOException {
        filename = getFilename(request, filename);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        exportBigExcel(response.getOutputStream(), entity, pojoClass, server, queryParams);
    }

    /**
     * web excel下载导出
     * <ul>
     *     <li>格式简单，数据量5W以内，注解方式</li>
     *     <li>方法执行完关闭流</li>
     * </ul>
     *
     * @param request   http servlet request
     * @param response  http servlet response
     * @param filename  下载的文件名（已对文件名进行编码,不包含文件后缀）
     * @param entity    Excel 导出参数
     * @param pojoClass excel实例类class
     * @param list      excel实体类集合
     * @throws IOException IO异常
     */
    public static <T> void exportExcel(HttpServletRequest request, HttpServletResponse response, String filename, ExportParams entity, Class<T> pojoClass, List<T> list) throws IOException {
        filename = getFilename(request, filename);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        exportExcel(response.getOutputStream(), entity, pojoClass, list);
    }

    /**
     * Excel导入数据源本地文件,不返回校验结果，导入字段类型：Integer,Long,Double,Date,String,Boolean
     * <ul>
     *     <li>数据量10W以内</li>
     * </ul>
     *
     * @param file      导入文件
     * @param pojoClass excel实体类class
     * @param params    导入参数
     * @param <T>       数据泛型T
     * @return 数据列表
     * @throws Exception 异常
     */
    public static <T> List<T> importExcel(File file, Class<T> pojoClass, ImportParams params) throws Exception {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            return new ExcelImportService().importExcelByIs(inputStream, pojoClass, params, false).getList();
        } catch (ExcelImportException e) {
            log.error("excel导入异常：{}", e.getMessage(), e);
            throw new ExcelImportException(e.getType(), e);
        }
    }

    /**
     * Excel导入数据源IO流，不返回校验结果，导入字段类型:Integer,Long,Double,Date,String,Boolean
     * <ul>
     *     <li>数据量10W以内</li>
     * </ul>
     *
     * @param inputStream 输入流
     * @param pojoClass   excel实体类class
     * @param params      导入参数
     * @param <T>         返回的数据类型
     * @return 数据列表
     * @throws Exception 异常
     */
    public static <T> List<T> importExcel(InputStream inputStream, Class<T> pojoClass, ImportParams params) throws Exception {
        return new ExcelImportService().importExcelByIs(inputStream, pojoClass, params, false).getList();
    }

    /**
     * Excel大文件导入通过SAX解析方法，适合大数据导入, 不支持图片，导入数据源IO流不返回校验结果 导入字段类型: Integer,Long,Double,Date,String,Boolean
     * <p>自行实现{@code BigExcelReadHandler#handleData(java.util.List)}方法处理业务逻辑，避免OOM</p>
     * <ul>
     *     <li>适合大数据量，数据量10W以上，100W以下</li>
     *     <li>每隔一段处理一次,cpu和内存都可以稳定在一定的范围内</li>
     * </ul>
     *
     * @param inputStream 输入流
     * @param pojoClass   excel实体类class
     * @param params      导入参数
     */
    public static <T> void importExcelBySax(InputStream inputStream, Class<T> pojoClass, ImportParams params, IReadHandler<T> handler) {
        new SaxReadExcel().readExcel(inputStream, pojoClass, params, handler);
    }

    /**
     * Csv 导入流适合大数据导入
     * 导入 数据源IO流,不返回校验结果 导入 字段类型 Integer,Long,Double,Date,String,Boolean
     * <ul>
     *     <li>适合超大数据量，100W以上</li>
     * </ul>
     *
     * @param inputStream 输入流
     * @param pojoClass   excel实体类class
     * @param params      CSV导入参
     */
    public static <T> List<T> importCsv(InputStream inputStream, Class<T> pojoClass, CsvImportParams params) {
        return new CsvImportService().readExcel(inputStream, pojoClass, params, null);
    }

    /**
     * Csv 导入流适合大数据导入
     * 导入 数据源IO流,不返回校验结果 导入 字段类型 Integer,Long,Double,Date,String,Boolean
     * <ul>
     *     <li>适合超大数据量，100W以上</li>
     * </ul>
     *
     * @param inputStream 输入流
     * @param pojoClass   excel实体类class
     * @param params      CSV导入参
     */
    public static <T> void importCsv(InputStream inputStream, Class<T> pojoClass, CsvImportParams params, IReadHandler<T> handler) {
        new CsvImportService().readExcel(inputStream, pojoClass, params, handler);
    }

    /**
     * 获取web下载文件名
     *
     * @param request  http servlet request
     * @param filename filename
     * @return 编码后的文件名
     */
    private static String getFilename(HttpServletRequest request, String filename) {
        String userAgent = request.getHeader("User-Agent");
        String msie = "MSIE";
        String trident = "Trident";
        if (!userAgent.contains(msie) && !userAgent.contains(trident)) {
            filename = new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        } else {
            filename = URLEncoder.encode(filename, StandardCharsets.UTF_8);
        }
        return filename.trim();
    }

    /**
     * excel预览
     * <p>07版<b style="color:red">.xlsx</b>后缀名的文件</p>
     *
     * @param response  http servlet response
     * @param entity    Excel 导出参数
     * @param pojoClass excel实例类class
     * @param list      excel实体类集合
     * @throws IOException IO异常
     */
    public static <T> void previewHtml(HttpServletResponse response, ExportParams entity, Class<T> pojoClass, List<T> list) throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(entity, pojoClass, list);
        ExcelToHtmlParams params = new ExcelToHtmlParams(workbook);
        workbook.close();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.getOutputStream().write(ExcelXorHtmlUtil.excelToHtml(params).getBytes());
    }
}
