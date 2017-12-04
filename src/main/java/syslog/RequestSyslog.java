package syslog;

/**
 * 处理访问API的日志
 */
public class RequestSyslog extends SyslogEvent {
    private String clientIP;
    private String datetime;
    private String method;
    private String url;
    private int status;
    private String client;

    public RequestSyslog(SyslogEvent event) {
        init(event);

        //time
//        startPos = endPos + 1;
//        tempPos = searchChar(raw, startPos, ' ');
//        tempPos = searchChar(raw, tempPos + 1, ' ');
//        endPos = searchChar(raw, tempPos + 1, ' ');
//        timestamp = getString(raw, startPos, endPos);
//
//        //hostname
//        startPos = endPos + 1;
//        endPos = searchChar(raw, startPos, ' ');
//        host = getString(raw, startPos, endPos);

        //name
        startPos = endPos + 1;
        endPos = searchChar(raw, startPos, '[');
        serviceName = getString(raw, startPos, endPos);

        startPos = endPos + 1;
        startPos = searchChar(raw, startPos, '[') + 1;
        endPos = searchChar(raw, startPos, ']');
        className = getString(raw, startPos, endPos);

        //clientIP
        startPos = endPos + 1;
        tempPos = searchChar(raw, startPos, ' ');
        tempPos = searchChar(raw, tempPos + 1, ' ');
        startPos = tempPos + 1;
        endPos = searchChar(raw, startPos, ' ');
        clientIP = getString(raw, startPos, endPos);

        //date
        startPos = endPos + 1;
        startPos = searchChar(raw, startPos, '[') + 1;
        endPos = searchChar(raw, startPos, ' ');
        datetime = getString(raw, startPos, endPos);

        //method
        startPos = endPos + 1;
        startPos = searchChar(raw, startPos, '\"') + 1;
        endPos = searchChar(raw, startPos, ' ');
        method = getString(raw, startPos, endPos);

        //"GET /application/api/v2/heart?code=8c9742 HTTP/1.1" 200 29 "-" "Apache-HttpClient/4.5.2 (Java/1.8.0_151)" 1

        //url
        startPos = endPos + 1;
        endPos = searchChar(raw, startPos, ' ');
        url = getString(raw, startPos, endPos);

        //过滤心跳包
//        int start, end, temp;
//        start = startPos + 1;
//        temp = searchChar(raw, start, '/');
//        temp = searchChar(raw, temp + 1, '/');
//        start = searchChar(raw, temp + 1, '/') + 1;
//        end = searchChar(raw, start, '?');
//        if (end < endPos && end != -1) {
//            String uri = getString(raw, start, end);
//            if (uri.equals("heart")) {
//                return;
//            }
//        }
//        String uri = getString(raw,start,endPos);       //无参数列表
//        if (uri.equals("heart")){
//            return;
//        }

        //status
        startPos = endPos + 1;
        tempPos = searchChar(raw, startPos, ' ');
        startPos = tempPos + 1;
        endPos = searchChar(raw, startPos, ' ');
        status = Integer.valueOf(getString(raw, startPos, endPos));

        //client
        startPos = endPos + 1;
        tempPos = searchChar(raw, startPos, '\"');
        tempPos = searchChar(raw, tempPos + 1, '\"');
        tempPos = searchChar(raw, tempPos + 1, '\"');
        startPos = tempPos + 1;
        endPos = searchChar(raw, startPos, '\"');
        client = getString(raw, startPos, endPos);

        System.out.println(this);
        SyslogService.addRequestLog(this);
    }

    /**
     * 域的初始化
     *
     * @param event
     */
    private void init(SyslogEvent event) {
        this.facility = event.facility;
        this.level = event.level;
        this.timestamp = event.timestamp;
        this.host = event.host;

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

    public String getServiceName() {
        return serviceName;
    }

    @Override
    public String toString() {
        return "[facility]\t" + facility + "\n" +
                "[level]\t" + level + "\n" +
                "[timestamp]\t" + timestamp + "\n" +
                "[host]\t" + host + "\n" +
                "[serviceName]\t" + serviceName + "\n" +
                "[clientIP]\t" + clientIP + "\n" +
                "[datetime]\t" + datetime + "\n" +
                "[method]\t" + method + "\n" +
                "[url]\t" + url + "\n" +
                "[status]\t" + status + "\n" +
                "[client]\t" + client + "\n";
    }

}
