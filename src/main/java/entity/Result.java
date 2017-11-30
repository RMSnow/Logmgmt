package entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
    private Object msg;
    private int status;
    private Object data;

    public Result(Object msg, int status, Object data) {
        this.msg = msg;
        this.status = status;
        this.data = data;
    }

    @JsonProperty
    public Object getMsg() {
        return msg;
    }

    @JsonProperty
    public void setMsg(Object msg) {
        this.msg = msg;
    }

    @JsonProperty
    public int getStatus() {
        return status;
    }

    @JsonProperty
    public void setStatus(int status) {
        this.status = status;
    }

    @JsonProperty
    public Object getData() {
        return data;
    }

    @JsonProperty
    public void setData(Object data) {
        this.data = data;
    }
}


