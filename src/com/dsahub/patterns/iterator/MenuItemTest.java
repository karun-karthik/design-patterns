package com.dsahub.patterns.iterator;

import java.util.ArrayList;

class MenuItem {
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
}

class PancakeHouseMenu {

    ArrayList<MenuItem> menuItems;

    public PancakeHouseMenu() {
        menuItems = new ArrayList<>();

        addItem("K&B Pancake Breakfast",
                "Pancakes with scrambled eggs and toast",
                true, 2.99);

        addItem("Regular Pancake Breakfast",
                "Pancakes with fried eggs and sausage",
                false, 2.99);

        addItem("Blueberry Pancakes",
                "Pancakes made with fresh blueberries",
                true, 3.49);
    }

    public void addItem(String name, String description,
                        boolean vegetarian, double price) {

        MenuItem menuItem =
                new MenuItem(name, description, vegetarian, price);
        menuItems.add(menuItem);
    }

    public MyIterator createIterator() {
        return new PancakeHouseMenuIterator(menuItems);
    }
}

class PancakeHouseMenuIterator implements MyIterator {
    ArrayList<MenuItem> items;
    int position = 0;

    public PancakeHouseMenuIterator(ArrayList<MenuItem> items) {
        this.items = items;
    }

    @Override
    public boolean hasNext() {
        return position < items.size();
    }

    public MenuItem next() {
        return items.get(position++);
    }
}

interface MyIterator {
    boolean hasNext();
    MenuItem next();
}

class DinerMenu {

    static final int MAX_ITEMS = 6;
    int numberOfItems = 0;
    MenuItem[] menuItems;

    public DinerMenu() {
        menuItems = new MenuItem[MAX_ITEMS];

        addItem("Vegetarian BLT",
                "Bacon with lettuce & tomato on whole wheat",
                true, 2.99);

        addItem("BLT",
                "Bacon with lettuce & tomato on whole wheat",
                false, 2.99);

        addItem("Soup of the Day",
                "Soup with a side of potato salad",
                false, 3.29);
    }

    public void addItem(String name, String description,
                        boolean vegetarian, double price) {

        MenuItem menuItem =
                new MenuItem(name, description, vegetarian, price);

        menuItems[numberOfItems] = menuItem;
        numberOfItems++;
    }

    public MyIterator createIterator() {
        return new DinerMenuIterator(menuItems);
    }
}

class DinerMenuIterator implements MyIterator {

    MenuItem[] items;
    int position = 0;

    public DinerMenuIterator(MenuItem[] items) {
        this.items = items;
    }

    public boolean hasNext() {
        return position < items.length &&
                items[position] != null;
    }

    public MenuItem next() {
        return items[position++];
    }
}

public class MenuItemTest {

    public static void main(String[] args) {
        PancakeHouseMenu pancakeHouseMenu = new PancakeHouseMenu();
        MyIterator iterator = pancakeHouseMenu.createIterator();
        print(iterator);
        DinerMenu dinerMenu = new DinerMenu();
        MyIterator customIterator = dinerMenu.createIterator();
        print(customIterator);
    }

    private static void print(MyIterator itemIterator) {
        while (itemIterator.hasNext()) {
            MenuItem item = itemIterator.next();
            System.out.println(
                    item.getName() + " -- " +
                            item.getPrice() + " -- " +
                            item.getDescription()
            );
        }
    }
}
