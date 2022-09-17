package com.itjyh.reggie.mapper;/**
 * @Author: jiayuhang
 * @Date: 2022/09/15/15:06
 * @Description:
 */

/**
 * @author yuhang
 * @version 1.0
 * @description: TODO
 * @date 2022/9/15 15:06
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itjyh.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
