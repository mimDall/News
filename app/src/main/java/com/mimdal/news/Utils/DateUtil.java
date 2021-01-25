package com.mimdal.news.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String FormatDate(String unformattedDate) {

        String result = "";
        Date date = null;

        SimpleDateFormat inputDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat finalDate = new SimpleDateFormat("dd MMM yyyy");

        try {
            date = inputDate.parse(unformattedDate);
            if (date != null) {

                result = finalDate.format(date);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;

    }
}
