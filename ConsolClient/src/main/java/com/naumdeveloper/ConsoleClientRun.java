package com.naumdeveloper;

import java.io.IOException;
import java.util.Scanner;


public class ConsoleClientRun {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        Network network = new Network();
        network.connect(8189);

        network.setOnMessageReceivedCallback(new Callback() {
            @Override
            public void callback(Object... args) {
                String msg = (String) args[0];
                System.out.println(msg);
            }
        });

        System.out.println("Reding LOGIN and PASSWORD");
        String loinLine = sc.nextLine();
        String login = loinLine.split("\\s+")[1];
        String password = loinLine.split("\\s+")[2];
        network.sendMessage("/login " + login + " " + password);

        while (true){
            String message = sc.nextLine();
            network.sendMessage(message);
        }
    }
}
