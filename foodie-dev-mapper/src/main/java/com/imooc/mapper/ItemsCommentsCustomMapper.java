package com.imooc.mapper;

import com.imooc.pojo.vo.center.MyCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsCommentsCustomMapper{
    /**
     * 保存自己的评价
     * @param map 入参
     */
    void saveComments(Map<String, Object> map);

    /**
     * 查询自己评价的数据
      * @param map 入参
     * @return 返回结果
     */
    List<MyCommentVO> queryMyComments(@Param(value = "param") Map map);

}