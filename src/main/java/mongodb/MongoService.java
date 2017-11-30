package mongodb;

import mongodb.dao.LoggingLogDao;
import mongodb.dao.RequestLogDao;

/**
 * Created by WYJ on 2017/11/7.
 */
public class MongoService {


    public static RequestLogDao getRequestLogCollection() {
        return new RequestLogDao();
    }

    public static LoggingLogDao getLoggingLogCollection() {
        return new LoggingLogDao();
    }

}