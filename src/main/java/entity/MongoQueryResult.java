package entity;

/**
 * Created by WYJ on 2017/11/27.
 */
public class MongoQueryResult {
    int resultNum;
    String jsonResult;

    public MongoQueryResult(int resultNum, String jsonResult) {
        this.resultNum = resultNum;
        this.jsonResult = jsonResult;
    }

    public int getResultNum() {
        return resultNum;
    }

    public void setResultNum(int resultNum) {
        this.resultNum = resultNum;
    }

    public String getJsonResult() {
        return jsonResult;
    }

    public void setJsonResult(String jsonResult) {
        this.jsonResult = jsonResult;
    }

    @Override
    public String toString() {
        return "MongoQueryResult{" +
                "resultNum=" + resultNum +
                ", jsonResult='" + jsonResult + '\'' +
                '}';
    }
}
