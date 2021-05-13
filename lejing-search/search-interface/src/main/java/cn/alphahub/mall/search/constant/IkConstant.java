package cn.alphahub.mall.search.constant;

/**
 * IK分词器常量类
 * <p>
 *
 * @author lwj
 * @version 1.0
 * @date 2021-05-13 13:45
 */
public class IkConstant {

    /**
     * 最粗粒度的拆分
     * <p>比如会将“中华人民共和国国歌”拆分为“中华人民共和国,国歌”，适合 Phrase 查询</p>
     */
    public static final String IK_SMART = "ik_smart";

    /**
     * 最大化会将文本做最细粒度的拆分
     * <p>比如会将“中华人民共和国国歌”拆分为“中华人民共和国,中华人民,中华,华人,人民共和国,人民,人,民,共和国,共和,和,国国,国歌”，会穷尽各种可能的组合，适合 Term Query；</p>
     */
    public static final String IK_MAX_WORD = "ik_max_word";

}
