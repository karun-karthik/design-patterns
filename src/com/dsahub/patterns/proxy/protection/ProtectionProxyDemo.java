package com.dsahub.patterns.proxy.protection;

import java.lang.reflect.*;

// INTERFACE
interface PersonBean {
    String getName();
    String getGender();
    String getInterests();
    int getRating();

    void setName(String name);
    void setGender(String gender);
    void setInterests(String interests);
    void setRating(int rating);
}

// REAL SUBJECT
class PersonBeanImpl implements PersonBean {

    private String name;
    private String gender;
    private String interests;
    private int rating;
    private int ratingCount = 0;

    public String getName() { return name; }
    public String getGender() { return gender; }
    public String getInterests() { return interests; }

    public int getRating() {
        if (ratingCount == 0) return 0;
        return rating / ratingCount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public void setRating(int rating) {
        this.rating += rating;
        ratingCount++;
    }
}

// OWNER INVOCATION HANDLER (PROXY LOGIC)
class OwnerInvocationHandler implements InvocationHandler {

    private PersonBean person;

    public OwnerInvocationHandler(PersonBean person) {
        this.person = person;
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws IllegalAccessException {

        try {
            if (method.getName().startsWith("get")) {
                return method.invoke(person, args);

            } else if (method.getName().equals("setRating")) {
                throw new IllegalAccessException("Owners cannot rate themselves!");
            } else if (method.getName().startsWith("set")) {
                return method.invoke(person, args);
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}


// NON-OWNER INVOCATION HANDLER (PROXY LOGIC)
class NonOwnerInvocationHandler implements InvocationHandler {

    private PersonBean person;

    public NonOwnerInvocationHandler(PersonBean person) {
        this.person = person;
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws IllegalAccessException {

        try {
            if (method.getName().startsWith("get")) {
                return method.invoke(person, args);

            } else if (method.getName().equals("setRating")) {
                return method.invoke(person, args);

            } else if (method.getName().startsWith("set")) {
                throw new IllegalAccessException("You cannot change someone else's profile!");
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}


// 5. PROXY FACTORY
class ProxyFactory {

    public static PersonBean getOwnerProxy(PersonBean person) {
        return (PersonBean) Proxy.newProxyInstance(
                person.getClass().getClassLoader(),
                person.getClass().getInterfaces(),
                new OwnerInvocationHandler(person)
        );
    }

    public static PersonBean getNonOwnerProxy(PersonBean person) {
        return (PersonBean) Proxy.newProxyInstance(
                person.getClass().getClassLoader(),
                person.getClass().getInterfaces(),
                new NonOwnerInvocationHandler(person)
        );
    }
}

// ============================================
// 6. DRIVER / TEST
// ============================================
public class ProtectionProxyDemo {

    public static void main(String[] args) {

        // ✅ REAL OBJECT
        PersonBean karun = new PersonBeanImpl();
        karun.setName("Karun");
        karun.setGender("Male");
        karun.setInterests("Gym, AI, Startups");

        System.out.println("===== OWNER PROXY TEST =====");
        PersonBean ownerProxy = ProxyFactory.getOwnerProxy(karun);

        System.out.println("Name: " + ownerProxy.getName());
        ownerProxy.setInterests("Gym, AI, YouTube");
        System.out.println("Interests updated ✅");

        try {
            ownerProxy.setRating(10); // ❌ BLOCKED
        } catch (Exception e) {
            System.out.println("Owner cannot rate himself ❌");
        }

        System.out.println("\n===== NON-OWNER PROXY TEST =====");
        PersonBean nonOwnerProxy = ProxyFactory.getNonOwnerProxy(karun);

        System.out.println("Name: " + nonOwnerProxy.getName());

        try {
            nonOwnerProxy.setInterests("Hacking"); // ❌ BLOCKED
        } catch (Exception e) {
            System.out.println("Non-owner cannot edit profile ❌");
        }

        nonOwnerProxy.setRating(8); // ✅ ALLOWED
        nonOwnerProxy.setRating(10); // ✅ ALLOWED

        System.out.println("New rating after non-owner votes ✅: " + nonOwnerProxy.getRating());
    }
}