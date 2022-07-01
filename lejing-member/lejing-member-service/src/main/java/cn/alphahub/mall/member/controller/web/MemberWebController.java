package cn.alphahub.mall.member.controller.web;

import cn.alphahub.common.core.domain.Result;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.feign.OrderClient;
import cn.alphahub.mall.order.dto.vo.OrderVo;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 会员订单页Controller
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/06/27
 */
@Slf4j
@Controller
public class MemberWebController {
    @Resource
    private OrderClient orderClient;

    /**
     * 获取当前会员的所有订单列表数据
     *
     * @return 订单列表
     */
    @GetMapping(value = "/memberOrder.html")
    public String memberOrderPage(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                  Model model,
                                  HttpServletRequest request
    ) {
        var domain = new PageDomain();
        domain.setPage(pageNum);
        domain.setRows(100);
        Result<PageResult<OrderVo>> result = orderClient.getMemberOrderList(domain);
        log.info("当前登录用的订单数据:{}", JSONUtil.toJsonPrettyStr(result));
        model.addAttribute("orders", result);
        return "orderList";
    }
}
