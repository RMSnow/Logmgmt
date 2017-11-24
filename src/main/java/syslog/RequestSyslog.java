package syslog;

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
    private String clientIP;
    private String datetime;
    private String method;
    private String url;
    private int status;
    private String client;


    public RequestSyslog(SyslogEvent event) {
//        int startPos = event.startPos;
//        int endPos = event.endPos;
//        int tempPos = event.tempPos;
//        byte[] raw = event.raw;

        //System.out.println(new String(event.getRaw()));

        init(event);

        //time
        startPos = endPos + 1;
        tempPos = searchChar(raw, startPos, ' ');
        tempPos = searchChar(raw, tempPos + 1, ' ');
        endPos = searchChar(raw, tempPos + 1, ' ');
        timestamp = getString(raw, startPos, endPos);

        //hostname
        startPos=endPos+1;
        endPos=searchChar(raw, startPos, ' ');
        host=getString(raw, startPos, endPos);

        //name
        startPos = endPos + 1;
        endPos = searchChar(raw, startPos, '[');
        serviceName = getString(raw, startPos, endPos);

        //clientIP
        startPos = endPos + 1;
        tempPos = searchChar(raw, startPos, ' ');
        tempPos = searchChar(raw, tempPos + 1, ' ');
        tempPos = searchChar(raw, tempPos + 1, ' ');
        startPos = tempPos + 1;
        endPos = searchChar(raw, startPos, ' ');
        clientIP = getString(raw, startPos, endPos);

        //date
        startPos = endPos + 1;
        startPos = searchChar(raw, startPos, '[') + 1;
        endPos = searchChar(raw, startPos, ' ');
        datetime = getString(raw, startPos, endPos);
        //System.out.println(datetime);

        //method
        startPos = endPos + 1;
        startPos = searchChar(raw, startPos, '\"') + 1;
        endPos = searchChar(raw, startPos, ' ');
        method = getString(raw, startPos, endPos);
        //System.out.println(method);

        //url
        startPos = endPos + 1;
        endPos = searchChar(raw, startPos, '\"');
        url = getString(raw, startPos, endPos);
        //System.out.println(url);

        //status
        startPos = endPos + 1;
        tempPos = searchChar(raw, startPos, ' ');
        startPos = tempPos + 1;
        endPos = searchChar(raw, startPos, ' ');
        status = Integer.valueOf(getString(raw, startPos, endPos));
        //System.out.println(status);

        //client
        startPos = endPos + 1;
        tempPos = searchChar(raw, startPos, '\"');
        tempPos = searchChar(raw, tempPos + 1, '\"');
        tempPos = searchChar(raw, tempPos + 1, '\"');
        startPos = tempPos + 1;
        endPos = searchChar(raw, startPos, '\"');
        client = getString(raw, startPos, endPos);
        //System.out.println(client);

        System.out.println(toString());

        /**
         * 将日志存储到数据库中
         */
        SyslogService.addRequestLog(this);


    }

    /**
     * 域的初始化
     * @param event
     */
    private void init(SyslogEvent event){
        this.facility = event.facility;
        this.level = event.level;

        this.startPos = event.startPos;
        this.endPos = event.endPos;
        this.tempPos = event.tempPos;
        this.raw = event.raw;
    }

    public String getClientIP() {
        return clientIP;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public int getStatus() {
        return status;
    }

    public String getClient() {
        return client;
    }
    public String getServiceName(){
        return serviceName;
    }

    @Override
    public String toString() {
        return "facility: " + facility + "\n" +
                "level: " + level + "\n" +
                "timestamp: " + timestamp + "\n" +
                "host: " + host + "\n" +
                "serviceName: " + serviceName + "\n" +
                "clientIP: " + clientIP + "\n" +
                "datetime: " + datetime + "\n" +
                "method: " + method + "\n" +
                "url: " + url + "\n" +
                "status: " + status + "\n" +
                "client: " + client + "\n";
    }

}
