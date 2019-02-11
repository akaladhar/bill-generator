package com.bjss.assignment.util;

import com.bjss.assignment.dao.DiscountsDao;
import com.bjss.assignment.dao.PricesDao;
import com.bjss.assignment.discount.Discount;
import com.bjss.assignment.discount.DiscountType;
import com.esotericsoftware.yamlbeans.YamlReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Map;

import static org.junit.Assert.*;

public class PricesDiscountsLoaderTest {

    private YamlReader yamlReader;
    private Map<String, Object> priceData;

    @Before
    public void setUp() throws Exception{

        yamlReader = new YamlReader(new InputStreamReader(PricesDiscountsLoader.class.getResourceAsStream("/prices.yml")));
        priceData =  (Map)yamlReader.read();
    }

    @After
    public void closeResources() throws Exception {
        yamlReader.close();
    }

    @Test
    public void testGetDiscountMultiBuy() {

        final String item = "Bread";

        Map<String, Object> data = (Map)priceData.get(item);
        Discount discount = PricesDiscountsLoader.getDiscount(data, item);

        assertNotNull("Retrieved invalid discount", discount);
        assertEquals("Item name did not match", discount.getItem(), "Bread");
        assertEquals("Discount did not match", discount.getPercentage().intValue(), 50);
        assertEquals("Onitem did not match", discount.getOnItem(), "Soup");
        assertEquals("Number of items did not match", discount.getNumberOfOnItems(), 2);
        assertEquals("Discount type did not match", discount.getDiscountType(), DiscountType.MULTIBUY);

    }

    @Test
    public void testGetDiscountFlat() {
        final String item = "Apples";

        Map<String, Object> data = (Map)priceData.get(item);
        Discount discount = PricesDiscountsLoader.getDiscount(data, item);

        assertNotNull("Retrieved invalid discount", discount);
        assertEquals("Item name did not match", discount.getItem(), "Apples");
        assertEquals("Discount did not match", discount.getPercentage().intValue(), 10);
        assertEquals("Discount type did not match", discount.getDiscountType(), DiscountType.FLAT);
    }

    @Test
    public void testGetPrice() {
        final String item = "Apples";
        Map<String, Object> data = (Map)priceData.get(item);
        BigDecimal price = PricesDiscountsLoader.getPrice(data);
        assertEquals("Price did not match", price.intValue(), 100);
    }

    @Test
    public void testLoadDataPrices() throws Exception {
        PricesDiscountsLoader.loadData("/prices.yml");

        assertNotNull("Apples price not loaded", PricesDao.getPrice("Apples"));
        assertNotNull("Soup price not loaded", PricesDao.getPrice("Soup"));
        assertNotNull("Bread price not loaded", PricesDao.getPrice("Bread"));
        assertNotNull("Milk price not loaded", PricesDao.getPrice("Milk"));
    }

    @Test
    public void testLoadDataDiscounts() throws Exception {
        PricesDiscountsLoader.loadData("/prices.yml");

        assertNotNull("Apples discount not loaded", DiscountsDao.getDiscount("Apples"));
        assertNotNull("Bread discount not loaded", DiscountsDao.getDiscount("Bread"));

    }
}
