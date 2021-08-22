package cn.alphahub.mall.generator.controller;

import cn.alphahub.mall.generator.service.SysGeneratorService;
import cn.alphahub.mall.generator.utils.GenUtils;
import cn.alphahub.mall.generator.utils.PageUtils;
import cn.alphahub.mall.generator.utils.Query;
import cn.alphahub.mall.generator.utils.R;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 代码生成器
 *
 * @author Mark sunlightcs@gmail.com
 */
@RefreshScope
@Controller
@RequestMapping("/sys/generator")
public class SysGeneratorController {

    @Resource
    private SysGeneratorService sysGeneratorService;

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils pageUtil = sysGeneratorService.queryList(new Query(params));
        return R.ok().put("page", pageUtil);
    }

    /**
     * 生成代码
     */
    @RequestMapping("/code")
    public void code(String tables, HttpServletResponse response) throws IOException {
        Configuration config = GenUtils.getCodeGeneratorConfig();
        String zipFileName = config.getString("codeZipFileName");
        zipFileName = StringUtils.isNotEmpty(zipFileName) ? zipFileName : "renren";
        byte[] data = sysGeneratorService.generatorCode(tables.split(","));
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + zipFileName + ".zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
