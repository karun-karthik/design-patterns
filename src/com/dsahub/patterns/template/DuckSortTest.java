package com.dsahub.patterns.template;

import java.util.Arrays;

class Duck implements Comparable<Duck> {
    String name;
    int weight;

    public Duck(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public int compareTo(Duck duck) {
        return this.weight - duck.weight;
    }

    public String toString() {
        return name + " weighs " + weight;
    }
}

public class DuckSortTest {
    public static void main(String[] args) {
        Duck[] ducks = {
                new Duck("Daffy", 8),
                new Duck("Louie", 2),
                new Duck("Huey", 3)
        };
        Arrays.sort(ducks);
        System.out.println(Arrays.toString(ducks));
    }
}
