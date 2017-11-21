package syslog.toDelete;

import org.productivity.java.syslog4j.server.SyslogServerEventIF;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by WYJ on 2017/11/16.
 */
public class AccessSyslogEvent implements SyslogServerEventIF {
    /*
<134>Nov 17 14:28:39 DESKTOP-9EODV8A couseservice[12624]: [dw-17] http.request 0:0:0:0:0:0:0:1 - - [17/十一月/2017:06:28:39 +0000] "GET /application/kk HTTP/1.1" 404 43 "-" "PostmanRuntime/6.4.1" 231
0 134
1 Nov
2 17
3 14:28:39
4 DESKTOP-9EODV8A
5 couseservice
6 12624
7 :
8 dw-17
9 http.request
10 0:0:0:0:0:0:0:1
11 -
12 -
13 17/十一月/2017:06:28:39
14 +0000
15 GET
16 /application/kk
17 HTTP/1.1
18 404
19 43
20 -
21 PostmanRuntime/6.4.1
     */
//    private static final int[] INDEX1_TIMESTAMP={1,2,3};
//    private static final int[] INDEX1_NAME={5};
//    private static final int[] INDEX1_HOST={10};
//    private static final int[] INDEX1_DATE={13};
//    private static final int[] INDEX1_METHOD={15};
//    private static final int[] INDEX1_URL={16,17};
//    private static final int[] INDEX1_STATUS={18};
//    private static final int[] INDEX2_FACILITY={5};
//    private static final char[] DELIMETER1={' ','<','>','[',']'};
//    private static final char[] DELIMETER2={'\"'};
    private final String host;
    private final String name;
    private final String timestamp;
    private final String datetime;
    private final String method;
    private final String url;
    private final int status;
    private final String facility;

    private static final long serialVersionUID = 1L;
    private static final String CHARSET = "UTF-8";
    private static final byte[] UTF_8_BOM = {(byte) 0xef, (byte) 0xbb, (byte) 0xbf};



    private final byte raw[];

    public AccessSyslogEvent(byte[] data, int offset, int length) {
        String rawStr = new String(data);
        System.out.println(rawStr);

        raw = new byte[length - offset];
        System.arraycopy(data, offset, raw, 0, length);

//        ArrayList<String> list1=multiSplit(data,DELIMETER1);
//        for (int i = 0; i < list1.size(); i++) {
//            System.out.println("[List1]"+i+" "+list1.get(i));
//        }
//        ArrayList<String> list2=multiSplit(data,DELIMETER2);
//        for (int i = 0; i < list2.size(); i++) {
//            System.out.println("[List2]"+i+" "+list2.get(i));
//        }
        System.out.println(data);
        int startPos = 0;
        int endPos = -1;
        int tempPos = endPos;
        //priority
        startPos = searchChar(raw, startPos, '<') + 1;
        endPos = searchChar(raw, startPos, '>');

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
        name = getString(raw, startPos, endPos);
        System.out.printf(name+"\n");

        //host
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

        //facility
        startPos=endPos+1;
        tempPos=searchChar(raw,startPos,'\"');
        tempPos=searchChar(raw,tempPos+1,'\"');
        tempPos=searchChar(raw,tempPos+1,'\"');
        startPos=tempPos+1;
        endPos=searchChar(raw,startPos,'\"');
        facility=getString(data,startPos,endPos);
        System.out.println(facility);
    }


    private static ArrayList<String> multiSplit(byte[] data,char[] delimeters){
        int pos=0;
        ArrayList<String> list=new ArrayList<String>();
        for (int i = 0; i < data.length; i++) {
            byte c=data[i];
            for (char delimeter:delimeters){
                if (c==delimeter) {
                    if(pos!=i){
                        list.add(new String(data, pos, i - pos));
                        pos = i + 1;
                    }else{
                        pos++;
                    }
                    break;
                }
            }

        }
        return list;
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

    @Override
    public byte[] getRaw() {
        return new byte[0];
    }

    @Override
    public int getFacility() {
        return 0;
    }

    @Override
    public void setFacility(int i) {

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
        return 0;
    }

    @Override
    public void setLevel(int i) {

    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public void setHost(String s) {

    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public void setMessage(String s) {

    }

    @Override
    public String getCharSet() {
        return null;
    }

    @Override
    public void setCharSet(String s) {
    }
}