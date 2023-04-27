package com.imooc.controller;

import com.imooc.service.SearchService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Mengdexin
 * @date 2022 -01 -08 -12:00
 */
@RestController
@RequestMapping(value = "items")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping(value = "/hello")
    private String hello(){
        return "hello elasticsearch";
    }

    @GetMapping(value = "/es/search")
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

        page -- ;

        return IMOOCJSONResult.ok(searchService.searchItems(keywords, sort, page, pageSize));
    }

}
