package com.dsahub.patterns.proxy.gumballmachinemonitor;

import java.rmi.Naming;

public class GumballMonitorTestDrive {
    public static void main(String[] args) {
        try {
            // Lookup remote machines (in real HFDP these URLs come from args[])
            String[] locations = {
                    "rmi://localhost/chennai_gumball",
                    "rmi://localhost/bangalore_gumball"
            };

            for (String location : locations) {
                GumballMachineRemote machine =
                        (GumballMachineRemote) Naming.lookup(location);

                GumballMonitor monitor = new GumballMonitor(machine);
                monitor.report();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
