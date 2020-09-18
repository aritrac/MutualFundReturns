package com.core.mf.utils.test;

import com.core.mf.utils.JsonHelper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class JsonHelperTest {
    private static JsonHelper jsHelper = null;
    @BeforeClass
    public static void setup(){
        jsHelper = new JsonHelper("https://api.mfapi.in/mf/");
    }

    @Test(expected = NullPointerException.class)
    public void testInvalidURLToReadFrom() throws IOException{
        jsHelper.setUrl("https://apx.mfapi.in/mf/");
        jsHelper.readJsonFromUrl();
    }

    @AfterClass
    public static void tearDown(){
        jsHelper = null;
    }
}
