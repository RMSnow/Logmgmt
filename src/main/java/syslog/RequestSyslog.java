package syslog;

import orm.Record;

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

    public RequestSyslog(SyslogEvent event) throws Exception {
        init(event);

        //name
        startPos = endPos + 1;
        endPos = searchChar(raw, startPos, '[');
        serviceName = getString(raw, startPos, endPos);

        Record record = generateNewRecord(serviceName);

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

        //<134>Dec  4 15:14:51 localhost registry[7613]:
        // [dw-14] http.request 221.232.92.29 - -
        // [04/Dec/2017:07:14:51 +0000] "GET /favicon.ico HTTP/1.1" 200
        // - "http://123.207.73.150:8000/application/api/v1/apis/list"
        // "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)
        // Chrome/58.0.3029.110 Safari/537.36" 0

        //过滤心跳包
        String heartCheck = "/application/api/v2/heart?";
        if (url.length() >= heartCheck.length()) {
            if (url.substring(0, heartCheck.length()).equals(heartCheck)) {
                return;
            }
        }

        //URI
        int paramPos = searchChar(raw, startPos, '?');
        int uriPos;
        String uri;
        if (paramPos < endPos && paramPos != -1) {       //url中存在参数列表
            uriPos = searchChar(raw, paramPos, '/', false) + 1;
            uri = getString(raw, uriPos, paramPos);
        } else {      //无参数列表
            uriPos = searchChar(raw, endPos, '/', false) + 1;
            uri = getString(raw, uriPos, endPos);
        }

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

        //日志分析
        analyzeRecord(record, uri, status);
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

    //进行日志分析
    public void analyzeRecord(Record record, String uri, int status) {
        //URI <==> apiRequestTable
        if (record.getApiRequestTable().containsKey(uri)) {
            int requests = record.getApiRequestTable().get(uri);
            record.putApiRequestTable(uri, requests + 1);
        } else {
            record.putApiRequestTable(uri, 1);
        }

        //requestsExceptions
        if (status != 200) {
            int exceptions = record.getRequestExceptions();
            record.setRequestExceptions(exceptions + 1);
        }

        //hourRequests & secondRequestsOfScale
        int hourRequests = record.getHourRequests();
        record.setHourRequests(hourRequests + 1);
    }

}
