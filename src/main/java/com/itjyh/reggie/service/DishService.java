package com.itjyh.reggie.service;/**
 * @Author: jiayuhang
 * @Date: 2022/09/15/15:11
 * @Description:
 */

import com.baomidou.mybatisplus.extension.service.IService;
import com.itjyh.reggie.dto.DishDTO;
import com.itjyh.reggie.entity.Dish;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yuhang
 * @version 1.0
 * @description: TODO
 * @date 2022/9/15 15:11
 */

public interface DishService extends IService<Dish> {

//新增菜品,同时插入菜品对应口味信息,对应dish和dishFlavor
    void saveWithFlavor(DishDTO dishDTO);
//    根据id查询菜品信息和口味信息
    public DishDTO getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDTO dishDTO);


}
