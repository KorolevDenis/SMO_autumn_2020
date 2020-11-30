package app.SMO;

public class Source {
    private int number;
    private int sourceCount = 0;
    private int deniedCountSource = 0;
    private double lastTime = 0;
    private double beta;
    private double alpha;
    private double processTime = 0;
    private double bufferTime = 0;

    public Source(int number, double beta, double alpha) {
        this.number = number;
        this.beta = beta;
        this.alpha = alpha;
    }

    Request generateNewRequest() {
        sourceCount++;
        Request request = new Request(
                Integer.parseInt((number + 1) + Integer.toString(sourceCount)),
                lastTime, generateNextEndTime(lastTime));
        lastTime = request.getEndTime();
        return request;
    }

    private double generateNextEndTime(double beginTime) {
        return beginTime + Math.random() * (beta - alpha) + alpha;
    }

    public void incrementDeniedSourceCount() {
        deniedCountSource++;
    }

    public double getDenyProbability() {
        return (double) deniedCountSource / sourceCount;
    }

    public void addProcessTime(double processTime) {
        this.processTime += processTime;
    }

    public void addBufferTime(double bufferTime) {
        this.bufferTime += bufferTime;
    }

    public double getAverageProcessTime() {
        return processTime/(sourceCount);
    }

    public double getAverageBufferTime() {
        return bufferTime/(sourceCount + deniedCountSource);
    }

    public double getAverageSystemTime() {
        return (bufferTime/(sourceCount + deniedCountSource)) + processTime/(sourceCount);
    }

    public int getSourceCount() {
        return sourceCount;
    }


}
