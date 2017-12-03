package syslog;

import mongodb.MongoService;
import orm.LoggingLog;
import orm.RequestLog;

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
     * @param errDetails
     */
    public static void addLoggingNormal(LoggingSyslog syslog, String errDetails) {
        LoggingLog log = new LoggingLog();
        log.setFacility(String.valueOf(syslog.getFacility()));
        log.setLevel(syslog.getLevel());
        log.setTimestamp(syslog.getTimestamp());
        log.setHost(syslog.getHost());
        log.setServiceName(syslog.getServiceName());
        log.setClassName(syslog.getClassName());
        log.setMessage(syslog.getMessage());
        log.setErrDetails(errDetails);

        MongoService.getLoggingLogCollection().add(log);
    }

    /**
     * Logging - Error
     */
    public static void addLoggingError() {
        System.out.println(errHeader.toString());
        addLoggingNormal(errHeader, errHeader.getErrDetails());
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
            errLogInit(syslog);
        } else {     //body
            if (errBodySum == 0) {
                errHeader.message = errHeader.message + " [BASIC ERROR-DETAILS] "
                        + syslog.message;
                errHeader.errDetails = "";
            } else {
                errHeader.errDetails = errHeader.errDetails + " \n " + syslog.message;
            }
            errBodySum++;
        }
    }

    static void errLogInit(LoggingSyslog syslog) {
        errHeader = syslog;
        errBodySum = 0;
    }

    /**
     * RequestLog
     *
     * @param syslog
     */
    public static void addRequestLog(RequestSyslog syslog) {
        RequestLog log = new RequestLog();
        log.setFacility(syslog.getFacility());
        log.setClient(syslog.getClient());
        log.setClientIP(syslog.getClientIP());
        log.setDatetime(syslog.getDatetime());
        log.setStatus(syslog.getStatus());
        log.setHost(syslog.getHost());
        log.setMethod(syslog.getMethod());
        log.setServiceName(syslog.getServiceName());
        log.setClassName(syslog.getClassName());
        log.setUrl(syslog.getUrl());

        MongoService.getRequestLogCollection().add(log);
    }
}