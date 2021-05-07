package Homework3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class App {

    public static void main(String[] args) {

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Moscow");
        arrayList.add("Paris");
        arrayList.add("Moscow");
        arrayList.add("London");
        arrayList.add("Moscow");
        arrayList.add("Paris");
        arrayList.add("Moscow");
        arrayList.add("London");
        arrayList.add("Moscow");
        arrayList.add("Paris");
        arrayList.add("Moscow");
        arrayList.add("London");
        arrayList.add("Moscow");
        arrayList.add("Paris");
        arrayList.add("Moscow");
        arrayList.add("London");
        arrayList.add("Moscow");
        arrayList.add("Paris");
        arrayList.add("Moscow");
        arrayList.add("London");
        arrayList.add("Rome");
        arrayList.add("Athens");
        arrayList.add("Glasgow");
        arrayList.add("Oslo");

        HashMap<String, Integer> hashMap = new HashMap<>();

        for (String s : arrayList) {
            hashMap.put(s, hashMap.get(s) == null ? 1 : hashMap.get(s) + 1);
        }

        System.out.println(hashMap);

        System.out.println("==========");

        Phonebook phonebook = new Phonebook();
        phonebook.add("Ivanov", "89111111111");
        phonebook.add("Sidorov", "89222222222");
        phonebook.add("Ivanov", "89333333333");
        phonebook.add("Petrov", "89444444444");

        System.out.println(phonebook.get("Ivanov"));
        System.out.println(phonebook.get("Petrov"));

    }

}
