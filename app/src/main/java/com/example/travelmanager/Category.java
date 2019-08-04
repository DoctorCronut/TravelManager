package com.example.travelmanager;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    List<Data> cat_list = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public float calculte_expense_sum(){
        float sum = 0;
        for (Data d:cat_list){
            sum += d.getmExpense();
        }
        return sum;
    }
}
