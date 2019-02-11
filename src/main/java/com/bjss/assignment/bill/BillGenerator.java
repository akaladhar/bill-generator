package com.bjss.assignment.bill;

import com.bjss.assignment.discount.Discount;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Generates bill output from inout data
 */
public class BillGenerator {
    public static String printBill(List<Discount> discounts,
                                        BigDecimal subTotal, BigDecimal discountAmount, BigDecimal total) {
        Locale enGBLocale =
                        new Locale.Builder().setLanguage("en").setRegion("GB").build();
        NumberFormat numberFormatter = NumberFormat.getCurrencyInstance(enGBLocale);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Subtotal: " + numberFormatter.format(subTotal));
        stringBuilder.append("\n");
        if (discounts.isEmpty()) {
            stringBuilder.append("(no offers available)\n");
        } else {
            discounts.stream().forEach(e -> stringBuilder.append(e.getItem() + " " + e.getPercentage().longValue() + "% off: -"
                    + numberFormatter.format(e.getDiscountAmount().divide(Biller.ONE_HUNDRED)) + "\n"));
        }
        stringBuilder.append("Total: "+numberFormatter.format(total));

        return stringBuilder.toString();
    }
}
