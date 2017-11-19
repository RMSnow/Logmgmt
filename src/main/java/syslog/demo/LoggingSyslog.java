package syslog.demo;

/**
 * Created by snow on 18/11/2017.
 * <p>
 * 处理运行API的日志
 */
public class LoggingSyslog extends SyslogEvent {

    public LoggingSyslog(SyslogEvent event) {
        //time: Nov 15 17:14:48
        event.startPos = event.endPos + 1;
        event.tempPos = searchChar(event.raw, event.startPos, ' ');
        event.tempPos = searchChar(event.raw, event.tempPos + 1, ' ');
        event.endPos = searchChar(event.raw, event.tempPos + 1, ' ');
        event.timestamp = getString(event.raw, event.startPos, event.endPos);
        //System.out.printf(event.timestamp + "\n");

        //event.host: snow.local
        event.startPos = event.endPos + 1;
        event.endPos = searchChar(event.raw, event.startPos, ' ');
        event.host = getString(event.raw, event.startPos, event.endPos);
        //System.out.printf(event.host + "\n");

        //logging(event);

        errorLogging(event);

    }

    /**
     * 正常日志（INFO/WARNING等）
     */
    public void logging(SyslogEvent event) {

//        //time: Nov 15 17:14:48
//        event.startPos = event.endPos + 1;
//        event.tempPos = searchChar(event.raw, event.startPos, ' ');
//        event.tempPos = searchChar(event.raw, event.tempPos + 1, ' ');
//        event.endPos = searchChar(event.raw, event.tempPos + 1, ' ');
//        event.timestamp = getString(event.raw, event.startPos, event.endPos);
//        //System.out.printf(event.timestamp + "\n");
//
//        //event.host: snow.local
//        event.startPos = event.endPos + 1;
//        event.endPos = searchChar(event.raw, event.startPos, ' ');
//        event.host = getString(event.raw, event.startPos, event.endPos);
//        //System.out.printf(event.host + "\n");

        //name of service: courseservice
        event.startPos = event.endPos + 1;
        event.endPos = searchChar(event.raw, event.startPos, '[');
        event.serviceName = getString(event.raw, event.startPos, event.endPos);
        //System.out.printf(event.serviceName+"\n");

        //class: org.eclipse.jetty.server.ServerConnector
        event.startPos = event.endPos + 1;
        event.tempPos = searchChar(event.raw, event.startPos, ']');
        event.startPos = searchChar(event.raw, event.tempPos + 1, ']') + 2;
        event.endPos = searchChar(event.raw, event.startPos, ' ');
        event.className = getString(event.raw, event.startPos, event.endPos);
        //System.out.printf(event.className + "\n");

        //event.message
        event.startPos = event.endPos + 1;
        event.endPos = event.raw.length;
        event.message = getString(event.raw, event.startPos, event.endPos);
        //System.out.printf(event.message + "\n");
    }

    /**
     * 异常日志（ERROR）
     */
    public void errorLogging(SyslogEvent event){
        //type1：header，即包含报错的信息头
        //type2：body，即堆栈信息（只有第一条是错误类型，其他均为行号的报错）
    }
}
