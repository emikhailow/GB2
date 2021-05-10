package Homework3;

import java.util.*;

public class App {

    private static final int ARRAY_LENGTH = 20;

    public static void main(String[] args) {

        List<String> uniqueStrings = new ArrayList<>();
        uniqueStrings.add("Moscow");
        uniqueStrings.add("Paris");
        uniqueStrings.add("London");
        uniqueStrings.add("Rome");
        uniqueStrings.add("Athens");

        Random random = new Random();

        ArrayList<String> arrayList = new ArrayList<>();
        for(int i = 0; i < ARRAY_LENGTH; i++){
            arrayList.add(uniqueStrings.get(random.nextInt(uniqueStrings.size())));
        }
        System.out.println("Initial array: " + arrayList);

        Map<String, Integer> hashMap = new HashMap<>();

        for (String s : arrayList) {
            hashMap.put(s, hashMap.getOrDefault(s, 0) + 1);
        }

        System.out.println("Occurence: " + hashMap);

        System.out.println("==========");

        Phonebook phonebook = new Phonebook();
        phonebook.add("Ivanov", "89111111111");
        phonebook.add("Sidorov", "89222222222");
        phonebook.add("Ivanov", "89333333333");
        phonebook.add("Petrov", "89444444444");

        System.out.println(phonebook.get("Ivanov"));
        System.out.println(phonebook.get("Petrov"));
        System.out.println(phonebook.get("Mikhailov"));

    }

}
