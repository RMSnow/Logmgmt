package syslog;

import org.productivity.java.syslog4j.server.SyslogServerEventIF;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by snow on 15/11/2017.
 */
public class SyslogEvent implements SyslogServerEventIF {
    private static final long serialVersionUID = 1L;
    private static final char SP = ' ';
    private static final String CHARSET = "UTF-8";
    private static final String NIL = "-";
    private static final byte[] UTF_8_BOM = {(byte) 0xef, (byte) 0xbb, (byte) 0xbf};

    private final byte[] raw;

    private final String priority;
//    private final int facility;
//    private final int level;
//    private final int version;

    private final String timestamp;
    private final String host;
    private final String serviceName;
//    private final String procId;
//    private final String msgId;
//    private final String structuredData;
//    private final String message;

    //add
    private final String className;
    private final String content;

    /*
    (3)要存到数据库中的
        {
            "_id" : ObjectID("[特定值]"),
            "name" : "courseservice",
            "level" : 6,
            "timestamp" : ISODate("2017-11-04 11:43:42"),
            "class : "io.dropwizard.server.SimpleServerFactory",
            "data" : "Registering admin handler with root path prefix: /admin"
        }
     */

    /*
        <134>
        Nov 15 16:40:39
        snow.local
        couseservice[1039]:
        [main] org.eclipse.jetty.server.handler.ContextHandler
        Started i.d.j.MutableServletContextHandler@de77232{/admin,null,AVAILABLE}

        <134>
        Nov 15 16:40:39
        snow.local
        couseservice[1039]:
        [main] org.eclipse.jetty.server.Server
        Started
        @28191ms

        <132>
        Nov 15 17:14:48
        snow.local
        couseservice[1212]:
        [main] org.glassfish.jersey.internal.Errors
        The following warnings have been detected: WARNING: A resource, Resource{"api/v1/answer", 0 child resources, 0 resource methods, 0 sub-resource locator, 0 method handler classes, 0 method handler instances}, with path "api/v1/answer" is empty. It has no resource (or sub resource) methods neither sub resource locators defined.
        WARNING: A resource, Resource{"api/v1/homework", 0 child resources, 0 resource methods, 0 sub-resource locator, 0 method handler classes, 0 method handler instances}, with path "api/v1/homework" is empty. It has no resource (or sub resource) methods neither sub resource locators defined.
     */

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

        int startPos = 0;
        int endPos = -1;
        int tempPos = endPos;

        //level: <132>、<134>
        startPos = searchChar(raw, startPos, '<') + 1;
        endPos = searchChar(raw, startPos, '>');
        priority = getString(raw, startPos, endPos);
        System.out.printf(priority + "\n");

        //time: Nov 15 17:14:48
        startPos = endPos + 1;
        tempPos = searchChar(raw, startPos, ' ');
        tempPos = searchChar(raw, tempPos + 1, ' ');
        endPos = searchChar(raw, tempPos + 1, ' ');
        timestamp = getString(raw, startPos, endPos);
        System.out.printf(timestamp + "\n");

        //host: snow.local
        startPos = endPos + 1;
        endPos = searchChar(raw, startPos, ' ');
        host = getString(raw, startPos, endPos);
        System.out.printf(host + "\n");

        //name of service: courseservice
        startPos = endPos + 1;
        endPos = searchChar(raw, startPos, '[');
        serviceName = getString(raw, startPos, endPos);
        System.out.printf(serviceName+"\n");

        //class: org.eclipse.jetty.server.ServerConnector
        startPos = endPos + 1;
        tempPos = searchChar(raw, startPos, ']');
        startPos = searchChar(raw, tempPos + 1, ']') + 2;
        endPos = searchChar(raw,startPos,' ');
        className = getString(raw, startPos, endPos);
        System.out.printf(className + "\n");

