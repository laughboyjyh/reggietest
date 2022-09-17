package com.itjyh.reggie.service;/**
 * @Author: jiayuhang
 * @Date: 2022/09/15/10:30
 * @Description:
 */

import com.baomidou.mybatisplus.extension.service.IService;
import com.itjyh.reggie.entity.Category;

/**
 * @author yuhang
 * @version 1.0
 * @description: TODO
 * @date 2022/9/15 10:30
 */
public interface CategoryService extends IService<Category> {
    /*
     * @Author jiayuhang
     * @Date 15:20 2022/9/15
     * @Param 根据id删除前,进行判断
     * @return void
     **/
    void remove(Long id);
}
