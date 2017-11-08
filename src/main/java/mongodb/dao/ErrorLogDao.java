package mongodb.dao;

import com.mongodb.client.MongoCollection;
import mongodb.MongoConnector;
import org.bson.Document;
import orm.ErrorLog;

/**
 * Created by WYJ on 2017/11/8.
 */
public class ErrorLogDao {
    private static final String DB_NAME="logs";

    private static final String COLLECTION_NAME="error";


    private static final String KEY_ID="_id";
    private static final String KEY_IP="ip";
    private static final String KEY_NAME="name";
    private static final String KEY_URL="url";
    private static final String KEY_DATETIME="datetime";
    private static final String KEY_DATA="data";

    MongoCollection<Document> collection;

    public ErrorLogDao(){
        collection= MongoConnector.getCollection(DB_NAME,COLLECTION_NAME);
    }

    public String queryAll(){
        return null;
    }

    public String queryByParam(String ip,String name,String datetime){
        return null;
    }

    public void add(ErrorLog errorLog){

    }

    public void delete(String id){

    }

}
