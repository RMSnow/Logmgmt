package orm.record;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 一个时间段内的请求
 */
public class RequestsOfScale {
    @JsonProperty
    private String timescale;
    @JsonProperty
    private int requests;
    @JsonProperty
    private double rate;

    public RequestsOfScale(String timescale, int requests) {
        this.timescale = timescale;
        this.requests = requests;

        rate = requests / 300;      //TODO: 确认合适的scale
    }

    public RequestsOfScale(String timescale, double rate) {
        this.timescale = timescale;
        this.rate = rate;

        requests = (int) (rate * 300);
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
