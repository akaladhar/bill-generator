package com.bjss.assignment.util;

import com.bjss.assignment.discount.Discount;
import com.bjss.assignment.discount.DiscountType;
import com.bjss.assignment.dao.DiscountsDao;
import com.bjss.assignment.dao.PricesDao;
import com.bjss.assignment.exception.DataLoadException;
import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Map;

/*
 * This class loads to prices and discounts to DAO classes from yaml file
 *
 * This is not an ideal implementation. To keep it in simple, prices and discounts are kept
 * in a file.
 */

public class PricesDiscountsLoader {

    private static final String PRICE_KEY = "price";
    private static final String DISCOUNT_KEY = "discount";
    private static final String DISCOUNT_TYPE_KEY = "discountType";
    private static final String NUMBER_OF_ITEMS_KEY = "num";
    private static final String ON_ITEM_KEY = "onitem";
    private static final String ITEM_KEY = "item";

    /**
     * Loads price and discount data to dao classes
     *
     * @param path
     * @throws IOException
     */
    public static void loadData(String path) throws IOException {

        YamlReader reader = null;

        try {
            reader = new YamlReader(new InputStreamReader(PricesDiscountsLoader.class.getResourceAsStream(path)));
            final Object object = reader.read();
            final Map priceDiscountData = (Map) object;

            for (Object key : priceDiscountData.keySet()) {

                final String item = (String) key;
                Map data = (Map) priceDiscountData.get(item);
                BigDecimal cost = getPrice(data);
                PricesDao.addPrice(item, getPrice(data));
                Logger.log("Loaded " + item + "-" + cost);
                Logger.log("Check if there are any discounts for item " + item);

                Discount discount = getDiscount(data, item);
                if (discount != null) {
                    Logger.log("Discount found for " + item);
                    DiscountsDao.addDiscount(item, discount);
                    Logger.log("Loaded discount " + discount);
                }
            }
        }
        catch(Exception e){
            Logger.log("Error occurred "+e.toString());
            throw new DataLoadException();
        }
        finally {
            if (reader != null) {
                reader.close();
            }
        }

    }

    /**
     * Reads price of an item from map
     */
    public static BigDecimal getPrice(Map<String, Object> data) {
        return BigDecimal.valueOf(Long.valueOf((String)data.get(PRICE_KEY)));
    }

    /**
     * Reads discounts of an item
     */
    public static Discount getDiscount(Map<String, Object> data, String item) {
        Discount discount = null;
        final Object discountPercentage = data.get(DISCOUNT_KEY);
        if (discountPercentage != null){
            discount = new Discount();
            discount.setItem(item);
            BigDecimal percentage = BigDecimal.valueOf(Long.valueOf((String)discountPercentage));
            discount.setPercentage(percentage);
            DiscountType discountType = DiscountType.valueOf((String)data.get(DISCOUNT_TYPE_KEY));
            discount.setDiscountType(discountType);
            if (discountType == DiscountType.MULTIBUY) {
                Map multiBuyInfo =  (Map)data.get(ON_ITEM_KEY);
                discount.setOnItem((String)multiBuyInfo.get(ITEM_KEY));
                discount.setNumberOfOnItems(Integer.parseInt((String)multiBuyInfo.get(NUMBER_OF_ITEMS_KEY)));
            }
            DiscountsDao.addDiscount(item, discount);
            Logger.log("Loaded discount "+ discount);
        }
        return discount;
    }
}

