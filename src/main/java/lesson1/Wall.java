package lesson1;

public class Wall extends Obstacle{

    private int height;

    public Wall(int number, int height) {
        this.number = number;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Wall{" +
                "number=" + number +
                ", height=" + height +
                '}';
    }
}
