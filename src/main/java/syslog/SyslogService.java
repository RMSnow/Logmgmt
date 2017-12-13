package syslog;

import entity.RequestsRate;
import mongodb.DateUtil;
import mongodb.MongoService;
import orm.LoggingLog;
import orm.Record;
import orm.RequestLog;

import java.util.ArrayList;
import java.util.Hashtable;

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

    /**
     * 获取SyslogEvent中的日志分析记录
     */
    public static ArrayList<Record> getServiceRecords() {
        return SyslogEvent.serviceRecords;
    }

    /**
     * 添加五分钟内的秒访问率
     */
    public static int addSecondRequestsRate() {
        try {
            ArrayList<Record> records = getServiceRecords();
            for (int i = 0; i < records.size(); i++) {
                Record record = records.get(i);

                RequestsRate[] rates = record.getSecondRequestsRate();
                int requests = 0;
                for (int j = 0; j < rates.length; j++) {
                    if (rates[j] != null) {
                        requests += rates[j].getRequests();
                        continue;
                    } else {
                        int newRequests = record.getHourRequests() - requests;
                        record.setSecondRequestsRate(j,
                                new RequestsRate(DateUtil.getDateNow(), newRequests));
                        break;
                    }
                }
            }
            return records.size();
        }catch (Exception e){
            System.err.println("Errors of calculating secondRequestsRate.");
        }
        return 0;
    }

    /**
     * 清空当前与日志记录有关的静态变量
     */
    public static void initRecords(){
        SyslogEvent.serviceTable = new Hashtable<>();
        SyslogEvent.serviceTableIndex = 0;
        SyslogEvent.serviceRecords = new ArrayList<>();
    }
}