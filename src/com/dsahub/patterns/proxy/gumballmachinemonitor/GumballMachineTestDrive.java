package com.dsahub.patterns.proxy.gumballmachinemonitor;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class GumballMachineTestDrive {
    public static void main(String[] args) {
        try {
            // Start RMI registry (on default port 1099)
            try {
                LocateRegistry.createRegistry(1099);
                System.out.println("RMI registry started on port 1099");
            } catch (Exception e) {
                System.out.println("RMI registry may already be running");
            }

            // Create and bind machines
            GumballMachine chennaiMachine =
                    new GumballMachine("Chennai", 5);
            GumballMachine bangaloreMachine =
                    new GumballMachine("Bangalore", 2);

            Naming.rebind("rmi://localhost/chennai_gumball", chennaiMachine);
            Naming.rebind("rmi://localhost/bangalore_gumball", bangaloreMachine);

            System.out.println("Gumball machines bound in RMI registry");

            // Simulate some actions on the server side
            chennaiMachine.insertQuarter();
            chennaiMachine.turnCrank();

            bangaloreMachine.insertQuarter();
            bangaloreMachine.turnCrank();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
