package com.dsahub.patterns.proxy.remote;

public class GumballMonitor {
    private GumballMachineRemote gumballMachine;

    public GumballMonitor(GumballMachineRemote gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    public void report() {
        try {
            System.out.println("Location: " + gumballMachine.getLocation());
            System.out.println("Inventory: " + gumballMachine.getCount() + " gumballs");
            System.out.println("State: " + gumballMachine.getState());
            System.out.println("---------------------------");
        } catch (Exception e) {
            System.out.println("Could not retrieve gumball machine data.");
            e.printStackTrace();
        }
    }
}
