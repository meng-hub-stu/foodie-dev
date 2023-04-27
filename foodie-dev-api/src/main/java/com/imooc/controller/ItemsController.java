package com.imooc.controller;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.ItemInfoVO;
import com.imooc.service.ItemsService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品控制类
 * @author Mengdl
 * @date 2021/11/11
 */
@Api(value = "商品展示", tags = {"商品相关的接口数据"})
@RestController
@RequestMapping(value = "items")
public class ItemsController {

    @Autowired
    private ItemsService itemsService;

    @GetMapping(value = "info/{itemId}")
    @ApiOperation(value = "获取商品的详情", notes = "传入商品的id", httpMethod = "GET")
    public IMOOCJSONResult info(@ApiParam(value = "商品的id", name = "itemId", required = true)
                                @PathVariable(value = "itemId") String itemId){
        Items items = itemsService.queryItemById(itemId);
        List<ItemsImg> itemsImgList = itemsService.queryItemImageList(itemId);
        List<ItemsSpec> itemsSpecList = itemsService.queryItemSpecList(itemId);
        ItemsParam itemParam = itemsService.queryItemParam(itemId);
        return IMOOCJSONResult.ok(new ItemInfoVO(items, itemsImgList, itemsSpecList, itemParam));
    }

    @GetMapping(value = "commentLevel")
    @ApiOperation(value = "商品的评价等级", notes = "传入商品的id", httpMethod = "GET")
    public IMOOCJSONResult commentLevel(@ApiParam(value = "商品的id", name = "itemId", required = true)
                                        @RequestParam(value = "itemId") String itemId){
        return IMOOCJSONResult.ok(itemsService.queryCommentLevel(itemId));
    }

    @GetMapping(value = "comments")
    @ApiOperation(value = "商品的评价", notes = "传入参数", httpMethod = "GET")
    public IMOOCJSONResult comments(@ApiParam(value = "商品的id", name = "itemId", required = true)
                                    @RequestParam(value = "itemId") String itemId,
                                    @ApiParam(value = "商品的等级", name = "level", required = true)
                                    @RequestParam(value = "level") Integer level,
                                    @ApiParam(value = "页码", name = "page", required = true)
                                    @RequestParam(value = "page", defaultValue = "1") Integer page,
                                    @ApiParam(value = "条数", name = "pageSize", required = true)
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        return IMOOCJSONResult.ok(itemsService.queryCommentByPage(itemId, level, page, pageSize));
    }

    @GetMapping(value = "search")
    @ApiOperation(value = "搜索商品列表", notes = "参数", httpMethod = "GET")
    public IMOOCJSONResult search(
            @ApiParam(value = "关键字", name = "keywords", required = true)
            @RequestParam(value = "keywords") String keywords,
            @ApiParam(value = "排序", name = "sort", required = true)
            @RequestParam(value = "sort") String sort,
            @ApiParam(value = "页码", name = "page", required = true)
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @ApiParam(value = "条数", name = "pageSize", required = true)
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ){
        if(StringUtils.isBlank(keywords)){
            return IMOOCJSONResult.errorMsg(null);
        }
        return IMOOCJSONResult.ok(itemsService.searchItems(keywords, sort, page, pageSize));
    }

    @GetMapping(value = "refresh")
    @ApiOperation(value = "商品规格ids刷新最新的商品-购物车", notes = "商品规格的ids", httpMethod = "GET")
    public IMOOCJSONResult refresh(
            @ApiParam(value = "商品规格id拼接", name = "itemSpecIds", required = true, example = "1001,1002")
            @RequestParam(value = "itemSpecIds") String itemSpecIds
    ){
        return IMOOCJSONResult.ok(itemsService.queryItemsBySpecIds(itemSpecIds));
    }


}
