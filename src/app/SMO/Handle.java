package app.SMO;

public class Handle {
    private int number = 0;
    private int requestNumber = 0;
    private int prevRequestNumber = 0;
    private double lambda;
    private double endTime = 0;
    private double startTime = 0;
    private boolean isFree = true;
    private double workTime = 0;
    private double allTime = 0;

    public Handle(int number, double lambda) {
        this.number = number;
        this.lambda = lambda;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public void setRequestNumber(int requestNumber) {
        prevRequestNumber = this.requestNumber;
        this.requestNumber = requestNumber;
    }

    public boolean isFree() {
        return isFree;
    }

    public double getNextEndTime(double time) {
        startTime = time;
        endTime  = time + (1 - Math.exp(-lambda * Math.random()));
        workTime += endTime - time;
        return endTime;
    }

    public int getNumber() {
        return number;
    }

    public int getRequestNumber() {
        return requestNumber;
    }

    public double getCoefficient() {
        return workTime/allTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public int getPrevRequestNumber() {
        return prevRequestNumber;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setAllTime(double allTime) {
        this.allTime = allTime;
    }
}
