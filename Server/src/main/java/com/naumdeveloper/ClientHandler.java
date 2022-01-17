package com.naumdeveloper;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

/* Класс отвечающий за авторизацию клеента к сервера и создание отдельного потока подключения к серверу */
public class ClientHandler {

    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String username;

    public String getUsername() {
        return username;
    }

    public ClientHandler(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());

        /*создание отдельного потока подключения к серверу*/
        new Thread(() -> {
            try {
                // Цикл авторизации
                while (true) {
                    String msg = in.readUTF();
                    if (executeAuthenticationMessage(msg)) {
                        break;
                    }
                }
                // Цикл общения с клиентом
                while (true) {
                    String msg = in.readUTF();
                    if (msg.startsWith("/")) {
                        executeCommand(msg);
                        continue;
                    }
                    server.broadcastMessage(username + ": " + msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                disconnect();
            }
        }).start();
    }

    private boolean executeAuthenticationMessage(String msg) {
        if (msg.startsWith("/login ")) {
            String[] tokens = msg.split("\\s+");
            if (tokens.length != 3) {
                sendMessage("/login_failed Введите имя пользователя и пароль");
                return false;
            }
            String login = tokens[1];
            String password = tokens[2];

           // TODO deleting optional
            Optional<String> userNickname = Optional.ofNullable(server.getAuthenticationProvider().getNicknameByLoginAndPassword(login, password));
            if (!userNickname.isPresent()) {
                sendMessage("/login_failed Введен некорретный логин/пароль");
                return false;
            }
            if (server.isUserOnline(userNickname.get())) {
                sendMessage("/login_failed Учетная запись уже используется");
                return false;
            }
            username = userNickname.get();
            sendMessage("/login_ok " + login + " " + username);
            server.subscribe(this);
            return true;
        }
        return false;
    }

    private void executeCommand(String cmd) {
        // /w Bob Hello, Bob!!!
        if (cmd.startsWith("/w ")) {
            String[] tokens = cmd.split("\\s+", 3);
            server.sendPrivateMessage(this, tokens[1], tokens[2]);
            return;
        }
        // /change_nick myNewNickname
        if (cmd.startsWith("/change_nick ")) {
            String[] tokens = cmd.split("\\s+");
            if (tokens.length != 2) {
                sendMessage("Server: Введена некорректная команда");
                return;
            }
            String newNickname = tokens[1];
            if (server.getAuthenticationProvider().isNickBusy(newNickname)) {
                sendMessage("Server: Такой никнейм уже занят");
                return;
            }
            server.getAuthenticationProvider().changeNickname(username, newNickname);
            username = newNickname;
            sendMessage("Server: Вы изменили никнейм на " + newNickname);
            server.broadcastClientsList();
        }
    }

    public void sendMessage(String message){
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            disconnect();
        }
    }

    public void disconnect() {
        server.unsubscribe(this);
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
