package com.core.mf.utils.test;

import com.core.mf.utils.NevRangeHelper;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class NevRangeHelperTest {
    private static NevRangeHelper nhMock = null;
    @BeforeClass
    public static void setup(){
        nhMock = new NevRangeHelper();
    }

    @Test
    public void testInvalidSchemeNumber(){
        nhMock.setSchemeNumber(12);
        nhMock.setHorizon(1);
        nhMock.setPeriodOfInvestment(1);
        assert (nhMock.generateReport() != 0);
    }

    @Test
    public void testInvalidHorizon(){
        nhMock.setHorizon(100);
        nhMock.setSchemeNumber(102885);
        Assert.assertFalse(nhMock.checkHorizon());
    }

    @AfterClass
    public static void tearDown(){
        nhMock = null;
    }
}
