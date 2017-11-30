package mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Created by WYJ on 2017/11/7.
 */
public class MongoConnector {

    /*
    * This connector seems to function
    * though PORT is not used.
    *
    * Need more tests.
     */

    public static String HOST ;
    public static int PORT ;

    private final static int POOLSIZE=100;
    private final static int BLOCKSIZE=100;
    private static MongoClient client;

    static {
        init();
    }
    private static void init(){
        try {
            MongoClientOptions.Builder builder=new MongoClientOptions.Builder();
            builder.connectionsPerHost(POOLSIZE);
            builder.threadsAllowedToBlockForConnectionMultiplier(BLOCKSIZE);

            MongoClientOptions myOpt=builder.build();

            client=new MongoClient(HOST,myOpt);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static MongoDatabase getDatabaseConnection(String dbName) {
        MongoDatabase db = null;
        try {
            // 连接到 mongodb 服务
//            MongoClient mongoClient = new MongoClient(HOST, PORT);
            // 连接到数据库
            db = client.getDatabase(dbName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return db;
    }
    public static MongoCollection<Document> getCollection(String dbName, String collecitonName) {
        MongoDatabase db = getDatabaseConnection(dbName);
        return db.getCollection(collecitonName);
    }
}