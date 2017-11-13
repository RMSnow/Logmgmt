package mongodb;

import mongodb.dao.AccessLogDao;
import mongodb.dao.StandardLogDao;

/**
 * Created by WYJ on 2017/11/7.
 */
public class MongoService {


    public static AccessLogDao getLogCollection() {
        return new AccessLogDao();
    }

    public static StandardLogDao getErrorLogCollection() {
        return new StandardLogDao();
    }

}