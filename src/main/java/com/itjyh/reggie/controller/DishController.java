package com.itjyh.reggie.controller;/**
 * @Author: jiayuhang
 * @Date: 2022/09/15/17:31
 * @Description:
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itjyh.reggie.common.R;
import com.itjyh.reggie.dto.DishDTO;
import com.itjyh.reggie.entity.Category;
import com.itjyh.reggie.entity.Dish;
import com.itjyh.reggie.entity.Employee;
import com.itjyh.reggie.service.CategoryService;
import com.itjyh.reggie.service.DishFlavorService;
import com.itjyh.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yuhang
 * @version 1.0
 * @description: TODO
 * @date 菜品管理
 */
@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {
    @Autowired
    DishService dishService;
    @Autowired
    DishFlavorService dishFlavorService;
    @Autowired
    CategoryService categoryService;
//    @Autowired
//    DishDTO dishDTO;

    @PostMapping
    public R<String> save(@RequestBody DishDTO dishDTO) { //提交得是Json数据所以必须加@RequestBody
//        log.info(dishDTO.toString());
        dishService.saveWithFlavor(dishDTO);
        return R.success("新增菜品成功");
    }

    /*分页界面\
    * */
    @GetMapping("/page")  //多表分页查询需要DTO
    public R<Page> page(int page, int pageSize, String name) {
        log.info("page={},pageSize={},name={}", page, pageSize, name);
//        构造分页构造器
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDTO> dishDTOPage = new Page<>();

//        构造条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();

//        添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name), Dish::getName, name);

//        添加排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);
//        执行查询
        dishService.page(pageInfo, queryWrapper);
//        对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDTOPage, "records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDTO> list = records.stream().map((item) -> {
            DishDTO dishDTO = new DishDTO();
            BeanUtils.copyProperties(item, dishDTO);
            Long categoryId = item.getCategoryId();
//            根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                dishDTO.setCategoryName(categoryName);
            }
            return dishDTO;
        }).collect(Collectors.toList());
        dishDTOPage.setRecords(list);


        return R.success(dishDTOPage);
    }

    /*
     * @Author jiayuhang
     * @Date 10:16 2022/9/16
     * @Param 根据id查询菜品信息和对应口味数据.菜品表和口味表
     * @return
     **/
    @GetMapping("/{id}")   //如果id在请求路径里面就要用@PathVariable，直接以“？”的形式拼接在路径后面就直接接收即可
    public R<DishDTO>get(@PathVariable Long id){
        DishDTO dishDTO = dishService.getByIdWithFlavor(id);
    return R.success(dishDTO);
    }


    /*修改菜品*/
    @PutMapping
    public R<String> update(@RequestBody DishDTO dishDTO) { //提交得是Json数据所以必须加@RequestBody
//        log.info(dishDTO.toString());
//        更新dish表和flavor表
        dishService.updateWithFlavor(dishDTO);
        return R.success("修改菜品成功");
    }


    
    /*
     * @Author jiayuhang
     * @Date 14:40 2022/9/16
     * @Param 新建套餐,添加菜品列表
     * @return 
     **/

   @GetMapping("/list")
   /*根据条件查询分类数据*/
    public R<List<Dish>>list(Dish dish){  //Dish接受通用性好点,也可以用DishId
//        条件构造器
        LambdaQueryWrapper<Dish>queryWrapper=new LambdaQueryWrapper<>();
       queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
//        添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);
        return R.success(list);
    }

}


