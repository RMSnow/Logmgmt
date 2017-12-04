/**
 * Created by snow on 04/12/2017.
 */
public class StringTest {
    public static void main(String[] args) {
        String url = "/favicon.ico";
        String heartCheck = "/application/api/v2/heart";
        if (url.length() >= heartCheck.length()) {
            if (url.substring(0, heartCheck.length()).equals(heartCheck)) {
                System.out.println("yes");
            }else {
                System.out.println("no");
            }
        }

        url = "/application/api/v2/heart?code=8c9742";
        if (url.length() >= heartCheck.length()) {
            System.out.println(url.substring(0, heartCheck.length()));
            if (url.substring(0, heartCheck.length()).equals(heartCheck)) {
                System.out.println("yes");
            }else {
                System.out.println("no");
            }
        }

        System.out.println();

        String a = "hello";
        System.out.println(a.length());
        System.out.println(a.substring(0,1));
        System.out.println(a.substring(0,3));
    }
}
