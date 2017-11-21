package syslog.demo;

import mongodb.MongoService;
import orm.AccessLog;

import java.util.regex.Pattern;

/**
 * Created by snow on 18/11/2017.
 * <p>
 * 处理访问API的日志
 */
public class RequestSyslog extends SyslogEvent {

    /*
    RequestLog:
    <134>Nov 17 14:28:39 DESKTOP-9EODV8A couseservice[12624]: [dw-17] http.request 0:0:0:0:0:0:0:1 - - [17/十一月/2017:06:28:39 +0000] "GET /application/kk HTTP/1.1" 404 43 "-" "PostmanRuntime/6.4.1" 231
    InfoLog:
    <134>Nov 20 22:30:53 DESKTOP-9EODV8A couseservice[8796]: [main] org.eclipse.jetty.server.Server jetty-9.2.13.v20150730
     */

    //access log
    private String datetime;
    private String method;
    private String url;
    private int status;
    private String client;


    public RequestSyslog(SyslogEvent event) {
        int startPos=event.startPos;
        int endPos=event.endPos;
        int tempPos=event.tempPos;
        byte[] raw=event.raw;


        System.out.println(new String(event.getRaw()));

        //time
        startPos = endPos + 1;
        tempPos = searchChar(raw, startPos, ' ');
        tempPos = searchChar(raw, tempPos + 1, ' ');
        endPos = searchChar(raw, tempPos + 1, ' ');
        timestamp = getString(raw, startPos, endPos);
        System.out.printf(timestamp + "\n");

        //name
        startPos = endPos + 1;
        startPos=searchChar(raw,startPos,' ')+1;
        endPos = searchChar(raw, startPos, '[');
        serviceName = getString(raw, startPos, endPos);
        System.out.printf(serviceName+"\n");

        //host or not
        startPos = endPos + 1;
        tempPos=searchChar(raw,startPos,' ');
        tempPos=searchChar(raw,tempPos+1,' ');
        tempPos=searchChar(raw,tempPos+1,' ');
        startPos=tempPos+1;
        endPos = searchChar(raw, startPos, ' ');
        host = getString(raw, startPos, endPos);
        System.out.printf(host + "\n");

        //date
        startPos=endPos+1;
        startPos=searchChar(raw,startPos,'[')+1;
        endPos=searchChar(raw,startPos,' ');
        datetime=getString(raw,startPos,endPos);
        System.out.println(datetime);

        //method
        startPos=endPos+1;
        startPos=searchChar(raw,startPos,'\"')+1;
        endPos=searchChar(raw,startPos,' ');
        method=getString(raw,startPos,endPos);
        System.out.println(method);

        //url
        startPos=endPos+1;
        endPos=searchChar(raw,startPos,'\"');
        url=getString(raw,startPos,endPos);
        System.out.println(url);

        //status
        startPos=endPos+1;
        tempPos=searchChar(raw,startPos,' ');
        startPos=tempPos+1;
        endPos=searchChar(raw,startPos,' ');
        status=Integer.valueOf( getString(raw,startPos,endPos));
        System.out.println(status);

        //client
        startPos=endPos+1;
        tempPos=searchChar(raw,startPos,'\"');
        tempPos=searchChar(raw,tempPos+1,'\"');
        tempPos=searchChar(raw,tempPos+1,'\"');
        startPos=tempPos+1;
        endPos=searchChar(raw,startPos,'\"');
        client=getString(raw,startPos,endPos);
        System.out.println(client);

        AccessLog log=new AccessLog();
        log.setClient(client);
        log.setDatetime(datetime);
        log.setStatus(status);
        log.setHost(host);
        log.setMethod(method);
        log.setName(serviceName);
        log.setUrl(url);
        MongoService.getAccessLogCollection().add(log);
    }

}
