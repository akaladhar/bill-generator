package com.bjss.assignment.discount;

import com.bjss.assignment.util.PricesDiscountsLoader;
import com.esotericsoftware.yamlbeans.YamlReader;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class DiscountCalculatorTest {
    private YamlReader yamlReader;
    private DiscountCalculator discountCalculator;

    @Before
    public void setUp() throws Exception{
        discountCalculator = new DiscountCalculator();
        yamlReader = new YamlReader(new InputStreamReader(PricesDiscountsLoader.class.getResourceAsStream("/prices.yml")));
        PricesDiscountsLoader.loadData("/prices.yml");
    }

    @Test
    public void testGetDiscounts() {
        String[] items = {"Milk", "Apples", "Bread"};

        List<Discount> discounts = discountCalculator.getDiscounts(Arrays.asList(items));
        assertEquals("Number of discounts did not match", discounts.size(), 1);
        assertEquals("Discount for Apples is not loaded", discounts.get(0).getItem(), "Apples");

        items = new String[]{"Milk", "Apples", "Bread", "Soup", "Soup"};
        discounts = discountCalculator.getDiscounts(Arrays.asList(items));
        assertEquals("Number of discounts did not match", discounts.size(), 2);
        assertEquals("Discount for Apples is not loaded", discounts.get(0).getItem(), "Apples");
        assertEquals("Discount for Bread is not loaded", discounts.get(1).getItem(), "Bread");

    }

    @Test
    public void testGetDiscountsTotal() {
        String[] items = new String[]{"Milk", "Apples", "Bread", "Soup", "Soup"};
        List<Discount> discounts = discountCalculator.getDiscounts(Arrays.asList(items));

        BigDecimal discountsTotal =  discountCalculator.getDiscountsTotal(discounts);

        assertEquals( discountsTotal.doubleValue(), 0.5, 0);
    }

}
