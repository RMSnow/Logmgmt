package mongodb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by WYJ on 2017/11/9.
 *
 * parse date from format 'pattern' to ISODate
 * example:
 *      17/十一月/2017:06:28:39
 *  ->  17-11-2017 06:28:39
 *
 *
 */
public class DateUtil {
    public static final String PATTERN_REQLOG_DATETIME="dd/MMM/yyyy:HH:mm:ss";
    public static final String PATTERN_LOG_TIMESTAMP="MMM dd HH:mm:ss";

    private static final String PATTERN_ISODATE="dd-MM-yyyy HH:mm:ss";


    private static String parseDate(String dateStr,String pattern,Locale locale){
        SimpleDateFormat originFormat = new SimpleDateFormat(pattern, locale);
        SimpleDateFormat newFormat = new SimpleDateFormat(PATTERN_ISODATE);

        Date d=null;
        String newDateStr = null;
        try {
            d = originFormat.parse(dateStr);
            newDateStr = newFormat.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDateStr;
    }


    public static String parseReqLogDateTime(String dateStr){
        SimpleDateFormat originFormat = new SimpleDateFormat(PATTERN_REQLOG_DATETIME);
        SimpleDateFormat newFormat = new SimpleDateFormat(PATTERN_ISODATE);
        originFormat.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        newFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        Date d=null;
        String newDateStr = null;
        try {
            d = originFormat.parse(dateStr);
            newDateStr = newFormat.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDateStr;
    }

    /*
    *   Deprecated because timestamp in a log lacks year value,
    *   and adding a value by server causes chaos.
    *   Using getDateNow() method as a compromise to create String timestamp in form of ISODate.
     */
    @Deprecated
    public static String parseTimeStamp(String dateStr){
        return parseDate(dateStr,PATTERN_LOG_TIMESTAMP,Locale.ENGLISH);
    }

    public static String getDateNow(){
        SimpleDateFormat format=new SimpleDateFormat(PATTERN_ISODATE);
        return format.format(new Date(System.currentTimeMillis()));
    }
}