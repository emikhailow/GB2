package Homework2;

public class MyArraySizeException extends Exception {

    public static final String TEXT = "ERROR: Array size (%s) is not equal to %s";
    private int arraySize;

    public MyArraySizeException(int arraySize) {
        this.arraySize = arraySize;
    }

    public int getArraySize() {
        return arraySize;
    }
}
