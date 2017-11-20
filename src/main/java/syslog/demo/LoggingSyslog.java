package syslog.demo;

/**
 * Created by snow on 18/11/2017.
 * <p>
 * 处理运行API的日志
 */
public class LoggingSyslog extends SyslogEvent {
    public static final boolean NORMAL_LOG = true;
    public static final boolean ERROR_LOG = false;

    public LoggingSyslog(SyslogEvent event, boolean tag) {
        event.startPos = event.endPos + 1;
        event.tempPos = searchChar(event.raw, event.startPos, ' ');
        event.tempPos = searchChar(event.raw, event.tempPos + 1, ' ');
        event.endPos = searchChar(event.raw, event.tempPos + 1, ' ');
        event.timestamp = getString(event.raw, event.startPos, event.endPos);

        event.startPos = event.endPos + 1;
        event.endPos = searchChar(event.raw, event.startPos, ' ');
        event.host = getString(event.raw, event.startPos, event.endPos);

        if (tag == NORMAL_LOG) {
            logging(event);
        } else {
            errorLogging(event);
        }

    }

    /**
     * 正常日志（INFO/WARNING等）
     */
    public void logging(SyslogEvent event) {
        event.startPos = event.endPos + 1;
        event.endPos = searchChar(event.raw, event.startPos, '[');
        event.serviceName = getString(event.raw, event.startPos, event.endPos);

        event.startPos = event.endPos + 1;
        event.tempPos = searchChar(event.raw, event.startPos, ']');
        event.startPos = searchChar(event.raw, event.tempPos + 1, ']') + 2;
        event.endPos = searchChar(event.raw, event.startPos, ' ');
        event.className = getString(event.raw, event.startPos, event.endPos);

        event.startPos = event.endPos + 1;
        event.endPos = event.raw.length;
        event.message = getString(event.raw, event.startPos, event.endPos);
    }

    /**
     * 异常日志（ERROR）
     * type1：header，即包含报错的信息头
     * type2：body，即堆栈信息（只有第一条是错误类型说明，其他均为行号的报错）
     */
    public void errorLogging(SyslogEvent event) {
        //当event为type2时，host后面是一个空格 + 一个tab键
        event.startPos = event.endPos + 1;
        String flagChar = getString(event.raw, event.startPos, event.startPos + 1);
        if (flagChar.equals("\t")) {      //type2
            event.startPos++;
            event.endPos = event.raw.length;
            event.message = getString(event.raw, event.startPos, event.endPos);
        } else {     //type1
            event.endPos = searchChar(event.raw, event.startPos, '[');
            event.serviceName = getString(event.raw, event.startPos, event.endPos);

            event.tempPos = event.endPos + 1;
            event.endPos = searchChar(event.raw, event.tempPos, '[');
            event.startPos = event.endPos + 1;
            event.endPos = searchChar(event.raw, event.startPos, ']');
            event.message = getString(event.raw, event.startPos, event.endPos);

            event.startPos = event.endPos + 2;
            event.endPos = searchChar(event.raw, event.startPos, ' ');
            event.className = getString(event.raw, event.startPos, event.endPos);

            event.startPos = event.endPos + 1;
            event.endPos = event.raw.length;
            event.message = "[" + event.message + "] " + getString(event.raw, event.startPos, event.endPos);
        }
    }
}
