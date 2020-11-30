package app.SMO;

public class Event implements Comparable<Event> {
    private double endTime = 0;
    private double beginTime = 0;
    private int number = 0;
    private Type type;

    public Event( double beginTime, double endTime, int number, Type type) {
        this.endTime = endTime;
        this.beginTime = beginTime;
        this.number = number;
        this.type = type;
    }

    @Override
    public int compareTo(Event o) {
        return Double.compare(this.endTime, o.endTime);
    }

    public double getEndTime() {
        return endTime;
    }

    public int getNumber() {
        return number;
    }

    public Type getType() {
        return type;
    }

    public double getBeginTime() {
        return beginTime;
    }
}
