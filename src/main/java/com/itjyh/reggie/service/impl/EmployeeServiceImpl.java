package com.itjyh.reggie.service.impl;/**
 * @Author: jiayuhang
 * @Date: 2022/09/14/11:32
 * @Description:
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itjyh.reggie.entity.Employee;
import com.itjyh.reggie.mapper.EmployeeMapper;
import com.itjyh.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @author yuhang
 * @version 1.0
 * @description: TODO
 * @date 2022/9/14 11:32
 */


@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService{
}


