package lesson1;

public class Wall implements Overcomeable {

    private int number;
    private int height;

    public Wall(int number, int height) {
        this.number = number;
        this.height = height;
    }

    public boolean overcome(Capable capable) {

        if(capable.getHeightRestriction() < height){

            System.out.printf("FAIL: character '%s' could not overcome Wall %s", capable.getName(), this.number);
            System.out.println();
            return false;

        }
        capable.jump();
        return true;

    }

    @Override
    public String toString() {
        return "Wall{" +
                "number=" + number +
                ", height=" + height +
                '}';
    }
}
