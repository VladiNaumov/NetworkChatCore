package com.naumdeveloper;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleClient {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        Network network = new Network();
        network.connect(8189);

        network.setOnMessageReceivedCallback(args1 -> {
            String msg = (String) args1[0];
            System.out.println(msg);
        });

        System.out.println(" write please: login password");
        String loinLine = sc.nextLine();

        /* VANHA
        String login = loinLine.split("\\s+")[1];
        String password = loinLine.split("\\s+")[2];
         */

        String login = loinLine.split("\\s+")[0];
        String password = loinLine.split("\\s+")[1];

        network.tryToLogin(login,password);

        while (true){
            String message = sc.nextLine();
            network.sendMessage(message);
        }
    }
}
