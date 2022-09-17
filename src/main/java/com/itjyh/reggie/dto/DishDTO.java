package com.itjyh.reggie.dto;


import com.itjyh.reggie.entity.Dish;
import com.itjyh.reggie.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDTO extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
