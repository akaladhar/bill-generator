package com.bjss.assignment.bill;

import com.bjss.assignment.util.PricesDiscountsLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BillerTest {


    @Before
    public void setUp() throws Exception{

        PricesDiscountsLoader.loadData("/prices.yml");
    }

    @Test
    public void testProcessBill(){
        String[] items = {"Milk", "Apples", "Bread"};

        final Biller biller = new Biller();
        String formattedBill = biller.processBill(items);
        Assert.assertEquals("Output did not match",formattedBill,"Subtotal: £3.10\n"
                                                                                + "Apples 10% off: -£0.10\n"
                                                                                + "Total: £3.00");
    }

    @Test
    public void testProcessBillNoDiscount(){
        String[] items = {"Milk"};

        final Biller biller = new Biller();
        String formattedBill = biller.processBill(items);
        Assert.assertEquals("Output did not match",formattedBill,"Subtotal: £1.30\n"
                                                                                + "(no offers available)\n"
                                                                                + "Total: £1.30");
    }

    @Test
    public void testProcessBillNoDiscountSameMultipleItems(){
        String[] items = {"Milk", "Milk"};

        final Biller biller = new Biller();
        String formattedBill = biller.processBill(items);
        Assert.assertEquals("Output did not match",formattedBill,"Subtotal: £2.60\n"
                                                                                + "(no offers available)\n"
                                                                                + "Total: £2.60");
    }

    @Test
    public void testProcessBillMultipleMultiBuys(){
        String[] items = {"Milk", "Apples", "Bread", "Soup", "Soup", "Soup", "Soup", "Bread"};

        final Biller biller = new Biller();
        String formattedBill = biller.processBill(items);
        Assert.assertEquals("Output did not match",formattedBill,"Subtotal: £6.50\n"
                                                                                + "Apples 10% off: -£0.10\n"
                                                                                + "Bread 50% off: -£0.80\n"
                                                                                + "Total: £5.60");
    }

    @Test
    public void testProcessBillMultipleMultiBuysWithAnExtraSoup(){
        String[] items = {"Milk", "Apples", "Bread", "Soup", "Soup", "Soup", "Bread"};

        final Biller biller = new Biller();
        String formattedBill = biller.processBill(items);
        Assert.assertEquals("Output did not match",formattedBill,"Subtotal: £5.85\n"
                                                                                + "Apples 10% off: -£0.10\n"
                                                                                + "Bread 50% off: -£0.40\n"
                                                                                + "Total: £5.35");
    }
}
