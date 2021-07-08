package cn.alphahub.mall.order.excel;

import cn.alphahub.mall.order.excel.domain.User;
import cn.alphahub.mall.order.excel.easyexcel.dto.OrderItemExcelDTO;
import cn.alphahub.mall.order.excel.easyexcel.listener.EasyExcelEventListener;
import cn.alphahub.mall.order.excel.read.CommonExcelListener;
import cn.hutool.core.io.FileUtil;
import com.alibaba.excel.EasyExcel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * easy excel tests
 *
 * @author liuwe
 * @version 1.0
 * @date 2021/07/08
 */
@SpringBootTest
public class EasyExcelTests {

    @Resource
    private CommonExcelListener<User> commonExcelListener;
    @Resource
    private EasyExcelEventListener<OrderItemExcelDTO> orderItemExcelReadListener;

    @BeforeEach
    void setUp() {
        System.err.println("-------------------------");
    }

    @AfterEach
    void tearDown() {
        System.err.println("-------------------------");
    }

    @Test
    void testWrite() {
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(i);
            user.setName("easy excel tests" + i);
            users.add(user);
        }
        String file = "E:\\test.xlsx";
        EasyExcel.write(file, User.class).sheet("表1").doWrite(users);
    }

    /**
     * 直接new的方式
     *
     * @throws IOException
     */
    @Test
    void testRead() throws IOException {
        String file = "E:\\test.xlsx";
        EasyExcel.read(file, User.class, new CommonExcelListener<User>()).sheet().doRead();
    }

    /**
     * 交给Spring IOC托管的方式,Bean的生命周期: 原型模式
     */
    @Test
    void testReadInputStream() {
        String file = "E:\\test.xlsx";
        BufferedInputStream inputStream = FileUtil.getInputStream(new File(file));
        EasyExcel.read(inputStream, User.class, commonExcelListener).sheet().doRead();
    }


    @Test
    void testRead2() {
        String file = "E:\\子订单项数据.xlsx";
        BufferedInputStream inputStream = FileUtil.getInputStream(new File(file));
        EasyExcel.read(inputStream, OrderItemExcelDTO.class, orderItemExcelReadListener).sheet().doRead();
    }
}
