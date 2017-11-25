package mongodb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import org.bson.Document;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WYJ on 2017/11/12.
 */
public class JsonUtil {

    //TODO: parse results to JSON format, not string array.

    public static String parseFindIterableToJsonArray(FindIterable<Document> it) {
        List<String> results = new ArrayList<>();
        String json = null;
        StringWriter writer=new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        for (Document d : it) {
            results.add(d.toJson());
//            System.out.println(d.toJson());
        }
        try {
            mapper.writeValue(writer,it);
            System.out.println(writer.toString());
            json = mapper.writeValueAsString(results);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return json;
    }
}