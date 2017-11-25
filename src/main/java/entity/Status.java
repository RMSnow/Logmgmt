package entity;

/**
 * Result信息的状态码
 */
public class Status {
    //OK - [GET]：服务器成功返回用户请求的数据
    public static final int OK = 200;

//    //CREATED - [POST/PUT/PATCH]：用户新建或修改数据成功
//    public static final int CREATED = 201;
//
//    //NO CONTENT - [DELETE]：用户删除数据成功
//    public static final int NO_CONTENT = 204;
//
//    //INVALID REQUEST - [POST/PUT/PATCH]：用户发出的请求有错误，服务器没有进行新建或修改数据的操作
//    public static final int INVALID_REQUEST = 400;

    //NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作
    public static final int NOT_FOUND = 404;

    //INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功
    public static final int SERVER_ERROR = 500;

}