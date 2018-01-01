import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Created by snow on 07/12/2017.
 */
public class Date {
    public static void main(String[] args) throws JsonProcessingException {
//        System.out.println(DateUtil.getDateNow());
//        System.out.println(DateUtil.getYesterday());
//        System.out.println(DateUtil.getRecentDays());
//        System.out.println(DateUtil.getTheDay(0));

        System.out.println(args.length);
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
    }

}