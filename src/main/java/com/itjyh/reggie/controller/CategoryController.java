package com.itjyh.reggie.controller;/**
 * @Author: jiayuhang
 * @Date: 2022/09/15/10:35
 * @Description:
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itjyh.reggie.common.R;
import com.itjyh.reggie.entity.Category;
import com.itjyh.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yuhang
 * @version 1.0
 * @description: TODO
 * @date 2022/9/15 10:35
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /*新增
    分类*/
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("category:{}", category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    /*分页查询*/
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {
//        log.info("page={},pageSize={}", page, pageSize);
//        构造分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);
//        构造条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
////        添加过滤条件
//        queryWrapper.like(StringUtils.isNotEmpty(name), Category::getName, name);
//        添加排序条件
        queryWrapper.orderByAsc(Category::getSort);

//        执行查询
        categoryService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }


    /*
     * @Author jiayuhang
     * @Date 14:46 2022/9/15
     * @Param 根据id删除分类
     * @return
     **/
    @DeleteMapping
    public R<String>delete(Long id){
        log.info("删除分类,id为:{}",id);

//        categoryService.removeById(id);
        categoryService.remove(id);
        return R.success("分类信息删除成功");
    }

    /*
     * @Author jiayuhang
     * @Date 16:08 2022/9/15
     * @Param
     * @return
     *修改菜品种类*/
    @PutMapping
    public R<String>update(@RequestBody Category category){  //RequestBoay返回Json格式
      log.info("修改分类信息:{}",category);
      categoryService.updateById(category);
        return R.success("修改分类信息成功");
    }

    /*根据条件查询分类数据*/
    @GetMapping("/list")
    public R<List<Category>>list(Category category){
//        条件构造器
        LambdaQueryWrapper<Category>queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
//        添加排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }


    /**/



}
