package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WYJ on 2017/11/27.
 */
public class MongoResult {
    long resultNum;
    ArrayList results;
    boolean acknowledged;

    public MongoResult(ArrayList results) {
        this.resultNum = results.size();
        this.results = results;
        this.acknowledged=true;
    }
    public MongoResult(long resultNum, boolean acknowledged){
        this.resultNum=resultNum;
        this.acknowledged=acknowledged;
    }

    public long getResultNum() {
        return resultNum;
    }

    public void setResultNum(long resultNum) {
        this.resultNum = resultNum;
    }

    public ArrayList getResults() {
        return results;
    }

    public void setResults(ArrayList results) {
        this.results = results;
    }

}
