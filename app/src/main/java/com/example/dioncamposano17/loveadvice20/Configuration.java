package com.example.dioncamposano17.loveadvice20;

/**
 * Created by dioncamposano17 on 04/02/2017.
 */

public class Configuration {

    public static String IP_ADDRESS = "192.168.43.245";


    public static String configURL(String url) {
        return "http://" + IP_ADDRESS + "/api/" + url;
    }
}
