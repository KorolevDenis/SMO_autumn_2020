package app.SMO;
import java.util.Arrays;

public class Buffer {
    Request requestArray[];
    Request denyRequest = null;
    int requestIndex = 0;

    public Buffer(int size) {
        this.requestArray = new Request[size];
        Arrays.fill(requestArray, null);
    }

    Request putRequest(Request request) {
        int index = 0;

        while (index < requestArray.length && requestArray[index] != null) {
            index++;
        }

        if (index < requestArray.length) {
            requestArray[index] = request;
            denyRequest = null;
            return null;
        } else {
            double max = 0;
            int maxIndex = 0;
            for (int i = 0; i < requestArray.length; i++) {
                if (requestArray[i].getEndTime() > max) {
                    max = requestArray[i].getEndTime();
                    maxIndex = i;
                }
            }

            denyRequest = requestArray[maxIndex];
            requestArray[maxIndex] = request;
            return denyRequest;
        }
    }

    Request getRequest() {

        int count = 0;

        while (count < requestArray.length) {
            if (requestArray[requestIndex] != null)
            {
                Request request = requestArray[requestIndex];
                requestArray[requestIndex] = null;
                requestIndex++;
                if (requestIndex == requestArray.length) {
                    requestIndex = 0;
                }
                return request;
            }
            requestIndex++;

            if (requestIndex == requestArray.length) {
                requestIndex = 0;
            }

            count++;
        }

        return null;
    }

    boolean isEmpty() {
        for (Request request : requestArray) {
            if (request != null) {
                return false;
            }
        }
        return true;
    }

    public Request[] getRequestArray() {
        return requestArray;
    }

    public Request getDenyRequest() {
        return denyRequest;
    }
}
