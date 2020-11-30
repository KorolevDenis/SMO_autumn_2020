package app.entity;

public class StepModEventRow {
    private String type;
    private int requestNumber;
    private double eventTime;

    public StepModEventRow(String type, int requestNumber, double eventTime) {
        this.type = type;
        this.requestNumber = requestNumber;
        this.eventTime = eventTime;
    }

    public String getType() {
        return type;
    }

    public int getRequestNumber() {
        return requestNumber;
    }

    public double getEventTime() {
        return eventTime;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRequestNumber(int requestNumber) {
        this.requestNumber = requestNumber;
    }

    public void setEventTime(double eventTime) {
        this.eventTime = eventTime;
    }
}
