package app.entity;

public class SourceResultsRow {
    private int sourceNumber;
    private int sourceCount;
    private double denyProbability;
    private double systemTime;
    private double processTime;
    private double bufferTime;
    private double dispProcessTime;
    private double dispBufferTTime;

    public SourceResultsRow(int sourceNumber, int sourceCount, double denyProbality,
                            double systemTime, double processTime, double bufferTime,
                            double dispProcessTime, double dispBufferTTime) {
        this.sourceNumber = sourceNumber;
        this.sourceCount = sourceCount;
        this.denyProbability = denyProbality;
        this.systemTime = systemTime;
        this.processTime = processTime;
        this.bufferTime = bufferTime;
        this.dispProcessTime = dispProcessTime;
        this.dispBufferTTime = dispBufferTTime;
    }

    public int getSourceNumber() {
        return sourceNumber;
    }

    public int getSourceCount() {
        return sourceCount;
    }

    public double getDenyProbability() {
        return denyProbability;
    }

    public double getSystemTime() {
        return systemTime;
    }

    public double getProcessTime() {
        return processTime;
    }

    public double getBufferTime() {
        return bufferTime;
    }

    public double getDispProcessTime() {
        return dispProcessTime;
    }

    public double getDispBufferTTime() {
        return dispBufferTTime;
    }

    public void setSourceNumber(int sourceNumber) {
        this.sourceNumber = sourceNumber;
    }

    public void setSourceCount(int sourceCount) {
        this.sourceCount = sourceCount;
    }

    public void setDenyProbability(double denyProbability) {
        this.denyProbability = denyProbability;
    }

    public void setSystemTime(double systemTime) {
        this.systemTime = systemTime;
    }

    public void setProcessTime(double processTime) {
        this.processTime = processTime;
    }

    public void setBufferTime(double bufferTime) {
        this.bufferTime = bufferTime;
    }

    public void setDispProcessTime(double dispProcessTime) {
        this.dispProcessTime = dispProcessTime;
    }

    public void setDispBufferTTime(double dispBufferTTime) {
        this.dispBufferTTime = dispBufferTTime;
    }
}

