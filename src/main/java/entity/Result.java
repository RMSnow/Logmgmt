package entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
    @JsonProperty
    private Object msg;
    @JsonProperty
    private int status;
    @JsonProperty
    private Object data;

    public Result(Object msg, int status, Object data) {
        this.msg = msg;
        this.status = status;
        this.data = data;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}


