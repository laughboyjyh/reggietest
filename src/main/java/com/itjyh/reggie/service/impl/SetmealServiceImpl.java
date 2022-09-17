package com.itjyh.reggie.service.impl;/**
 * @Author: jiayuhang
 * @Date: 2022/09/15/15:15
 * @Description:
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itjyh.reggie.common.CustomException;
import com.itjyh.reggie.dto.DishDTO;
import com.itjyh.reggie.dto.SetmealDTO;
import com.itjyh.reggie.entity.Dish;
import com.itjyh.reggie.entity.DishFlavor;
import com.itjyh.reggie.entity.Setmeal;
import com.itjyh.reggie.entity.SetmealDish;
import com.itjyh.reggie.mapper.SetmealMapper;
import com.itjyh.reggie.service.SetmealDishService;
import com.itjyh.reggie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yuhang
 * @version 1.0
 * @description: TODO
 * @date 2022/9/15 15:15
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper
        , Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;

    @Transactional  //事务注解

    public void saveWithSetmeal(SetmealDTO setmealDTO) {
//    保存套餐信息,操作setmeal .insert
        this.save(setmealDTO);


        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDTO.getId());
            return item;
        }).collect(Collectors.toList());


//    保存套餐和菜品的关联.setmeal-dish,
        setmealDishService.saveBatch(setmealDishes);


    }


    @Override
    public SetmealDTO getByIdWithDish(Long id) {
        //查询套餐的基本信息，从数据表中查询
        Setmeal setmeal = this.getById(id);
        SetmealDTO setmealDTO = new SetmealDTO();

        BeanUtils.copyProperties(setmeal, setmealDTO);

        //查询当前套餐下的具体菜品
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, id);
        List<SetmealDish> list = setmealDishService.list(queryWrapper);
        setmealDTO.setSetmealDishes(list);

        return setmealDTO;
    }

    @Override
    public void updateWithDish(SetmealDTO setmealDTO) {
        //查询套餐状态，确定是否可以修改
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();

        List<Long> list = new ArrayList<>();
        list.add(setmealDTO.getId());
        queryWrapper.in(Setmeal::getId, list);
        queryWrapper.eq(Setmeal::getStatus, 1);

        int count = this.count(queryWrapper);

        if (count > 0) {
            //如果不能修改，抛出业务运行异常
            throw new CustomException("套餐正在售卖中，不能修改");
        }

        //如果可以修改，则先删除表中的对应数据
        this.removeByIds(list);

        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId, list);

        //删除记录套餐中具体餐品的表中的指定数据
        setmealDishService.remove(lambdaQueryWrapper);


        //saveWithDish(setmealDTO);

        //保存套餐的基本信息，操作setmeal，执行insert操作
        this.save(setmealDTO);

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();

        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDTO.getId());
            return item;
        }).collect(Collectors.toList());

        //保存套餐和菜品的关联信息，操作setmeal_dish,执行insert操作
        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    @Transactional
    public void deleteWithDish(List<Long> list) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,list);
        queryWrapper.eq(Setmeal::getStatus,1);

        int count = this.count(queryWrapper);
        if(count > 0){
            //如果不能删除，抛出业务异常
            throw new CustomException("套餐正在售卖中，不能删除");
        }

        //如果可以删除，先删除套餐表中的数据---setmeal
        this.removeByIds(list);

        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,list);

        //删除关系表中的数据----setmeal_dish
        setmealDishService.remove(lambdaQueryWrapper);

    }
}


