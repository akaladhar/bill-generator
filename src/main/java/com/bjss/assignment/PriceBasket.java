package com.bjss.assignment;

import com.bjss.assignment.bill.Biller;
import com.bjss.assignment.util.PricesDiscountsLoader;

import java.io.IOException;

public class PriceBasket {
    public static void main(String[] args) throws IOException {
        PricesDiscountsLoader.loadData("/prices.yml");

        Biller biller = new Biller();
        String bill = biller.processBill(args);
        System.out.println("\nProducing formatted Bill\n");
        System.out.println("\n****************************************");
        System.out.println(bill);
        System.out.println("****************************************");
    }
}
