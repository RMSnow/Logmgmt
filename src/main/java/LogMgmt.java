import app.LogMgmtApp;
import entity.ConfInfo;
import mongodb.MongoConnector;
import syslog.Server;

import java.util.Arrays;

/**
 * 日志管理的主入口
 */
public class LogMgmt {
    public static void main(final String[] args) {
        //App入口
        Arrays.asList(new LogMgmtApp()).forEach(
                (l) -> {
                    try {
                        l.run(args);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        System.out.println();
        System.out.println("------------------------------------------");
        System.out.println();

        MongoConnector.HOST=ConfInfo.mongodbHost;
        MongoConnector.PORT=Integer.valueOf(ConfInfo.mongodbPort);

        //Syslog入口
        Server syslogServer = new Server();

        System.out.println();
        System.out.println("------------------------------------------");
        System.out.println();
    }

}

//java -jar target/cloudclass_logmgmt-1.0-SNAPSHOT.jar server logmgmt.yml