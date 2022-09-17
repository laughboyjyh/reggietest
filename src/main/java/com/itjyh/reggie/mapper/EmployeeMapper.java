package com.itjyh.reggie.mapper;/**
 * @Author: jiayuhang
 * @Date: 2022/09/14/11:27
 * @Description:
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itjyh.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yuhang
 * @version 1.0
 * @description: TODO
 * @date 2022/9/14 11:27
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
