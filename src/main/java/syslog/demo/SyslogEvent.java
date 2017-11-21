package syslog.demo;

import org.productivity.java.syslog4j.server.SyslogServerEventIF;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by snow on 18/11/2017.
 * <p>
 * SyslogEvent 基类
 */
public class SyslogEvent implements SyslogServerEventIF {
    protected byte[] raw;
    protected int priVal;
    protected int facility;
    protected int level;
    protected String timestamp;
    protected String host;
    protected String message;

    protected static final String CHARSET = "UTF-8";

    protected String serviceName;       //服务名
    protected String className;     //类名

    protected int startPos;
    protected int endPos;
    protected int tempPos;

    public SyslogEvent() {

    }

    public SyslogEvent(byte[] data, int offset, int length) {
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

        startPos = searchChar(raw, startPos, '<') + 1;
        endPos = searchChar(raw, startPos, '>');
        priVal = Integer.parseInt(getString(raw, startPos, endPos));
        level = priVal & 7;
        facility = (priVal - level) >> 3;

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
            new LoggingSyslog(this, LoggingSyslog.ERROR_LOG);
            return;
        }

        //TODO: distinguish normal logs between RequestSyslog and LoggingSyslog

        if (level==6){
            int lastPos=raw.length;
            int beforeLastPos=0;
            for (int i = lastPos-1; i >lastPos-10; i--) {
                if (raw[i]==' '){
                    beforeLastPos=i;
                }
            }
            String numOrNot=getString(raw,beforeLastPos+1,lastPos);
            System.out.println(numOrNot);
            if (isRequestLog(numOrNot)){
                new RequestSyslog(this);
            }else {
                new LoggingSyslog(this,LoggingSyslog.NORMAL_LOG);
            }
        }


    }

    private boolean isRequestLog(String msg){
//        String infoPattern="\\d{1,3}(:\\d){2,}";
        String infoPattern="[0-9]+";
        return Pattern.matches(infoPattern,msg);
    }

    protected String getString(byte[] data, int startPos, int endPos) {
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
    protected int searchChar(byte[] data, int startPos, char c) {
        for (int i = startPos; i < data.length; i++) {
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

    @Override
    public String toString() {
        return "\nfacility: " + facility + "\n" +
                "level: " + level + "\n" +
                "timestamp: " + timestamp + "\n" +
                "host: " + host + "\n" +
                "serviceName: " + serviceName + "\n" +
                "className: " + className + "\n" +
                "message: " + message + "\n";

        //return "doing...";
    }
}
