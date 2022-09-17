package com.itjyh.reggie.service.impl;/**
 * @Author: jiayuhang
 * @Date: 2022/09/15/15:13
 * @Description:
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itjyh.reggie.dto.DishDTO;
import com.itjyh.reggie.entity.Dish;
import com.itjyh.reggie.entity.DishFlavor;
import com.itjyh.reggie.mapper.DishMapper;
import com.itjyh.reggie.service.DishFlavorService;
import com.itjyh.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yuhang
 * @version 1.0
 * @description: TODO
 * @date 2022/9/15 15:13
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;

    //操作dish和dishflavos两张表,新增菜品同时保存对应口味数据
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
//        菜品信息保存到菜品表
        this.save(dishDTO);
        Long dishId = dishDTO.getId();
//        菜品口味
        List<DishFlavor> flavors = dishDTO.getFlavors();
        flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

//        保存菜品口味数据到菜品口味表
        dishFlavorService.saveBatch(dishDTO.getFlavors());
    }


    public DishDTO getByIdWithFlavor(Long id) {
//        查询菜品基本信息。从dish表查询
        Dish dish = this.getById(id);
        DishDTO dishDTO = new DishDTO();
        BeanUtils.copyProperties(dish, dishDTO);
//        查询菜品口味信息，从dish—flavor表查询
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDTO.setFlavors(flavors);
        return dishDTO;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDTO dishDTO) {
//        更新dish表基本信息
        this.updateById(dishDTO);
//        清理当前菜品对应口味数据 -- dish_flavor表的delete操作
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDTO.getId());
//        添加当前提交的口味数据   --dish_flavor表的insert操作
        dishFlavorService.remove(queryWrapper);
//        添加当前提交的口味数据 --dish_flavor表的insert
        List<DishFlavor>flavors= dishDTO.getFlavors();
        flavors=flavors.stream().map((item) -> {
            item.setDishId(dishDTO.getId());
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);


    }

}
