package com.kiloit.onlyadmin.util;

import java.security.*;

public class RandomUntil {
    public static String random6Digits(){
        SecureRandom random = new SecureRandom();
        int number =100000 + random.nextInt(900000);
        return String.valueOf(number);
    }
}
