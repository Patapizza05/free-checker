package fr.clementduployez.freechecker;

import java.util.LinkedList;

/**
 * Created by cdupl on 11/14/2015.
 */
public class MncInfo {
    private Integer mnc;
    private Integer mcc;
    private String brand;
    private String operator;

    public MncInfo(int mnc, int mcc, String brand, String operator) {
        this.mnc = mnc;
        this.mcc = mcc;
        this.brand = brand;
        this.operator = operator;
    }

    public Integer getMnc() {
        return mnc;
    }

    public void setMnc(Integer mnc) {
        this.mnc = mnc;
    }

    public Integer getMcc() {
        return mcc;
    }

    public void setMcc(Integer mcc) {
        this.mcc = mcc;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
