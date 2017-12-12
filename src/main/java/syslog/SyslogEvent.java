package syslog;

import mongodb.MongoService;
import org.productivity.java.syslog4j.server.SyslogServerEventIF;
import orm.Record;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

/**
 * Created by snow on 18/11/2017.
 * <p>
 * SyslogEvent 基类
 */
public class SyslogEvent implements SyslogServerEventIF {
    protected static final String CHARSET = "UTF-8";
    private static int savedSyslogLevel;        //保存上一条日志的类型
    protected byte[] raw;
    protected int priVal;
    protected int facility;
    protected int level;
    protected String timestamp;
    protected String host;
    protected String message;
    protected String serviceName;       //服务名
    protected String className;     //类名
    protected int startPos;
    protected int endPos;
    protected int tempPos;

    static Hashtable<String, Integer> serviceTable = new Hashtable<>();     //所有的服务名
    static int serviceTableIndex = 0;
    static ArrayList<Record> serviceRecords = new ArrayList<>();

    public SyslogEvent() {

    }

    public SyslogEvent(byte[] data, int offset, int length) throws Exception {
        raw = new byte[length - offset];
        System.arraycopy(data, offset, raw, 0, length);

        //raw
        System.out.println("---------------");
        try {
            System.out.println(new String(raw, CHARSET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("---------------");

        //level & facility
        startPos = searchChar(raw, startPos, '<') + 1;
        endPos = searchChar(raw, startPos, '>');
        priVal = Integer.parseInt(getString(raw, startPos, endPos));
        level = priVal & 7;
        facility = (priVal - level) >> 3;

        //<134>Dec  1 09:38:25 7f753a3880d5 keyverify[1]

        //timestamp
        startPos = endPos + 1;
        tempPos = searchChar(raw, startPos, ':');
        tempPos = searchChar(raw, tempPos + 1, ':');
        endPos = searchChar(raw, tempPos + 1, ' ');
        timestamp = getString(raw, startPos, endPos);

        //host
        startPos = endPos + 1;
        endPos = searchChar(raw, startPos, ' ');
        host = getString(raw, startPos, endPos);

        /*
              0       Emergency: system is unusable
              1       Alert: action must be taken immediately
              2       Critical: critical conditions
              3       Error: error conditions
              4       Warning: warning conditions
              5       Notice: normal but significant condition
              6       Informational: informational messages
              7       Debug: debug-level messages
         */

        if (level == 3) {
            savedSyslogLevel = level;
            new LoggingSyslog(this, LoggingSyslog.ERROR_LOG);
            return;
        }

        /* distinguish normal logs between RequestSyslog and LoggingSyslog */

        if (level == 6) {
            if (savedSyslogLevel == 3) {     //当访问日志前面一条日志为错误日志
                SyslogService.addLoggingError();
            }

            int lastPos = raw.length;
            int beforeLastPos = 0;
            for (int i = lastPos - 1; i > lastPos - 10; i--) {
                if (raw[i] == ' ') {
                    beforeLastPos = i;
                }
            }
            String numOrNot = getString(raw, beforeLastPos + 1, lastPos);

            savedSyslogLevel = level;

            if (isRequestLog(numOrNot)) {
                new RequestSyslog(this);
                return;
            } else {
                new LoggingSyslog(this, LoggingSyslog.NORMAL_LOG);
                return;
            }
        } else {
            // warning: level = 4
            savedSyslogLevel = level;
            new LoggingSyslog(this, LoggingSyslog.NORMAL_LOG);
            return;
        }
    }

    private boolean isRequestLog(String msg) {
        //String infoPattern="\\d{1,3}(:\\d){2,}";
        String infoPattern = "[0-9]+";
        return Pattern.matches(infoPattern, msg);

    }

    protected String getString(byte[] data, int startPos, int endPos) throws Exception {
        try {
            return new String(data, startPos, endPos - startPos, CHARSET);
        } catch (UnsupportedEncodingException e) {
            System.err.println("Unsupported encoding");
        }
        return "";
    }

    /**
     * Try to find a character in given byte array, starting from startPos.
     *
     * @param data
     * @param startPos
     * @param c
     * @return position of the character or -1 if not found
     */
    protected int searchChar(byte[] data, int startPos, char c) throws Exception {
        for (int i = startPos; i < data.length; i++) {
            if (data[i] == c) {
                return i;
            }
        }
        return -1;
    }

    //倒序查找字符
    protected int searchChar(byte[] data, int endPos, char c, boolean order) throws Exception {
        for (int i = endPos; i > 0; i--) {
            if (data[i] == c) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public byte[] getRaw() {
        return raw;
    }

    @Override
    public int getFacility() {
        return facility;
    }

    @Override
    public void setFacility(int i) {
        facility = i;
    }

    @Override
    public Date getDate() {
        return null;
    }

    @Override
    public void setDate(Date date) {

    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int i) {
        level = i;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public void setHost(String s) {
        host = s;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String s) {
        message = s;
    }

    @Override
    public String getCharSet() {
        return null;
    }

    @Override
    public void setCharSet(String s) {

    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getClassName() {
        return className;
    }

    //为某个服务新增一条分析记录
    protected Record generateNewRecord(String serviceName) {
        if (serviceTable.containsKey(serviceName)) {
            return serviceRecords.get(serviceTable.get(serviceName));
        } else {
            serviceTable.put(serviceName, serviceTableIndex++);
            Record record = new Record(serviceName);
            serviceRecords.add(record);
            return record;
        }
    }

    //采用另一个线程测试
    static class RecordTest implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    sleep(20000);       //20秒

                    for (int i = 0; i < serviceRecords.size(); i++) {
                        Record record = serviceRecords.get(i);
                        record.setTimestamp(new Date().toString());
                        MongoService.getRecordCollection().add(record);
                    }

                    initRecords();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        //初始化Records
        public void initRecords(){
            serviceTable = new Hashtable<>();
            serviceTableIndex = 0;
            serviceRecords = new ArrayList<>();
        }
    }
}
