package mongodb;

import mongodb.dao.LogDao;

/**
 * Created by WYJ on 2017/11/7.
 */
public class MongoService {


    public static LogDao getLogCollection(){
        return new LogDao();
    }

}