        //content
        startPos = endPos +1;
        endPos = raw.length;
        content = getString(raw, startPos,endPos);
        System.out.printf(content + "\n");


//        procId = getString(raw, startPos, endPos);
//
//        startPos = endPos + 1;
//        endPos = searchChar(raw, startPos, ' ');
//        msgId = getString(raw, startPos, endPos);
//
//        startPos = endPos + 1;
//        if (raw[startPos] == '[') {
//            endPos = searchChar(raw, startPos, ']') + 1;
//        } else {
//            endPos = searchChar(raw, startPos, ' ');
//            if (endPos == -1)
//                endPos = raw.length;
//        }
//        structuredData = getString(raw, startPos, endPos);
//
//        startPos = endPos + 1;
//        if (startPos < raw.length) {
//            if (startPos + 3 < raw.length && raw[startPos] == UTF_8_BOM[0] && raw[startPos + 1] == UTF_8_BOM[1]
//                    && raw[startPos + 2] == UTF_8_BOM[2]) {
//                startPos += 3;
//            }
//            message = getString(raw, startPos, raw.length);
//        } else {
//            message = null;
//        }
//
//        // parse priority and version
//        endPos = priority.indexOf(">");
//        final String priorityStr = priority.substring(1, endPos);
//        int priority = 0;
//        try {
//            priority = Integer.parseInt(priorityStr);
//        } catch (NumberFormatException nfe) {
//            System.err.println("Can't parse priority");
//        }
//
//        level = priority & 7;
//        facility = (priority - level) >> 3;
//
//        startPos = endPos + 1;
//        int ver = 0;
//        if (startPos < this.priority.length()) {
//            try {
//                ver = Integer.parseInt(this.priority.substring(startPos));
//            } catch (NumberFormatException nfe) {
//                System.err.println("Can't parse version");
//                ver = -1;
//            }
//        }
//        version = ver;
    }

    private String getString(byte[] data, int startPos, int endPos) {
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
    private int searchChar(byte[] data, int startPos, char c) {
        for (int i = startPos; i < data.length; i++) {
            if (data[i] == c) {
                return i;
            }
        }
        return -1;
    }

    public String getPriority() {
        return priority;
    }

//    public int getFacility() {
//        return facility;
//    }
//
//    public int getLevel() {
//        return level;
//    }
//
//    public int getVersion() {
//        return version;
//    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getHost() {
        return host;
    }

    public String getServiceName() {
        return serviceName;
    }

//    public String getProcId() {
//        return procId;
//    }
//
//    public String getMsgId() {
//        return msgId;
//    }
//
//    public String getStructuredData() {
//        return structuredData;
//    }
//
//    public String getMessage() {
//        return message;
//    }

    public String getCharSet() {
        return CHARSET;
    }

    public byte[] getRaw() {
        return raw;
    }

    @Override
    public int getFacility() {
        return 0;
    }

    public Date getDate() {
        if (NIL.equals(timestamp)) {
            return null;
        }
        String fixTz = timestamp.replace("Z", "+00:00");
        final int tzSeparatorPos = fixTz.lastIndexOf(":");
        fixTz = fixTz.substring(0, tzSeparatorPos) + fixTz.substring(tzSeparatorPos + 1);
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ").parse(fixTz);
        } catch (ParseException e) {
            System.err.println("Unable to parse date " + timestamp);
        }
        return null;
    }

    public void setCharSet(String charSet) {
    }

    public void setFacility(int facility) {
    }

    public void setDate(Date date) {
    }

    @Override
    public int getLevel() {
        return 0;
    }

    public void setLevel(int level) {
    }

    public void setHost(String host) {
    }

    @Override
    public String getMessage() {
        return null;
    }

    public void setMessage(String message) {
    }

    @Override
    public String toString() {
//        return "Rfc5424SyslogEvent [priority=" + priority + ", facility=" + facility + ", level=" + level
//                + ", version=" + version + ", timestamp=" + timestamp + ", host=" + host + ", serviceName=" + serviceName
//                + ", procId=" + procId + ", msgId=" + msgId + ", structuredData=" + structuredData + ", message="
//                + message + "]";
        return "doing...";
    }

}
