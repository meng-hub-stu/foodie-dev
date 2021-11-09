package com.imooc.controller;

import com.imooc.enums.YesOrNo;
import com.imooc.pojo.Carousel;
import com.imooc.service.CarouselService;
import com.imooc.service.CategoryService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 首页展示控制类
 * @Author Mengdexin
 * @date 2021 -11 -09 -21:31
 */
@RequestMapping(value = "index")
@RestController
@Api(value = "首页展示", tags = {"首页展示相关的接口"})
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "carousel")
    @ApiOperation(value = "首页轮播", notes = "传入是否展示参数", httpMethod = "GET")
    public IMOOCJSONResult carousel(){
        return IMOOCJSONResult.ok(carouselService.queryAll(YesOrNo.YES.type));
    }

    @GetMapping(value = "cats")
    @ApiOperation(value = "首页商品一级分类", notes = "传入一级分类的类别", httpMethod = "GET")
    public IMOOCJSONResult category(){
        return IMOOCJSONResult.ok(categoryService.queryAllRootLevelCat());
    }

    @GetMapping(value = "subCat/{rootCatId}")
    @ApiOperation(value = "商品二级和三级的目录", notes = "传入一级分类的id", httpMethod = "GET")
    public IMOOCJSONResult categorySubList(@ApiParam(value = "一级分类的id", name = "rootCatId", required = true)
                                           @PathVariable(value = "rootCatId") Integer rootCatId){
         if(rootCatId == null){
             return IMOOCJSONResult.errorMsg("不能为空");
         }
         return IMOOCJSONResult.ok(categoryService.querySubCat(rootCatId));
    }

}
