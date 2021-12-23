package com.naumdeveloper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {

    public static void main(String[] args) {
        new Server(8189);
    }

   private DataInputStream in;
   private DataOutputStream out;
   private ServerSocket serverSocket;


    ServerApp(){
          try {
              serverSocket = new ServerSocket(8189);
              System.out.println("Сервер запущен на порту 8189. Ожидаем подключение клиента...");
              Socket socket = serverSocket.accept();
              in = new DataInputStream(socket.getInputStream());
              out = new DataOutputStream(socket.getOutputStream());
              System.out.println("Клиент подключился");

              int msnCounter = 0;

            while (true) {
                String msg = in.readUTF();
                System.out.println(msg);

                if (msg.startsWith("/")){
                    if(msg.equals("/stat")){
                        out.writeUTF("Messan out " + msnCounter);
                        continue;
                    }
                }
                out.writeUTF("ECHO: " + msg);
                msnCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
