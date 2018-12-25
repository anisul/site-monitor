package types;

public class ResponseStat {
    boolean available;
    double responseTime;

    public ResponseStat(boolean available, double responseTime) {
        this.available = available;
        this.responseTime = responseTime;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public double getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(double responseTime) {
        this.responseTime = responseTime;
    }
}
