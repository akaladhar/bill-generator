package com.bjss.assignment.discount;

import com.bjss.assignment.dao.DiscountsDao;
import com.bjss.assignment.dao.PricesDao;
import com.bjss.assignment.util.Logger;

import java.math.BigDecimal;
import java.util.List;

import static com.bjss.assignment.bill.Biller.ONE_HUNDRED;
import static java.util.stream.Collectors.toList;

public class DiscountCalculator {

    private DiscountsDao discountsDao = new DiscountsDao();

    public List<Discount> getDiscounts( List<String> basket) {
        Logger.log("Distinct items in the bill are "+basket.stream().distinct().collect(toList()));
        Logger.log("Calculating the discounts....");
        return basket.stream().distinct().map(n -> getDiscount(n, basket)).filter(e-> e != null).collect(toList());
    }

    private Discount getDiscount(String item, List<String> allItems) {
        final Discount discount = discountsDao.getDiscount(item);
        if (discount != null && discount.getDiscountType() == DiscountType.MULTIBUY ) {
            if (!isEligibleMultiBuyDone(allItems, discount)){
                return null;
            } else {
                //get number of items bought
                long noOfItems =  allItems.stream().filter(s -> s.equals(item)).count();
                //number of multi buys items bought for discount
                long noOfMultiBuyItems = allItems.stream().filter(e -> e.equals(discount.getOnItem())).count();
                //Check how many multi-buy discounts to be applied
                long noOfBuysEligibleForMultiBuyDiscounts = noOfMultiBuyItems/discount.getNumberOfOnItems();

                long noOfDiscountedItems = noOfItems > noOfBuysEligibleForMultiBuyDiscounts ? noOfBuysEligibleForMultiBuyDiscounts : noOfItems;

                discount.setDiscountAmount(
                        PricesDao.getPrice(item).
                                multiply(discount.getPercentage()).
                                multiply(BigDecimal.valueOf(noOfDiscountedItems)).
                                divide(ONE_HUNDRED));
                return discount;
            }
        }
        if (discount != null) {
            discount.setDiscountAmount(
                            PricesDao.getPrice(item).multiply(discount.getPercentage()).divide(ONE_HUNDRED));
        }
        return discount;
    }

    private boolean isEligibleMultiBuyDone(List<String> allItems, Discount discount){
        final String onItem = discount.getOnItem();
        if (allItems.stream().filter(e -> e.equals(onItem)).count() >= discount.getNumberOfOnItems()){
            return true;
        }
        return false;
    }

    /**
     *Gets discount amount of a single item
     *
     * @param item
     * @param percentage
     * @return
     */

    private BigDecimal getDiscount(String item, BigDecimal percentage) {
        return (percentage.multiply(PricesDao.getPrice(item))).divide(ONE_HUNDRED).divide(ONE_HUNDRED);
    }

    /**
     *
     * Calculates the total discount amount of the bill
     *
     * @param discounts
     * @return
     */
    public BigDecimal getDiscountsTotal(List<Discount> discounts) {
        return discounts.stream().map(e->e.getDiscountAmount().divide(ONE_HUNDRED)).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}