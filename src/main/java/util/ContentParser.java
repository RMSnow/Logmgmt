package util;

import orm.Log;

/**
 * 解析日志的content属性
 */
public class ContentParser {
    public static Log getAttrFromContent(String name, String content){
        Log log = new Log();
        log.setName(name);

        //解析content
//        {
//            "_id" : ObjectID("[特定值]"),
//                "name" : "courseservice",
//                "ip" : "123.207.73.150",
//                "dateTime" : "04/Nov/2017:11:43:54",
//                "url" : "GET /application/api/v2/class? HTTP/1.1",
//                "status" : 200,
//                "client" : "Apache-HttpClient/4.5.2 (Java/1.8.0_151)"
//        }



        return log;
    }
}

/*
content的形式1
*/

//        123.207.73.150 - -
//        [04/Nov/2017:12:16:52 +0000]
//        "GET /application/api/v2/course/123? HTTP/1.1"
//        200 44
//        "-"
//        "Apache-HttpClient/4.5.2 (Java/1.8.0_151)"
//        1

//        123.207.73.150 - -
//        [04/Nov/2017:12:16:52 +0000]
//        "GET /application/api/v2/choice?isDelete=0&courseId=123&userId=45& HTTP/1.1"
//        200 43
//        "-"
//        "Apache-HttpClient/4.5.2 (Java/1.8.0_151)"
//        3

        /*
            content的形式2（有一个缺失）
         */

//        123.207.73.150 - -
//        [04/Nov/2017:11:43:53 +0000]
//        "GET /application/api/v2/subject? HTTP/1.1"
//        200 -
//        "-"
//        "Apache-HttpClient/4.5.2 (Java/1.8.0_151)"
//        346


        /*
            content的形式3（对其他对错误形式处理）
         */