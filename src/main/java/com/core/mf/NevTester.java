package com.core.mf;

import com.core.mf.utils.NevRangeHelper;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NevTester {
    private final static Logger LOGGER = Logger.getLogger(NevTester.class.getName());

    public static void main(String[] args) {
        NevRangeHelper nrHelper = new NevRangeHelper();
        try {
            Scanner in = new Scanner(System.in);
            System.out.println("Scheme number: ");
            nrHelper.setSchemeNumber(in.nextInt());
            System.out.println("Period of investment: ");
            nrHelper.setPeriodOfInvestment(in.nextInt());
            System.out.println("Horizon: ");
            nrHelper.setHorizon(in.nextInt());
            nrHelper.initList();
            nrHelper.generateReport();
        }catch(InputMismatchException ex){
            LOGGER.log(Level.SEVERE,"Invalid input provided, please try again. Exiting now");
        }
    }
}


