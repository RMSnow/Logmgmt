package mongodb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WYJ on 2017/11/12.
 */
public class JsonUtil {
    public static String parseFindIterableToJsonArray(FindIterable<Document> it) {
        List<String> results = new ArrayList<>();
        String json = null;
        ObjectMapper mapper = new ObjectMapper();
        for (Document d : it) {
            results.add(d.toJson());
        }
        try {
            json = mapper.writeValueAsString(results);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}