package syslog.demo;

/**
 * Created by snow on 21/11/2017.
 */
public class Test {
    public static void main(String[] args){
        String msg = "123";
        for (int i = 0; i < msg.length(); i++) {
            if (!Character.isDigit(Integer.parseInt(msg.substring(i,i+1)))) {
                System.out.println("false");
                return;
            }
        }
        System.out.println(msg.length()+" : true");
    }
}
