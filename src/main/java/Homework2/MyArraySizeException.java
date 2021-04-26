package Homework2;

public class MyArraySizeException extends Exception {

    private int arraySize;

    public MyArraySizeException(int arraySize) {
        this.arraySize = arraySize;
    }

    public int getArraySize() {
        return arraySize;
    }
}
