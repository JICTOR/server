package com.fasoo.logic.utils.time;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimePrint {
    public static void printCurrentTime(String step) {
        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("hh:mm:ss");
        String timeString = dayTime.format(new Date(time));
        System.out.println(step + " : " + timeString);
    }
}
