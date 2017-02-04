package com.example.dioncamposano17.loveadvice20;

/**
 * Created by dioncamposano17 on 04/02/2017.
 */

public class Configuration {

    public static String IP_ADDRESS = "10.225.3.239/LAfinal";

    public static String configURL(String url) {
        return "http://" + IP_ADDRESS + "/api/" + url;
    }
}
