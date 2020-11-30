package app.entity;

public class StepModHandleRow {
    private int handleNumber;
    private int requestNumber;
    private double releaseTime;

    public StepModHandleRow(int handleNumber, int requestNumber, double releaseTime) {
        this.handleNumber = handleNumber;
        this.requestNumber = requestNumber;
        this.releaseTime = releaseTime;
    }

    public int getHandleNumber() {
        return handleNumber;
    }

    public int getRequestNumber() {
        return requestNumber;
    }

    public double getReleaseTime() {
        return releaseTime;
    }

    public void setHandleNumber(int handleNumber) {
        this.handleNumber = handleNumber;
    }

    public void setRequestNumber(int requestNumber) {
        this.requestNumber = requestNumber;
    }

    public void setReleaseTime(double releaseTime) {
        this.releaseTime = releaseTime;
    }
}
