package com.itjyh.reggie.mapper;/**
 * @Author: jiayuhang
 * @Date: 2022/09/15/10:29
 * @Description:
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itjyh.reggie.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yuhang
 * @version 1.0
 * @description: TODO
 * @date 2022/9/15 10:29
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
