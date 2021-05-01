package lesson1;

public class Treadmill extends Obstacle{

    private int length;

    public Treadmill(int number, int length) {
        this.number = number;
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "Treadmill{" +
                "number=" + number +
                ", length=" + length +
                '}';
    }
}
