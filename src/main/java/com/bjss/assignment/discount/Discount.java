package com.bjss.assignment.discount;

import java.math.BigDecimal;

public class Discount {

    private String item;
    private String onItem;
    private int numberOfOnItems;
    private BigDecimal percentage;
    private DiscountType discountType;

    private BigDecimal discountAmount;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getOnItem() {
        return onItem;
    }

    public void setOnItem(String onItem) {
        this.onItem = onItem;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public int getNumberOfOnItems() {
        return numberOfOnItems;
    }

    public void setNumberOfOnItems(int numberOfOnItems) {
        this.numberOfOnItems = numberOfOnItems;
    }

    public String toString() {
        return item + "-Discount type: " + discountType + "-Percentage: " + percentage + "-OnItem: " + onItem + "-num: " + numberOfOnItems + "-discount amount: "+discountAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

}
