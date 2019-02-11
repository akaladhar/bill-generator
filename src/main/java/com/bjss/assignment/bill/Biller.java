package com.bjss.assignment.bill;

import com.bjss.assignment.util.Logger;
import com.bjss.assignment.dao.PricesDao;
import com.bjss.assignment.discount.Discount;
import com.bjss.assignment.discount.DiscountCalculator;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 *
 * This class is the starting step in the app.
 */

import static java.util.stream.Collectors.toList;

public class Biller {

    private DiscountCalculator discountCalculator = new DiscountCalculator();
    public static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    /**
     * processes bill items and gererates the bill output
     * @param args
     * @return
     */
    public String processBill(String[] args) {
        final List<String> items = Arrays.asList(args);
        Logger.log("Calculating discounts ..");
        final List<Discount> discounts = discountCalculator.getDiscounts(items);
        Logger.log("Calculated discount .."+discounts);
        Logger.log("Calculating subtotal....");
        final BigDecimal subTotal = getSubTotal(items);
        Logger.log("Calculated subtotal.."+subTotal.toString());
        Logger.log("Calculating total discounts...");
        final BigDecimal discount = discountCalculator.getDiscountsTotal(discounts);
        Logger.log("Calculated total discounts.."+discount.toString());
        Logger.log("Printing Bill...");
        final BigDecimal total = subTotal.subtract( discount);

        return BillGenerator.printBill(discounts, subTotal, discount, total);
    }

    /**
     * Calculates the subtotal element of output
     *
     * @param list of items
     * @return
     */
    private BigDecimal getSubTotal(List<String> items) {
        return items.stream().map(e -> PricesDao.getPrice(e)).reduce(BigDecimal.ZERO, BigDecimal::add).divide(ONE_HUNDRED);
    }

}
