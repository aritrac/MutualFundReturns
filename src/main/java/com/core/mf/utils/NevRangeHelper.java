package com.core.mf.utils;

import com.core.mf.models.Nev;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NevRangeHelper {
    private final static Logger LOGGER = Logger.getLogger(NevRangeHelper.class.getName());
    private final static String MF_URL = "https://api.mfapi.in/mf/";
    private static Map<String, Nev> nvList = new HashMap<>();

    //User data
    private int schemeNumber;
    private int periodOfInvestment;
    private int horizon;
    private LocalDate minDate = LocalDate.now();
    private List<String> horizonKeys = new ArrayList<>();

    public int getSchemeNumber() {
        return schemeNumber;
    }

    public void setSchemeNumber(int schemeNumber) {
        this.schemeNumber = schemeNumber;
    }

    public int getPeriodOfInvestment() {
        return periodOfInvestment;
    }

    public void setPeriodOfInvestment(int periodOfInvestment) {
        this.periodOfInvestment = periodOfInvestment;
    }

    public int getHorizon() {
        return this.horizon;
    }

    public void setHorizon(int horizon) {
        this.horizon = horizon;
    }

    public void initList(){
        try {
            JsonHelper jsonHelper = new JsonHelper( NevRangeHelper.MF_URL + schemeNumber);
            JSONArray nevList = jsonHelper.readJsonFromUrl();
            Iterator<JSONObject> iterator = nevList.iterator();
            while (iterator.hasNext()) {
                JSONObject tempObj = iterator.next();
                Nev nevObj = new Nev((String)tempObj.get("date"),Float.parseFloat((String)tempObj.get("nav")));
                if(nevObj.getDate().isBefore(minDate))
                    minDate = nevObj.getDate();
                nvList.put((String)tempObj.get("date"),nevObj);
                //System.out.println(nevObj.getDate() + " " + nevObj.getNavValue());
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading URL or parsing the response from URL");
        }
    }

    public void generateReport(){
        if(checkIntVals() && checkHorizon()) {
            generateHorizonKeys();
            printReport();
        }else{
            LOGGER.log(Level.SEVERE,"Invalid horizon or period of investment for provided data set. Exiting");
            System.exit(0);
        }
    }

    public boolean checkHorizon(){
        int latestMaxYrProvided = LocalDate.now().getYear();
        int latestMinYrProvided = minDate.getYear();
        return horizon <= (latestMaxYrProvided-latestMinYrProvided);
    }

    public boolean checkIntVals(){
        return periodOfInvestment > 0 && horizon > 0;
    }

    public void generateHorizonKeys(){
        LocalDate current = LocalDate.now();
        current = current.minusDays(1);
        LocalDate start = current.minus(horizon, ChronoUnit.YEARS);
        start = start.plus(1, ChronoUnit.MONTHS);
        LocalDate iter = start;
        while(iter.isBefore(current) || iter.isEqual(current)){
            String dateKey = iter.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            if(nvList.get(dateKey)!= null) {
                //System.out.println(dateKey + " " + nvList.get(dateKey).getNavValue());
                horizonKeys.add(dateKey);
            }else{
                LocalDate nextValid = findNextValidNav(iter);
                if(nextValid == null) {
                    //System.out.println(dateKey + " 0.0");
                    horizonKeys.add(dateKey + "ZERO");
                }
                else {
                    dateKey = nextValid.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    //System.out.println(dateKey + " " + nvList.get(dateKey).getNavValue());
                    horizonKeys.add(dateKey);
                }
            }
            iter = iter.plusMonths(1);
        }
    }

    public LocalDate findNextValidNav(LocalDate dt){
        LocalDate lastDay = dt.plusDays(dt.lengthOfMonth() - dt.getDayOfMonth());
        while(dt.isBefore(lastDay) || dt.isEqual(lastDay)) {
            String dtKey = dt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            if (nvList.containsKey(dtKey)) {
                return dt;
            }
            dt = dt.plusDays(1);
        }
        return null;
    }

    public void printReport(){
        System.out.printf("%-8s %7s %-20s","Month","Returns","Calculation");
        for(String endNavKey: horizonKeys){
            if(!endNavKey.contains("ZERO")){
                Nev nvEnd = nvList.get(endNavKey);
                Nev nvStart = getValidNvStart(nvEnd);
                String monthString = nvEnd.getDate().format(DateTimeFormatter.ofPattern("MMM-yy"));;
                if(nvStart != null){
                    double res = Math.pow((nvEnd.getNavValue()/nvStart.getNavValue()),(1/periodOfInvestment)) - 1;
                    String calcString = "Start nav - " + nvStart.getDate().format(DateTimeFormatter.ofPattern("dd-MMM-yy")) + " End nav - " + nvEnd.getDate().format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
                    System.out.printf("\n%-8s %7s %-20s",monthString,(int)Math.ceil(res * 100) + "%",calcString);
                }else{
                    LocalDate stNav = nvEnd.getDate().minusYears(periodOfInvestment);
                    System.out.printf("\n%-8s %7s %-20s",monthString,"0%","Start nav - " + stNav.format(DateTimeFormatter.ofPattern("dd-MMM-yy")) + " End nav - " + nvEnd.getDate().format(DateTimeFormatter.ofPattern("dd-MMM-yy")));
                }
            }else{
                endNavKey = endNavKey.substring(0,endNavKey.length() - 4);
                LocalDate nvEnd = LocalDate.parse(endNavKey,DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
                String monthString = nvEnd.format(DateTimeFormatter.ofPattern("MMM-yy"));
                Nev nvStart = getValidNvStart(new Nev(endNavKey,0.0f));
                if(nvStart != null){
                    System.out.printf("\n%-8s %7s %-20s",monthString,"0%","Start nav - " + nvStart.getDate().format(DateTimeFormatter.ofPattern("dd-MMM-yy")) + " End nav - " + nvEnd.format(DateTimeFormatter.ofPattern("dd-MMM-yy")));
                }else {
                    LocalDate stNav = nvEnd.minusYears(periodOfInvestment);
                    System.out.printf("\n%-8s %7s %-20s", monthString, "0%", "Start nav - " + stNav.format(DateTimeFormatter.ofPattern("dd-MMM-yy")) +  " End nav - " + nvEnd.format(DateTimeFormatter.ofPattern("dd-MMM-yy")));
                }
            }
        }
    }

    public Nev getValidNvStart(Nev obj){
        LocalDate end = obj.getDate();
        LocalDate start = end.minusYears(periodOfInvestment);
        String dateKey = start.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        if(nvList.containsKey(dateKey))
            return nvList.get(dateKey);
        else{
            start = findNextValidNav(start);
            if(start != null) {
                dateKey = start.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                return nvList.get(dateKey);
            }
        }
        return null;
    }
}
