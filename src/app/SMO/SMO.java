package app.SMO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SMO {
    private int sourceCount;
    private int handleCount;
    private int bufferSize;
    private int requestCountGlobal = 100;
    private double lambda;
    private double beta;
    private double alpha;

    private int handleIndex = 0;
    private List<Event> eventList = new ArrayList<Event>();
    private Source[] sources;
    private Handle[] handles;
    private Buffer buffer;
    private Event event;
    private Request denyRequest;

    public SMO(int sourceCount, int handleCount, int bufferSize, double lambda, double beta, double alpha) {
        this.sourceCount = sourceCount;
        this.handleCount = handleCount;
        this.bufferSize = bufferSize;
        this.lambda = lambda;
        this.beta = beta;
        this.alpha = alpha;

        sources = new Source[sourceCount];
        handles = new Handle[handleCount];
        buffer = new Buffer(bufferSize);

        for (int i = 0; i < sourceCount; i++) {
            sources[i] = new Source(i, beta, alpha);
        }

        for (int i =0; i < handleCount; i++) {
            handles[i] = new Handle(i, lambda);
        }

        for (int i = 0; i < sourceCount; i++) {
            Request request = sources[i].generateNewRequest();
            eventList.add(new Event(request.getBeginTime(), request.getEndTime(), request.getNumber(), Type.Request));
        }
    }
    
    public void doStep() {
        boolean flagSetOnHandle = false;
        double endWorkTime = 0;
        event = Collections.min(eventList);
        eventList.remove(event);
        flagSetOnHandle = false;

        if (event.getType() == Type.Request) {
            if (buffer.isEmpty()) {
                if (setRequestOnHandle(event)) {
                    flagSetOnHandle = true;
                }
            }
            if (!flagSetOnHandle) {
                denyRequest = buffer.putRequest(new Request(event.getNumber(),
                        event.getBeginTime(), event.getEndTime()));

                if (denyRequest != null) {
                    sources[Integer.parseInt(Integer.
                            toString(event.getNumber()).substring(0, 1)) - 1].
                            addBufferTime(event.getEndTime() - denyRequest.getEndTime());
                    sources[Integer.parseInt(Integer.
                            toString(event.getNumber()).substring(0, 1)) - 1].incrementDeniedSourceCount();
                }
            }
            Request request = sources[Integer.parseInt(Integer.
                    toString(event.getNumber()).substring(0, 1)) - 1].generateNewRequest();
            eventList.add(new Event(request.getBeginTime(), request.getEndTime(), request.getNumber(), Type.Request));

        } else if (event.getType() == Type.HandleRelease) {
            if (!buffer.isEmpty()) {
                Request request = buffer.getRequest();
                sources[Integer.parseInt(Integer.
                        toString(request.getNumber()).substring(0, 1)) - 1].addBufferTime(event.getEndTime() - request.getEndTime());

                handles[event.getNumber()].setFree(false);
                handles[event.getNumber()].setRequestNumber(request.getNumber());
                double newEndTime = handles[event.getNumber()].getNextEndTime(event.getEndTime());
                sources[Integer.parseInt(Integer.
                        toString(request.getNumber()).substring(0, 1)) - 1].addProcessTime(newEndTime - event.getEndTime());
                eventList.add(new Event(event.getEndTime(), newEndTime,  handles[event.getNumber()].getNumber(), Type.HandleRelease));
            } else {
                handles[event.getNumber()].setFree(true);
                handles[event.getNumber()].setRequestNumber(0);
            }
        }
    }

    public void init(int requestCountArg) {
        boolean flagSetOnHandle = false;

        requestCountGlobal += requestCountArg;
        int requestCount = sourceCount;

        double endWorkTime = 0;
        while(!eventList.isEmpty()) {
            Event event = Collections.min(eventList);
            eventList.remove(event);
            flagSetOnHandle = false;

            if (event.getType() == Type.Request) {
                if (buffer.isEmpty()) {
                    if (setRequestOnHandle(event)) {
                        flagSetOnHandle = true;
                    }
                }
                if (!flagSetOnHandle) {
                    Request denyRequest = buffer.putRequest(new Request(event.getNumber(),
                            event.getBeginTime(), event.getEndTime()));

                    if (denyRequest != null) {
                        sources[Integer.parseInt(Integer.
                                toString(event.getNumber()).substring(0, 1)) - 1].
                                addBufferTime(event.getEndTime() - denyRequest.getEndTime());
                        sources[Integer.parseInt(Integer.
                                toString(event.getNumber()).substring(0, 1)) - 1].incrementDeniedSourceCount();
                    }
                }

                if (requestCount < requestCountGlobal) {
                    Request request = sources[Integer.parseInt(Integer.
                            toString(event.getNumber()).substring(0, 1)) - 1].generateNewRequest();
                    eventList.add(new Event(request.getBeginTime(), request.getEndTime(), request.getNumber(), Type.Request));
                    requestCount++;
                }
            } else if (event.getType() == Type.HandleRelease) {
                if (!buffer.isEmpty()) {
                    Request request = buffer.getRequest();
                    sources[Integer.parseInt(Integer.
                            toString(request.getNumber()).substring(0, 1)) - 1].addBufferTime(event.getEndTime() - request.getEndTime());

                    handles[event.getNumber()].setFree(false);
                    handles[event.getNumber()].setRequestNumber(request.getNumber());
                    double newEndTime = handles[event.getNumber()].getNextEndTime(event.getEndTime());
                    sources[Integer.parseInt(Integer.
                            toString(request.getNumber()).substring(0, 1)) - 1].addProcessTime(newEndTime - event.getEndTime());
                    eventList.add(new Event(event.getEndTime(), newEndTime,  handles[event.getNumber()].getNumber(), Type.HandleRelease));
                } else {
                    handles[event.getNumber()].setFree(true);
                    handles[event.getNumber()].setRequestNumber(0);
                }
            }
            endWorkTime = event.getEndTime();
        }

        for (int i =0; i < handleCount; i++) {
            handles[i].setAllTime(endWorkTime);
        }
    }

    public String getTypeMessage() {
        String messsage = "";
        if (event != null) {
            if (event.getType() == Type.Request) {
                messsage = "New request " + event.getNumber() + "\n" + "generation time: " + event.getEndTime();
            } else if (event.getType() == Type.HandleRelease) {
                messsage = "Handle release " + (event.getNumber() + 1) + "; ";

                if (handles[event.getNumber()].getPrevRequestNumber() != 0) {
                    messsage += "release " + handles[event.getNumber()].getPrevRequestNumber();
                }
                if (handles[event.getNumber()].getRequestNumber() != 0) {
                    messsage += " set " + handles[event.getNumber()].getRequestNumber();
                }
                messsage += "\nstartProcessTime: " + event.getEndTime();
            }
        }
        return messsage;
    }

    boolean setRequestOnHandle(Event event) {
        int count = 0;

        while (count < handles.length) {
            if (handles[handleIndex].isFree())
            {
                double newEndTime = handles[handleIndex].getNextEndTime(event.getEndTime());
                sources[Integer.parseInt(Integer.
                        toString(event.getNumber()).substring(0, 1)) - 1].addProcessTime(newEndTime - event.getEndTime());
                handles[handleIndex].setRequestNumber(event.getNumber());
                handles[handleIndex].setFree(false);
                eventList.add(new Event(event.getEndTime(), newEndTime,  handles[handleIndex].getNumber(), Type.HandleRelease));
                return true;
            }
            handleIndex++;

            if (handleIndex == handles.length) {
                handleIndex = 0;
            }

            count++;
        }
        return false;
    }

    public Source[] getSources() {
        return sources;
    }

    public Handle[] getHandles() {
        return handles;
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public Request getDenyRequest() {
        return denyRequest;
    }
}
