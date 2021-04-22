package lesson1;

public class Treadmill implements Overcomeable{

    private int number;
    private int length;

    public Treadmill(int number, int length) {
        this.number = number;
        this.length = length;
    }

    public boolean overcome(Capable capable) {

        if(capable.getLengthRestriction() < length){

            System.out.printf("FAIL: character '%s' could not overcome Treadmill %s", capable.getName(), this.number);
            System.out.println();
            return false;

        }
        capable.run();
        return true;

    }

    @Override
    public String toString() {
        return "Treadmill{" +
                "number=" + number +
                ", length=" + length +
                '}';
    }
}
