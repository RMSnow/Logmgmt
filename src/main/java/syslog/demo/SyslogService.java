package syslog.demo;

/**
 * 对外的接口：在这里实现对数据库的存储
 */
public class SyslogService {

    // Logging中的正常日志（INFO/WARNING等）
    public static void addLoggingNormal(LoggingSyslog syslog){

    }

    // Logging中的异常日志（ERROR）
    public static void addLoggingError(LoggingSyslog syslog){

    }

    // RequestLog
    public static void addRequestLog(RequestSyslog syslog){

    }
}
