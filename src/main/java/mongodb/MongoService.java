package mongodb;

import mongodb.dao.RequestLogDao;
import mongodb.dao.LoggingLogDao;

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