package mongodb;

import com.mongodb.client.FindIterable;
import entity.MongoResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WYJ on 2017/11/12.
 */
public class JsonUtil {


    public static MongoResult parseFindIterableToQueryResult(FindIterable<Document> it) {

        int index=0;
        String result="{";
        for (Document d : it) {
            if(index>0){
                result += ",";
            }
            result += String.format(" \"%d\" : ", index);
            result += d.toJson();
            index++;
        }
        result +="}";
//        return new MongoResult();
        return null;

//        String json = null;
//        StringWriter writer=new StringWriter();
//        JsonFactory factory=new JsonFactory();
//        JsonGenerator generator;
//        try {
//            generator=factory.createGenerator(writer);
//            generator.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES,false);
//            generator.writeStartObject();
//            for (Document d : it) {
//                generator.writeFieldName(String.valueOf(index));
//                generator.writeString(d.toJson());
//                index++;
//            }
//            generator.writeEndObject();
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        return json;

    }
    public ArrayList parseFindIterableToArrayList(FindIterable<Document> it){
        ArrayList list=new ArrayList<>();
        for (Document d:it){
            list.add(d.toJson());
        }
        return list;
    }
}