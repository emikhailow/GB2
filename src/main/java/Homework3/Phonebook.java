package Homework3;

import java.util.HashMap;
import java.util.HashSet;

public class Phonebook {

    private HashMap<String, HashSet<String>> hashMap;

    public Phonebook() {

        hashMap = new HashMap<>();

    }

    public void add(String name, String phoneNumber) {

        HashSet<String> hashSet = hashMap.get(name) == null ? new HashSet<>() : hashMap.get(name);
        hashSet.add(phoneNumber);
        hashMap.put(name, hashSet);

    }

    public HashSet<String> get(String name){

        return hashMap.get(name);

    }

    @Override
    public String toString() {
        return "Phonebook{" +
                "hashMap=" + hashMap +
                '}';
    }
}
