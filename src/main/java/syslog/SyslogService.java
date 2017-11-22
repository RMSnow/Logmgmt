package syslog;

import mongodb.MongoService;
import orm.AccessLog;

/**
 * 实现对数据库的存储
 */
public class SyslogService {
    static LoggingSyslog errHeader = new LoggingSyslog();
    static int errBodySum = 0;

    /**
     * Logging - Normal (Info/Warning)
     *
     * @param syslog
     */
    public static void addLoggingNormal(LoggingSyslog syslog) {

    }

    /**
     * Logging - Error
     */
    public static void addLoggingError() {
        //add errHeader
        System.out.println(errHeader.toString());
    }

    public static void handleLoggingError(LoggingSyslog syslog) {
        /* header & body */
        //        facility
        //        level
        //        timestamp
        //        host
        //        message

        /* only header */
        //        serviceName
        //        className

        if (syslog.serviceName != null) {     //header
            if (errHeader.timestamp != syslog.timestamp && errHeader.timestamp != null) {       //a new err header
                //addLoggingError();
                errBodySum = 0;
            }
            errHeader = syslog;
        } else {     //body
            if (errBodySum == 0) {
                errHeader.message = errHeader.message + "\n[INFO] "
                        + syslog.message;
                errHeader.errDetails = "";
            } else {
                errHeader.errDetails += syslog.message;
            }
            errBodySum++;
        }
    }

    /**
     * RequestLog
     *
     * @param syslog
     */
    public static void addRequestLog(RequestSyslog syslog) {
        AccessLog log = new AccessLog();
        log.setClient(syslog.getClient());
        log.setDatetime(syslog.getDatetime());
        log.setStatus(syslog.getStatus());
        log.setHost(syslog.getHost());
        log.setMethod(syslog.getMethod());
        log.setName(syslog.getServiceName());
        log.setUrl(syslog.getUrl());
        MongoService.getAccessLogCollection().add(log);
    }
}

/*

 数据库设计

【LoggingSyslog】

    facility
    level
    timestamp
    host
    serviceName
    className
    message

    [errDetails]

【RequestSyslog】

    private String clientIP;
    private String datetime;
    private String method;
    private String url;
    private int status;
    private String client;

    facility
    //level
    timestamp (服务器的时间)
    host
    serviceName
    className

*/