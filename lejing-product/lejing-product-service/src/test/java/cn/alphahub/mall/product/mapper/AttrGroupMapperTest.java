package cn.alphahub.mall.product.mapper;

import cn.alphahub.mall.product.vo.SpuItemAttrGroupVO;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * <b>AttrGroupMapper测试</b>
 *
 * @author liuwe
 * @version 1.0
 * @date 2021/03/16
 */
public class AttrGroupMapperTest {
    private static AttrGroupMapper mapper;

    @BeforeEach
    public void setUpMybatisDatabase() {
        SqlSessionFactory builder = new SqlSessionFactoryBuilder().build(AttrGroupMapperTest.class.getClassLoader().getResourceAsStream("mybatisTestConfiguration/AttrGroupMapperTestConfiguration.xml"));
        //you can use builder.openSession(false) to not commit to database
        mapper = builder.getConfiguration().getMapper(AttrGroupMapper.class, builder.openSession(true));
    }

    @Test
    public void testListBySpuIdAndCatalogId() {
        List<SpuItemAttrGroupVO> groupVOS = mapper.listBySpuIdAndCatalogId(225L, 13L);
        groupVOS.forEach(spuItemAttrGroupVO -> System.out.println(groupVOS));
    }
}
