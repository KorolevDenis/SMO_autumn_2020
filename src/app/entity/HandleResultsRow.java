package app.entity;

public class HandleResultsRow {
    private int handleNumber;
    private double coefficient;

    public HandleResultsRow(int handleNumber, double coefficient) {
        this.handleNumber = handleNumber;
        this.coefficient = coefficient;
    }

    public int getHandleNumber() {
        return handleNumber;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setHandleNumber(int handleNumber) {
        this.handleNumber = handleNumber;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }
}
