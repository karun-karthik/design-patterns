package com.dsahub.patterns.template;

import java.io.BufferedReader;
import java.io.InputStreamReader;

abstract class CaffeineBeverageWithHook {
    final void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        if(customerWantsCondiments()) {
            addCondiments();
        }
    }

    abstract void brew();
    abstract void addCondiments();

    void boilWater() {
        System.out.println("Boiling water");
    }

    void pourInCup() {
        System.out.println("Pouring into cup");
    }

    boolean customerWantsCondiments() {
        return true;
    }
}

class CoffeeWithHook extends CaffeineBeverageWithHook {

    void brew() {
        System.out.println("Dripping coffee through filter");
    }

    void addCondiments() {
        System.out.println("Adding sugar and milk");
    }

    public boolean customerWantsCondiments() {
        String answer = getUserInput();
        return answer.toLowerCase().startsWith("y");
    }

    private String getUserInput() {
        System.out.print("Would you like milk and sugar? (y/n): ");
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            return in.readLine();
        } catch (Exception e) {
            return "n";
        }
    }
}

public class TemplateWithHook {
    public static void main(String[] args) {
        CoffeeWithHook coffee = new CoffeeWithHook();
        System.out.println("Coffee Preparation:");
        coffee.prepareRecipe();
    }
}
