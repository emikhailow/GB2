package Homework2;

public class MyArrayDataException extends Exception{

    public static final String TEXT = "ERROR: Couldn't read number in cell [%d : %d]";

    private int i;
    private int j;

    public MyArrayDataException(int i, int j) {

        this.i = i;
        this.j = j;

    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
