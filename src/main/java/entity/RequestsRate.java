package entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 一个小时之内，每五分钟的秒访问率
 */
public class RequestsRate {
    @JsonProperty
    private String timescale;
    @JsonProperty
    private int requests;
    @JsonProperty
    private double rate;

    public RequestsRate(String timescale, int requests) {
        this.timescale = timescale;
        this.requests = requests;

        rate = requests / 300;
    }

    public String getTimescale() {
        return timescale;
    }

    public void setTimescale(String timescale) {
        this.timescale = timescale;
    }

    public int getRequests() {
        return requests;
    }

    public void setRequests(int requests) {
        this.requests = requests;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String toString() {
        return "timescale = " + timescale + ", rate = " + rate;
    }
}
