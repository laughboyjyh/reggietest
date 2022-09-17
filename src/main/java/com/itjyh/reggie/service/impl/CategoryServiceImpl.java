package com.itjyh.reggie.service.impl;/**
 * @Author: jiayuhang
 * @Date: 2022/09/15/10:30
 * @Description:
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itjyh.reggie.common.CustomException;
import com.itjyh.reggie.entity.Category;
import com.itjyh.reggie.entity.Dish;
import com.itjyh.reggie.entity.Setmeal;
import com.itjyh.reggie.mapper.CategoryMapper;
import com.itjyh.reggie.service.CategoryService;

import com.itjyh.reggie.service.DishService;
import com.itjyh.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author yuhang
 * @version 1.0
 * @description: TODO
 * @date 2022/9/15 10:30
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>implements CategoryService {
   @Autowired
   private DishService dishService;
   @Autowired
   private SetmealService setmealService;
    /*
     * @Author jiayuhang
     * @Date 15:20 2022/9/15
     * @Param 根据id删除前,进行判断
     * @return void
     **/
    @Override
    public void remove(Long id){
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper=new LambdaQueryWrapper<>();
//添加查询条件,根据分类进行查询是否关联菜品
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id); //查询Category的id和Dish表中Category.id相同的数量
        int count1 = dishService.count(dishLambdaQueryWrapper);
//查询当前分类是否关联菜品,如果已经关联,抛出异常
        if(count1>0){
//已关联菜品,抛出异常
            throw new CustomException("当前分类项已关联菜品,不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=
                new LambdaQueryWrapper<>();
//        添加查询条件,根据分类查看时是否关联套餐
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2= setmealService.count(setmealLambdaQueryWrapper);
//        查询当前分类是否关联套餐,如果已经关联,抛出异常
        if (count2>0){
//            已关联套餐，抛出异常
            throw new CustomException("当前分类项已关联套餐,不能删除");
        }
//        正常删除分类
        super.removeById(id);


    }

}
