package com.itjyh.reggie.dto;

import com.itjyh.reggie.entity.Setmeal;
import com.itjyh.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDTO extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
