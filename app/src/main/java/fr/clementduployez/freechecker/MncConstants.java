package fr.clementduployez.freechecker;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by cdupl on 11/14/2015.
 */
public class MncConstants {

    public static final HashMap<Integer, MncInfo> mncCodes = initHashMap();

    public static final int MCC = 208;

    public static final String ORANGE = "Orange";
    public static final String GLOBALSTAR = "Globalstar";
    public static final String SFR = "SFR";
    public static final String FREE = "Free";
    public static final String FREE_MOBILE = FREE + " Mobile";
    public static final String BOUYGUES = "Bouygues Telecom";


    public static HashMap<Integer, MncInfo> initHashMap() {
        HashMap<Integer, MncInfo> h = new HashMap<>();

        add(h,MCC,ORANGE,ORANGE,1,2,91);
        add(h,MCC,SFR,SFR,9,10,11,13);
        add(h,MCC,FREE,FREE_MOBILE,15,16);
        add(h,MCC,BOUYGUES, BOUYGUES,20,21);

        return h;
    }

    public static final void add(HashMap<Integer, MncInfo> h, int mcc, String brand, String operator, int... mnc) {
        for (int i : mnc) {
            h.put(i, new MncInfo(i, mcc, brand, operator));
        }

    }

}
