package com.bjss.assignment.dao;

import com.bjss.assignment.exception.ItemNotFoundException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class PricesDao {

    private static Map<String, BigDecimal> prices = new HashMap<>();

    public static BigDecimal getPrice(String item) throws ItemNotFoundException {
        if (prices.get(item) == null) {
            throw new ItemNotFoundException("Item "+item + " not found in the price list");
        }
        return prices.get(item);
    }

    public static void addPrice(String item, BigDecimal cost) {
        prices.put(item, cost);
    }
}
