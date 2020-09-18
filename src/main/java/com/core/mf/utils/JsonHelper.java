package com.core.mf.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonHelper {
    private final static Logger LOGGER = Logger.getLogger(JsonHelper.class.getName());
    private final static String DATA_NODE_NAME = "data";
    private final static String CHARSET_ENCODING = "UTF-8";
    private String url;

    public JsonHelper(String URL){
        this.url = URL;
    }
    public JSONArray readJsonFromUrl() throws IOException {
        InputStream is = null;
        try {
            is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName(JsonHelper.CHARSET_ENCODING)));
            String jsonText = readAll(rd);
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(jsonText);
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray nevList = (JSONArray) jsonObject.get(JsonHelper.DATA_NODE_NAME);
            if(nevList.size() == 0){
                LOGGER.log(Level.SEVERE,"No data present to be analyzed due to invalid scheme number provided, exiting");
                System.exit(0);
            }
            return nevList;
        }catch(IOException | ParseException ex){
            LOGGER.log(Level.SEVERE,"URL is incorrect or response parsing failure occurred. Exiting");
            System.exit(0);
            return null;
        }
        finally {
            is.close();
        }
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
