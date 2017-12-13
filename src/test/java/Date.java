import com.fasterxml.jackson.core.JsonProcessingException;
import entity.RequestsRate;
import mongodb.DateUtil;

import java.util.LinkedHashMap;

/**
 * Created by snow on 07/12/2017.
 */
public class Date {
    public static void main(String[] args) throws JsonProcessingException {
        System.out.println(DateUtil.getDateNow());
        System.out.println(DateUtil.getYesterday());
        System.out.println(DateUtil.getRecentDays());
    }
}