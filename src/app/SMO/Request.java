package app.SMO;

public class Request implements Comparable<Request>{
    private int number = 0; // numberSource + countRequest
    private double beginTime = 0;
    private double endTime = 0;

    public Request(int number, double beginTime, double endTime) {
        this.number = number;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    @Override
    public int compareTo(Request o) {
        return Double.compare(this.beginTime, o.beginTime);
    }

    public double getBeginTime() {
        return beginTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public int getNumber() {
        return number;
    }
}
