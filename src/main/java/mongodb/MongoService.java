package mongodb;

import mongodb.dao.ErrorLogDao;
import mongodb.dao.LogDao;

/**
 * Created by WYJ on 2017/11/7.
 */
public class MongoService {


    public static LogDao getLogCollection(){
        return new LogDao();
    }
    public static ErrorLogDao getErrorLogCollection(){
        return new ErrorLogDao();
    }

}
