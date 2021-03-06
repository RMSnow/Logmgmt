package syslog;

import orm.record.Record;

/**
 * 处理运行API的日志
 */
public class LoggingSyslog extends SyslogEvent {
    public static final boolean NORMAL_LOG = true;
    public static final boolean ERROR_LOG = false;

    protected String errDetails;

    public LoggingSyslog() {

    }

    public LoggingSyslog(SyslogEvent event, boolean tag) throws Exception {
        init(event);

        if (tag == NORMAL_LOG) {
            logging();
            System.out.println(toString());
            SyslogService.addLoggingNormal(this, null);
        } else {
            errorLogging();
            //System.out.println(toString());
            SyslogService.handleLoggingError(this);
        }
    }

    /**
     * 域的初始化
     *
     * @param event
     */
    private void init(SyslogEvent event) {
        this.raw = event.raw;
        this.priVal = event.priVal;
        this.facility = event.facility;
        this.level = event.level;
        this.timestamp = event.timestamp;
        this.host = event.host;
        this.message = event.message;
        this.serviceName = event.serviceName;
        this.className = event.className;

        this.startPos = event.startPos;
        this.endPos = event.endPos;
        this.tempPos = event.tempPos;
    }

    /**
     * 正常日志（INFO/WARNING等）
     */
    public void logging() throws Exception {
        startPos = endPos + 1;
        endPos = searchChar(raw, startPos, '[');
        serviceName = getString(raw, startPos, endPos);

        generateNewRecord(serviceName);     //增加服务分析记录

        startPos = endPos + 1;
        tempPos = searchChar(raw, startPos, ']');
        startPos = searchChar(raw, tempPos + 1, ']') + 2;
        endPos = searchChar(raw, startPos, ' ');
        className = getString(raw, startPos, endPos);

        startPos = endPos + 1;
        endPos = raw.length;
        message = getString(raw, startPos, endPos);
    }

    /**
     * 异常日志（ERROR）
     * type1：header，即包含报错的信息头
     * type2：body，即堆栈信息（只有第一条是错误类型说明，其他均为行号的报错）
     */
    public void errorLogging() throws Exception {
        //当event为type2时，host后面是一个空格 + 一个tab键
        startPos = endPos + 1;
        String flagChar = getString(raw, startPos, startPos + 1);

        if (flagChar.equals("\t")) {      //type2
            startPos++;
            endPos = raw.length;
            message = getString(raw, startPos, endPos);
        } else {     //type1
            endPos = searchChar(raw, startPos, '[');
            serviceName = getString(raw, startPos, endPos);

            //新增分析记录
            Record record = generateNewRecord(serviceName);
            int errors = record.getLoggingErrors();
            record.setLoggingErrors(errors + 1);

            tempPos = endPos + 1;
            endPos = searchChar(raw, tempPos, '[');
            startPos = endPos + 1;
            endPos = searchChar(raw, startPos, ']');
            message = getString(raw, startPos, endPos);

            startPos = endPos + 2;
            endPos = searchChar(raw, startPos, ' ');
            className = getString(raw, startPos, endPos);

            startPos = endPos + 1;
            endPos = raw.length;
            message = "[" + message + "] " + getString(raw, startPos, endPos);
        }
    }

    @Override
    public String toString() {
        return "[facility]\t" + facility + "\n" +
                "[level]\t" + level + "\n" +
                "[timestamp]\t" + timestamp + "\n" +
                "[host]\t" + host + "\n" +
                "[serviceName]\t" + serviceName + "\n" +
                "[className]\t" + className + "\n" +
                "[message]\n" + message + "\n" +
                "[errDetails]\n" + errDetails + "\n";
    }

    public String toRichString(){
        return String.format("<h4>Info of the abnormal log is as follows.</h4>\n" +
                        "<table border=\"1\" cellpadding=\"10\">\n" +
                        "    <tr>\n" +
                        "      <td><b>facility</b></td>\n" +
                        "      <td>%s</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><b>level</b></td>\n" +
                        "        <td>%s</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><b>timestamp</b></td>\n" +
                        "        <td>%s</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><b>host</b></td>\n" +
                        "        <td>%s</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><b>serviceName</b></td>\n" +
                        "        <td>%s</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><b>className</b></td>\n" +
                        "        <td>%s</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><b>message</b></td>\n" +
                        "        <td>%s</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><b>errDetails</b></td>\n" +
                        "        <td>%s</td>\n" +
                        "    </tr>\n" +
                        "</table>", facility, level, timestamp,
                host, serviceName, className, message, errDetails);
    }

    public String getErrDetails() {
        return errDetails;
    }

}
