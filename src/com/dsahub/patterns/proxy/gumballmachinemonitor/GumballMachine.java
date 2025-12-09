package com.dsahub.patterns.proxy.gumballmachinemonitor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GumballMachine extends UnicastRemoteObject implements GumballMachineRemote {
    private String location;
    private int count;
    private String state;

    public GumballMachine(String location, int count) throws java.rmi.RemoteException {
        this.location = location;
        this.count = count;
        this.state = (count > 0) ? "NoQuarterState" : "SoldOutState";
    }

    public void insertQuarter() throws java.rmi.RemoteException {
        if (state.equals("NoQuarterState")) {
            state = "HasQuarterState";
            System.out.println(location + ": Quarter inserted.");
        } else {
            System.out.println(location + ": Cannot insert quarter now.");
        }
    }

    public void turnCrank() {
        if (state.equals("HasQuarterState")) {
            System.out.println(location + ": Crank turned...");
            releaseBall();
            if (count > 0) {
                state = "NoQuarter";
            } else {
                System.out.println(location + ": Oops, out of gumballs!");
                state = "SoldOut";
            }
        } else {
            System.out.println(location + ": Turn crank failed (state=" + state + ")");
        }
    }

    private void releaseBall() {
        if (count > 0) {
            System.out.println(location + ": A gumball comes rolling out...");
            count--;
        }
    }

    // --- Remote interface methods ---
    @Override
    public int getCount() throws RemoteException {
        return count;
    }

    @Override
    public String getLocation() throws RemoteException {
        return location;
    }

    @Override
    public String getState() throws RemoteException {
        return state;
    }

    @Override
    public String toString() {
        return "GumballMachine{" +
                "location='" + location + '\'' +
                ", count=" + count +
                ", state='" + state + '\'' +
                '}';
    }
}
