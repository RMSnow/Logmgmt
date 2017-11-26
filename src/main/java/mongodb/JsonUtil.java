package mongodb;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import entity.Result;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WYJ on 2017/11/12.
 */
public class JsonUtil {


    public static String parseFindIterableToJson(FindIterable<Document> it) {

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
        return result;
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

    }
}