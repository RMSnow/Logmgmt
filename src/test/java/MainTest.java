import entity.ConfInfo;
import exception.IpWhiteListGetter;
import exception.MailReport;
import mongodb.MongoConnector;

import java.util.ArrayList;

/**
 * Created by snow on 22/01/2018.
 */
public class MainTest {

    public static void main(String[] args){
//        ConfInfo.mongodbHost = "119.29.228.21";
//        ConfInfo.mongodbPort = "8610";
//        ConfInfo.mongodbUserName = "logmgt";
//        ConfInfo.mongodbPassword = "testDb";
//
//        ConfInfo.ip = "119.29.228.21";
//        ConfInfo.serviceName = "logmgmt";
//        ConfInfo.logmgmtPort = "9999";
//        ConfInfo.pswd = "d50e43d9fe9a418692eb5db78217af7b";
//
//        ConfInfo.registryIp = "123.207.73.150";
//        ConfInfo.registryPort = "8000";
//        ConfInfo.discoverPort = "8001";
//
//        new Thread(new IpWhiteListGetter()).start();

        ConfInfo.recipients = new ArrayList<>();
        ConfInfo.recipients.add("xueyao_98@foxmail.com");

        String richText = "测试中文";
        MailReport.reportMessage(MailReport.newRichMessage(MailReport.INSECURE_IP, richText));

    }
}
