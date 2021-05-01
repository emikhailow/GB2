package lesson1;

public class Cat implements Capable {

    private String name;
    private int lengthRestriction;
    private int heightRestriction;

    public Cat(String name, int lengthRestriction, int heightRestriction) {
        this.name = name;
        this.lengthRestriction = lengthRestriction;
        this.heightRestriction = heightRestriction;
    }

    public String getName() {
        return name;
    }

    public int getLengthRestriction() {
        return lengthRestriction;
    }

    public int getHeightRestriction() {
        return heightRestriction;
    }

    public boolean run(Treadmill treadmill) {

        if(this.getLengthRestriction() < treadmill.getLength()){

            System.out.printf("FAIL: Cat '%s' couldn't pass treadmill %s", this.name, treadmill.getNumber());
            System.out.println();
            return false;

        }
        System.out.printf("--Cat '%s' passes treadmill %s", this.name, treadmill.getNumber());
        System.out.println();
        return true;
    }

    public boolean jump(Wall wall) {

        if(this.getHeightRestriction() < wall.getHeight()){

            System.out.printf("FAIL: Cat '%s' couldn't pass wall %s", this.name, wall.getNumber());
            System.out.println();
            return false;

        }
        System.out.printf("--Cat '%s' passes wall %s", this.name, wall.getNumber());
        System.out.println();
        return true;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", lengthRestriction=" + lengthRestriction +
                ", heightRestriction=" + heightRestriction +
                '}';
    }
}
