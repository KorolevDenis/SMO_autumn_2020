package app.entity;

public class StepModBufferRow {
    private int index;
    private int requestNumber;
    private double genTime;

    public StepModBufferRow(int index, int requestNumber, double genTime) {
        this.index = index;
        this.requestNumber = requestNumber;
        this.genTime = genTime;
    }

    public int getIndex() {
        return index;
    }

    public int getRequestNumber() {
        return requestNumber;
    }

    public double getGenTime() {
        return genTime;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setRequestNumber(int requestNumber) {
        this.requestNumber = requestNumber;
    }

    public void setGenTime(double genTime) {
        this.genTime = genTime;
    }
}
