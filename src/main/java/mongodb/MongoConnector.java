package mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;

/**
 * Created by WYJ on 2017/11/7.
 */
public class MongoConnector {

    public static String host;
    public static int port;
    public static String userName;
    public static String password;
    public static final String DB_NAME = "logs";
    public static boolean init=false;


    private final static int POOLSIZE=100;
    private final static int BLOCKSIZE=100;
    private static MongoClient client;


    public static void init() {
        if (init){
            return;
        }
        try {
            MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
            builder.connectionsPerHost(POOLSIZE);
            builder.threadsAllowedToBlockForConnectionMultiplier(BLOCKSIZE);
            MongoClientOptions myOpt = builder.build();

            MongoCredential credential=MongoCredential.createCredential(userName,DB_NAME,password.toCharArray());
            client = new MongoClient(
                    new ServerAddress(host, port),
                    Arrays.asList(credential),
                    myOpt
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static MongoDatabase getDatabaseConnection(String dbName) {
        MongoDatabase db = null;
        try {
            // 连接到 mongodb 服务
//            MongoClient mongoClient = new MongoClient(host, port);
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