package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.enums.CommentLevel;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.*;
import com.imooc.pojo.*;
import com.imooc.pojo.vo.CommentLevelCountsVO;
import com.imooc.pojo.vo.ItemCommentVO;
import com.imooc.pojo.vo.SearchItemsVO;
import com.imooc.pojo.vo.ShopcartVO;
import com.imooc.service.ItemsService;
import com.imooc.utils.DesensitizationUtil;
import com.imooc.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品的实现类
 * @author Mengdl
 * @date 2021/11/11
 */
@Service
public class ItemsServiceImpl implements ItemsService {

    @Autowired
    private ItemsMapper itemsMapper;
    @Autowired
    private ItemsImgMapper itemsImgMapper;
    @Autowired
    private ItemsSpecMapper itemsSpecMapper;
    @Autowired
    private ItemsParamMapper itemsParamMapper;
    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;
    @Autowired
    private ItemsMapperCustom itemsMapperCustom;



    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public Items queryItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<ItemsImg> queryItemImageList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsImgMapper.selectByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<ItemsSpec> queryItemSpecList(String itemId) {
//        Example example = new Example(ItemsSpec.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("itemId", itemId);
        ItemsSpec itemsSpec = new ItemsSpec();
        itemsSpec.setItemId(itemId);
        return itemsSpecMapper.select(itemsSpec);
//        return itemsSpecMapper.selectByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public ItemsParam queryItemParam(String itemId) {
        Example example = new Example(ItemsParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsParamMapper.selectOneByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public CommentLevelCountsVO queryCommentLevel(String itemId) {
        Integer goodComment = getCommentCount(itemId, CommentLevel.GOOD.type);
        Integer normalComment = getCommentCount(itemId, CommentLevel.NORMAL.type);
        Integer badComment = getCommentCount(itemId, CommentLevel.BAD.type);
        Integer totalComment = goodComment + normalComment + badComment;
        return new CommentLevelCountsVO(goodComment,normalComment, badComment, totalComment);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    Integer getCommentCount(String itemId, Integer level){
        return itemsCommentsMapper.selectCount(new ItemsComments(itemId, level));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public PagedGridResult queryCommentByPage(String itemId, Integer level, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        Map<String, Object> param = new HashMap<>(3);
        param.put("itemId", itemId);
        param.put("commentLevel", level);
        List<ItemCommentVO> itemsComments = itemsMapperCustom.queryCommentByPage(param);
        itemsComments.forEach(v ->v.setNickname(DesensitizationUtil.commonDisplay(v.getNickname())));
        return setterPagedGridResult(page, itemsComments);
    }

    private PagedGridResult setterPagedGridResult(Integer page, List<?> params){
        PageInfo<?> pageList = new PageInfo<>(params);
        PagedGridResult result = new PagedGridResult();
        result.setPage(page);
        result.setRows(params);
        result.setTotal(pageList.getPages());
        result.setRecords(pageList.getTotal());
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        Map<String, Object> param = new HashMap<>(3);
        param.put("keywords", keywords);
        param.put("sort", sort);
        List<SearchItemsVO> result = itemsMapperCustom.searchItems(param);
        return setterPagedGridResult(page, result);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<ShopcartVO> queryItemsBySpecIds(String itemSpecIds) {
        String[] specIds = itemSpecIds.split(",");
        List<String> specIdsList = Arrays.stream(specIds).collect(Collectors.toList());
        return itemsMapperCustom.queryItemsBySpecIds(specIdsList);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public ItemsSpec queryItemSpecById(String itemSpecId) {
        return itemsSpecMapper.selectByPrimaryKey(itemSpecId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public ItemsImg queryItemImageByItemId(String itemId) {
        ItemsImg itemsImg = new ItemsImg();
        itemsImg.setItemId(itemId);
        itemsImg.setIsMain(YesOrNo.YES.type);
        return itemsImgMapper.selectOne(itemsImg);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void decreaseItemSpecStork(String specId, int buyCounts) {
        // synchronized 不推荐使用，集群下无用，性能低下
        // 锁数据库: 不推荐，导致数据库性能低下
        // 分布式锁 zookeeper redis
//         lockUtil.getLock(); -- 加锁

        // 1. 查询库存
//        int stock = 10;

        // 2. 判断库存，是否能够减少到0以下
//        if (stock - buyCounts < 0) {
        // 提示用户库存不够
//            10 - 3 -3 - 5 = -1
//        }

        // lockUtil.unLock(); -- 解锁
        int result = itemsMapperCustom.decreaseItemSpecStork(specId, buyCounts);
        if(result != 1){
            throw new RuntimeException("订单创建失败，原因，库存不足");
        }
    }
}
