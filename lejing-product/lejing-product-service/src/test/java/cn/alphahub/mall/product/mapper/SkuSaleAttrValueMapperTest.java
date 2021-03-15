package cn.alphahub.mall.product.mapper;

import cn.alphahub.mall.product.vo.SkuItemSaleAttrVO;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * <b>输入描述</b>
 *
 * @author liuwe
 * @version 1.0
 * @date 2021/03/16
 */
public class SkuSaleAttrValueMapperTest {
    private static SkuSaleAttrValueMapper mapper;

    @BeforeEach
    public void setUpMybatisDatabase() {
        SqlSessionFactory builder = new SqlSessionFactoryBuilder().build(SkuSaleAttrValueMapperTest.class.getClassLoader().getResourceAsStream("mybatisTestConfiguration/SkuSaleAttrValueMapperTestConfiguration.xml"));
        //you can use builder.openSession(false) to not commit to database
        mapper = builder.getConfiguration().getMapper(SkuSaleAttrValueMapper.class, builder.openSession(true));
    }

    @Test
    public void testGetSaleAttrBySpuId() {
        List<SkuItemSaleAttrVO> saleAttrBySpuId = mapper.getSaleAttrBySpuId(13L);
        for (SkuItemSaleAttrVO attrVO : saleAttrBySpuId) {
            System.out.println(attrVO);
        }
    }
}
