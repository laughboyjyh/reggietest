package com.itjyh.reggie.service;/**
 * @Author: jiayuhang
 * @Date: 2022/09/15/15:12
 * @Description:
 */

import com.baomidou.mybatisplus.extension.service.IService;
import com.itjyh.reggie.dto.DishDTO;
import com.itjyh.reggie.dto.SetmealDTO;
import com.itjyh.reggie.entity.Setmeal;

import java.util.List;

/**
 * @author yuhang
 * @version 1.0
 * @description: TODO
 * @date 2022/9/15 15:12
 */
public interface SetmealService extends IService<Setmeal> {

  //新增套餐,同时保存套餐和菜品的关联关系,对应setmeal和setmealdish
    void saveWithSetmeal(SetmealDTO setmealDTO);
  //    根据id查询菜品信息和套餐信息
  public SetmealDTO getByIdWithDish(Long id);
  //修改套餐信息
   public void updateWithDish(SetmealDTO setmealDTO);

  void deleteWithDish(List<Long> id);
}
