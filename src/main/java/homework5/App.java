package homework5;

import java.util.Arrays;

public class App{

    private static final int size = 10000000;
    private static final int h = size / 2;

    public static void main(String[] args) {

        firstMethod();
        try {
            secondMethod();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void firstMethod(){

        float[] arr = new float[size];
        Arrays.fill(arr, 1);

        long a = System.currentTimeMillis();

        applyFormulae(arr, 0);

        System.out.printf("No streams: %d", System.currentTimeMillis() - a);
        System.out.println();

    }

    public static void secondMethod() throws InterruptedException {

        float[] arr = new float[size];
        float[] arr1 = new float[h];
        float[] arr2 = new float[h];

        Arrays.fill(arr, 1);

        long a = System.currentTimeMillis();

        System.arraycopy(arr, 0, arr1, 0, h);
        System.arraycopy(arr, h, arr2, 0, h);

        Thread thread1 = new Thread(() -> {
            applyFormulae(arr1, 0);
        });

        Thread thread2 = new Thread(() -> {
            applyFormulae(arr2, h);
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread1.join();

        System.arraycopy(arr1, 0, arr, 0, h);
        System.arraycopy(arr2, 0, arr, h, h);

        System.out.printf("Using streams: %d", System.currentTimeMillis() - a);
        System.out.println();

    }

    public static void applyFormulae(float[] array, int shift){

        for (int i = 0; i < array.length; i++) {
            array[i] = (float) (array[i] * Math.sin(0.2f + (i + shift) / 5) * Math.cos(0.2f + (i + shift) / 5) * Math.cos(0.4f + (i + shift) / 2));
        }

    }

}

