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

        final int a = 1;
        final int b = 2;

        int i = 3;
        switch (i){
            case a:
                System.out.println("a");
                break;
            case b:
                System.out.println("b");
                break;
            default:
                System.out.println("others");
                break;
        }

    }

}