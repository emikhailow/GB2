package Homework2;

import java.util.Arrays;
import java.util.Random;

public class App {

    //необходимый размер массива
    final static int NEEDED_ARRAY_SIZE = 4;
    //максимальное значение числа в ячейке массива
    final static int MAX_INT = 100;
    //максимальная длина строки для ячейки массива
    final static int MAX_STRING_LENGTH = 2;


    public static void main(String[] args) {

        Random random = new Random();

        //размер создаваемого массива
        int arraySize = getRandomIntBetween(1, NEEDED_ARRAY_SIZE + 1);
        //выбор между генерацией случайных строки или строк, содержащих только цифры
        //true - в массиве будут строки, состоящие только из цифр
        //false - в массиве будут случайные строки
        boolean digitsOnly = random.nextBoolean();

        String[][] stringArray = new String[arraySize][arraySize];

        for(int i = 0; i < arraySize; i++){
            for(int j = 0; j < stringArray[i].length; j++){

                random.nextBoolean();
                if(digitsOnly){
                    stringArray[i][j] = String.valueOf(random.nextInt(MAX_INT + 1));
                }else
                {
                    boolean randomBoolean = random.nextBoolean();
                    stringArray[i][j] = randomBoolean ? generateRandomString(MAX_STRING_LENGTH) : String.valueOf(random.nextInt(MAX_INT + 1));
                }

            }
        }

        for(int i = 0; i < arraySize; i++){
            System.out.println(Arrays.toString(stringArray[i]));
        }

        int sum = 0;

        try {
            sum = getSum(stringArray);
            System.out.printf("Sum of array is %d", sum);
        } catch (MyArraySizeException ex) {
            System.out.printf("ERROR: Array size (%s) is not equal to %s", ex.getArraySize(), NEEDED_ARRAY_SIZE);
        } catch(MyArrayDataException ex){
            System.out.printf("ERROR: Couldn't read number in cell [%d : %d]", ex.getI() + 1, ex.getJ() + 1);
        }

    }

    /**
     * Генерирует случайную строку длины length
     * @param length
     * @return
     */
    public static String generateRandomString(int length){

        Random random = new Random();

        String string = "";
        for(int i = 0; i < length; i++){

            int asciiCode = getRandomIntBetween(32, 127);
            char c = (char)asciiCode;
            string = string + c;

        }

        return string;

    }

    /**
     * Складывает значения, преобразованные в число, в ячейках массива
     * @param stringArray
     * @return
     * @throws MyArraySizeException
     * @throws MyArrayDataException
     */
    public static int getSum(String[][] stringArray) throws MyArraySizeException, MyArrayDataException {

        if(stringArray.length != NEEDED_ARRAY_SIZE || stringArray[0].length != NEEDED_ARRAY_SIZE){
            throw new MyArraySizeException(stringArray.length);
        }

        int sum = 0;


        for(int i = 0; i < stringArray.length; i++){
            for(int j = 0; j < stringArray[i].length; j++){

               try{
                   sum += Integer.valueOf(stringArray[i][j]);
               }
               catch(NumberFormatException nfe){
                   throw new MyArrayDataException(i, j);
               }

            }
        }

        return sum;

    }

    /**
     * Получает случайное целое число в диапазоне между двумя целыми
     * @param min
     * @param max
     * @return
     */
    private static int getRandomIntBetween(int min, int max){

        Random random = new Random();
        return random.nextInt(max - min) + min;

    }

}
