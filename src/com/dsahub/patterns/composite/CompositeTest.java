package com.dsahub.patterns.composite;

import java.util.ArrayList;

// Base Component
abstract class MenuComponent {

    // Default composite operations
    public void add(MenuComponent component) {
        throw new UnsupportedOperationException();
    }

    public void remove(MenuComponent component) {
        throw new UnsupportedOperationException();
    }

    public MenuComponent getChild(int i) {
        throw new UnsupportedOperationException();
    }

    // Leaf operations
    public String getName() {
        throw new UnsupportedOperationException();
    }

    public String getDescription() {
        throw new UnsupportedOperationException();
    }

    public double getPrice() {
        throw new UnsupportedOperationException();
    }

    public boolean isVegetarian() {
        throw new UnsupportedOperationException();
    }

    public void print() {
        throw new UnsupportedOperationException();
    }
}

// Leaf — MenuItem
class MenuItem extends MenuComponent {
    String name;
    String description;
    boolean vegetarian;
    double price;

    public MenuItem(String name, String description,
                    boolean vegetarian, double price) {
        this.name = name;
        this.description = description;
        this.vegetarian = vegetarian;
        this.price = price;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public boolean isVegetarian() { return vegetarian; }

    @Override
    public void print() {
        System.out.print("  " + name);
        if (vegetarian) {
            System.out.print(" (v)");
        }
        System.out.println(" -- " + price);
        System.out.println("     " + description);
    }
}

// Composite — Menu
class Menu extends MenuComponent {

    ArrayList<MenuComponent> menuComponents = new ArrayList<>();
    String name;
    String description;

    public Menu(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void add(MenuComponent component) {
        menuComponents.add(component);
    }

    public void remove(MenuComponent component) {
        menuComponents.remove(component);
    }

    public MenuComponent getChild(int i) {
        return menuComponents.get(i);
    }

    public String getName() { return name; }
    public String getDescription() { return description; }

    @Override
    public void print() {
        System.out.println("\n" + name + " -- " + description);
        System.out.println("-------------------------");

        for (MenuComponent component : menuComponents) {
            component.print();   // ✅ Delegation to leaf or sub-menu
        }
    }
}


// Client — Waitress
class Waitress {
    MenuComponent allMenus;

    public Waitress(MenuComponent allMenus) {
        this.allMenus = allMenus;
    }

    public void printMenu() {
        allMenus.print();
    }
}


// DRIVER CLASS
public class CompositeTest {

    public static void main(String[] args) {

        // MENUS
        MenuComponent pancakeHouseMenu =
                new Menu("PANCAKE HOUSE MENU", "Breakfast");
        MenuComponent dinerMenu =
                new Menu("DINER MENU", "Lunch");
        MenuComponent cafeMenu =
                new Menu("CAFE MENU", "Dinner");
        MenuComponent dessertMenu =
                new Menu("DESSERT MENU", "Dessert!");

        MenuComponent allMenus =
                new Menu("ALL MENUS", "All menus combined");

        // BUILD COMPOSITION TREE
        allMenus.add(pancakeHouseMenu);
        allMenus.add(dinerMenu);
        allMenus.add(cafeMenu);

        // PANCAKE ITEMS
        pancakeHouseMenu.add(new MenuItem(
                "Pancake Breakfast",
                "Pancakes with scrambled eggs",
                true, 2.99));

        pancakeHouseMenu.add(new MenuItem(
                "Waffle",
                "Crispy waffle with honey",
                true, 3.49));

        // DINER ITEMS
        dinerMenu.add(new MenuItem(
                "BLT",
                "Bacon, Lettuce & Tomato Sandwich",
                false, 2.99));

        dinerMenu.add(new MenuItem(
                "Vegetarian BLT",
                "Veggies on whole wheat",
                true, 2.99));

        // DESSERT SUB-MENU INSIDE DINER
        dinerMenu.add(dessertMenu);

        dessertMenu.add(new MenuItem(
                "Apple Pie",
                "Apple pie with ice cream",
                true, 1.59));

        dessertMenu.add(new MenuItem(
                "Chocolate Brownie",
                "Dark chocolate brownie",
                true, 1.89));

        // CAFE ITEMS
        cafeMenu.add(new MenuItem(
                "Veg Burger",
                "Veg burger with fries",
                true, 3.99));

        cafeMenu.add(new MenuItem(
                "Coffee",
                "Fresh hot coffee",
                true, 1.99));

        // CLIENT
        Waitress waitress = new Waitress(allMenus);
        waitress.printMenu();   // prints everything
    }
}
