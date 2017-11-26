package mongodb;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;

/**
 * Created by WYJ on 2017/11/7.
 */
public class MongoConnector {

    //TODO: build a connection pool or release connections

    private static final String host = "localhost";
    private static final int port = 27017;

    private static MongoDatabase getDatabaseConnection(String dbName) {
        MongoDatabase db = null;
        try {
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient(host, port);

            // 连接到数据库
            db = mongoClient.getDatabase(dbName);

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