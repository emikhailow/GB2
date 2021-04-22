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

    public void setName(String name) {
        this.name = name;
    }

    public int getLengthRestriction() {
        return lengthRestriction;
    }

    public void setLengthRestriction(int lengthRestriction) {
        this.lengthRestriction = lengthRestriction;
    }

    public int getHeightRestriction() {
        return heightRestriction;
    }

    public void setHeightRestriction(int heightRestriction) {
        this.heightRestriction = heightRestriction;
    }

    public void run() {
        System.out.printf("--Cat '%s' runs", this.name);
        System.out.println();
    }

    public void jump() {
        System.out.printf("--Cat '%s' jumps", this.name);
        System.out.println();
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
