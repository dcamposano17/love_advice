package com.example.dioncamposano17.loveadvice20.model;

/**
 * Created by dioncamposano17 on 21/02/2017.
 */

public class Assessment {

    private String id, assess_name;

    public Assessment(String id, String assess_name) {
        this.id = id;
        this.assess_name = assess_name;
    }

    public String getId() {
        return id;
    }

    public String getAssess_name() {
        return assess_name;
    }
}
