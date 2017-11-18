package syslog.demo;

/**
 * Created by snow on 18/11/2017.
 */
public class Test {
    public Test(int i){
        if(i == 1){
            System.out.println(i);
            return;
        }
        i++;
        System.out.println(i);
    }
    public static void main(String[] args){
        new Test(1);
        new Test(2);
    }
}
