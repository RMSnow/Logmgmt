package syslog.demo;

import java.io.UnsupportedEncodingException;

/**
 * Created by snow on 18/11/2017.
 *
 * 处理运行API的日志，以及访问API产生的异常日志
 */
public class OtherSyslog extends SyslogEvent{

    public OtherSyslog(SyslogEvent event){

        /**
         * 运行API时产生的日志
         */

//        raw = new byte[length - offset];
//        System.arraycopy(data, offset, raw, 0, length);

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

        //PRI: <131>、<132>、<134>等
        startPos = searchChar(raw, startPos, '<') + 1;
        endPos = searchChar(raw, startPos, '>');
        priVal = Integer.parseInt(getString(raw, startPos, endPos));
        level = priVal % 8;
        facility = (priVal - level) / 8;
        System.out.printf(priVal + "\t" + level + "\t" + facility +"\n");

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

        //message
        startPos = endPos +1;
        endPos = raw.length;
        message = getString(raw, startPos,endPos);
        System.out.printf(message + "\n");
    }
}
