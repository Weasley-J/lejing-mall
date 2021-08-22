package cn.alphahub.mall.order.mapper;

import cn.alphahub.mall.order.dto.request.OrderDetailReq;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 输入类描述
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/08/12
 */
public class OrderMapperTest {

    private static OrderMapper mapper;

    @BeforeEach
    public void setUpMybatisDatabase() {
        SqlSessionFactory builder = new SqlSessionFactoryBuilder().build(OrderMapperTest.class.getClassLoader().getResourceAsStream("mybatisTestConfiguration/OrderMapperTestConfiguration.xml"));
        //you can use builder.openSession(false) to not commit to database
        mapper = builder.getConfiguration().getMapper(OrderMapper.class, builder.openSession(true));
    }

    @Test
    void testSelectOrderDetail() {
        mapper.selectOrderDetail(new OrderDetailReq("202106202209191061406615145992024065"));
    }
}
