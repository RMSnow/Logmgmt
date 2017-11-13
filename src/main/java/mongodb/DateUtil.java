package mongodb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by WYJ on 2017/11/9.
 */
public class DateUtil {
    public static String parseDate(String dateStr) {
        SimpleDateFormat originFormat = new SimpleDateFormat("dd/MMM/yyyy:hh:mm:ss", Locale.ENGLISH);
        SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        Date d = null;
        String newDateStr = null;
        try {
            d = originFormat.parse(dateStr);
            newDateStr = newFormat.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDateStr;
    }
}