package lesson1;

import java.util.Random;

public class MyApp {

    public static void main(String[] args) {

        Random random = new Random();

        final int MAX_LENGTH_RESTRICTION = 1000;
        final int MAX_HEIGHT_RESTRICTION = 100;

        final int MAX_CHARACTER_COUNT = 4;
        final int MAX_OBSTACLE_COUNT = 3;

        int humanCount = random.nextInt(MAX_CHARACTER_COUNT - 1) + 1;
        int robotCount = random.nextInt(MAX_CHARACTER_COUNT - 1) + 1;
        int catCount = random.nextInt(MAX_CHARACTER_COUNT - 1) + 1;
        int totalCount = humanCount + robotCount + catCount;

        System.out.println("--------------------------------------");
        System.out.println("Characters:");
        System.out.println("--------------------------------------");

        Capable[] capables = new Capable[totalCount];
        int initIndex;

        initIndex = 0;
        for (int i = initIndex; i < humanCount + initIndex; i++) {
            capables[i] = new Human("Human " + (i + 1 - initIndex), random.nextInt(MAX_LENGTH_RESTRICTION + 1 / 2), random.nextInt(MAX_HEIGHT_RESTRICTION + 1 / 2));
            System.out.println(capables[i]);
        }

        initIndex += humanCount;
        for (int i = initIndex; i < robotCount + initIndex; i++) {
            capables[i] = new Robot("Robot " + (i + 1 - initIndex), random.nextInt(MAX_LENGTH_RESTRICTION + 1 / 2), random.nextInt(MAX_HEIGHT_RESTRICTION + 1 / 2));
            System.out.println(capables[i]);
        }

        initIndex += robotCount;
        for (int i = initIndex; i < catCount + initIndex; i++) {
            capables[i] = new Cat("Cat " + (i + 1 - initIndex), random.nextInt(MAX_LENGTH_RESTRICTION + 1 / 2), random.nextInt(MAX_HEIGHT_RESTRICTION + 1 / 2));
            System.out.println(capables[i]);
        }

        int treadmillCount = random.nextInt(MAX_OBSTACLE_COUNT - 1) + 1;
        int wallCount = random.nextInt(MAX_OBSTACLE_COUNT - 1) + 1;

        totalCount = treadmillCount + wallCount;

        System.out.println("--------------------------------------");
        System.out.println("Obstacles:");
        System.out.println("--------------------------------------");

        Obstacle[] obstacles = new Obstacle[totalCount];

        initIndex = 0;
        for (int i = initIndex; i < treadmillCount + initIndex; i++) {
            obstacles[i] = new Treadmill(i + 1 - initIndex, random.nextInt(MAX_LENGTH_RESTRICTION + 1));
            System.out.println(obstacles[i]);
        }

        initIndex += treadmillCount;
        for (int i = initIndex; i < wallCount + initIndex; i++) {
            obstacles[i] = new Wall(i + 1 - initIndex, random.nextInt(MAX_HEIGHT_RESTRICTION + 1));
            System.out.println(obstacles[i]);
        }

        System.out.println("--------------------------------------");
        System.out.println("Result:");
        System.out.println("--------------------------------------");

        boolean finished;
        for (Capable capable : capables) {

            finished = true;
            for (Obstacle obstacle : obstacles) {

                if ((obstacle instanceof Wall && !capable.jump((Wall) obstacle)) ||
                        (obstacle instanceof Treadmill && !capable.run((Treadmill) obstacle))) {
                    finished = false;
                    break;
                }

            }
            if (finished) {

                String name = "";
                if (capable instanceof Cat) {
                    name = ((Cat) capable).getName();
                } else if (capable instanceof Human) {
                    name = ((Human) capable).getName();
                } else if (capable instanceof Robot) {
                    name = ((Robot) capable).getName();
                }
                System.out.printf("SUCCESS: character '%s' has finished", name);
                System.out.println();
            }

        }

    }

}
