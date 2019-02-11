package com.bjss.assignment.dao;

import com.bjss.assignment.discount.Discount;

import java.util.HashMap;
import java.util.Map;

public class DiscountsDao {

    private static Map<String, Discount> discounts = new HashMap<>();

    public static Discount getDiscount(String item) {
        return discounts.get(item);
    }

    public static void addDiscount(String item, Discount discount) {
        discounts.put(item, discount);
    }

}
