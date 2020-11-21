package com.github.java.fileoper.entity;

/**
 * @author pengfei.zhao
 * @date 2020/11/21 15:36
 */
public class DemoData {
    private String lfSid;

    private String feeName;

    private String amount;

    private String direction;

    private String country;

    public String getLfSid() {
        return lfSid;
    }

    public String getFeeName() {
        return feeName;
    }

    public String getAmount() {
        return amount;
    }

    public String getDirection() {
        return direction;
    }

    public String getCountry() {
        return country;
    }

    public void setLfSid(String lfSid) {
        this.lfSid = lfSid;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
