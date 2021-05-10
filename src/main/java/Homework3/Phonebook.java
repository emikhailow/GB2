package Homework3;

import java.util.*;

public class Phonebook {

    private Map<String, HashSet<String>> hashMap;

    public Phonebook() {

        hashMap = new HashMap<>();

    }

    public void add(String name, String phoneNumber) {

        HashSet<String> hashSet = hashMap.getOrDefault(name, new HashSet<>());
        hashSet.add(phoneNumber);
        hashMap.put(name, hashSet);

    }

    public Set<String> get(String name){

        return hashMap.getOrDefault(name, new HashSet<>());

    }

    @Override
    public String toString() {
        return "Phonebook{" +
                "hashMap=" + hashMap +
                '}';
    }
}
