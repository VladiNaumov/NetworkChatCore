package com.naumdeveloper;

import javax.swing.text.html.ListView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller {

   // ListView<String> clientsList;

    private Network network;
    private HistoryManager historyManager;
    private String username;

    public void setUsername(String username) {
        this.username = username;
        boolean usernameIsNull = username == null;
    }

  /*
    public void initialize(URL location, ResourceBundle resources) {
        setUsername(null);
        network = new Network();

        network.setOnAuthFailedCallback(args -> msgArea.appendText((String)args[0] + "\n"));

        network.setOnAuthOkCallback(args -> {
            String msg = (String)args[0];
            setUsername(msg.split("\\s")[2]);
            historyManager.init(msg.split("\\s")[1]);
            msgArea.clear();
            msgArea.appendText(historyManager.load());
        });

        network.setOnMessageReceivedCallback(args -> {
            String msg = (String)args[0];
            if (msg.startsWith("/")) {
                if (msg.startsWith("/clients_list ")) {
                    String[] tokens = msg.split("\\s");
                    Platform.runLater(() -> {
                        clientsList.getItems().clear();
                        for (int i = 1; i < tokens.length; i++) {
                            clientsList.getItems().add(tokens[i]);
                        }
                    });
                }
                return;
            }
            historyManager.write(msg + "\n");
            msgArea.appendText(msg + "\n");
        });

        network.setOnDisconnectCallback(args -> {
            setUsername(null);
            historyManager.close();
        });

        historyManager = new HistoryManager();
    }


    public void login() {
        if (loginField.getText().isEmpty()) {
            showErrorAlert("Имя пользователя не может быть пустым");
            return;
        }

        if (!network.isConnected()) {
            try {
                network.connect(8189);
            } catch (IOException e) {
                showErrorAlert("Невозможно подключиться к серверу на порт: " + 8189);
                return;
            }
        }

        try {
            network.tryToLogin(loginField.getText(), passwordField.getText());
        } catch (IOException e) {
            showErrorAlert("Невозможно отправить данные пользователя");
        }
    }

    public void sendMsg() {
        try {
            network.sendMessage(msgField.getText());
            msgField.clear();
            msgField.requestFocus();
        } catch (IOException e) {
            showErrorAlert("Невозможно отправить сообщение");
        }
    }

    public void exit() {
        network.disconnect();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.setTitle("Chat FX");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

   */
}
