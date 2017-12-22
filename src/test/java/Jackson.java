import com.fasterxml.jackson.core.JsonProcessingException;
import entity.RequestsOfScale;

import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Created by snow on 07/12/2017.
 */
public class Jackson {
    public static void main(String[] args) throws JsonProcessingException {
//        Record record = new Record();
//        record.setHourRequests(10);
//        record.setRequestExceptions(20);
//
//        record.testJsonTable = new Hashtable<>();
//        record.testJsonTable.put("test1",1);
//        record.testJsonTable.put("test2",2);
//        record.testJsonTable.put("test3",3);
//
//        record.testJsonArrayList = new ArrayList<>();
//        record.testJsonArrayList.add(record.testJsonTable);
//        record.testJsonArrayList.add(record.testJsonTable);
//
//        record.testJsonDouble = new Double[3];
//        record.testJsonDouble[0] = 1.0;
//        record.testJsonDouble[1] = 2.0;
//        record.testJsonDouble[2] = 3.0;
//
//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(record);
//        System.out.println(json);

        RequestsOfScale[] rates = new RequestsOfScale[12];
        for (int j = 0; j < 12; j++) {
            RequestsOfScale rate = new RequestsOfScale(new Date().toString() + "_" + j,
                    100);
            rates[j] = rate;
            System.out.println(rate);
        }
        LinkedHashMap<String, RequestsOfScale[]> testMap = new LinkedHashMap<>();
        testMap.put("test",rates);

        System.out.println("ok");
    }
}

/*
{
    "id":null,
    "serviceName":null,
    "mostURI":null,
    "mostRequests":null,
    "loggingErrors":null,
    "requestExceptions":20,
    "hourRequests":10,
    "secondRequestsOfScale":null,

    "testJsonTable":{"test3":3,"test2":2,"test1":1},

    "testJsonArrayList":[
        {"test3":3,"test2":2,"test1":1},
        {"test3":3,"test2":2,"test1":1}
    ],

    "testJsonDouble":[1.0,2.0,3.0]
}

 */