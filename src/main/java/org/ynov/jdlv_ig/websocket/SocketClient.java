package org.ynov.jdlv_ig.websocket;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.ynov.jdlv_ig.GameController;

import java.io.*;
import java.net.Socket;

public class SocketClient {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public SocketClient(Socket socket, String username) {
        try {
            this.socket = socket;
            this.username = username;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.sendMessage(username);
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage(String text) {
        boolean  messageEnvoye = false;
        try {
            while (socket.isConnected() && !messageEnvoye) {
                bufferedWriter.write(username + ": " + text);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                text = "";
                messageEnvoye = true;
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage(VBox vBox) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromServer;
                while (socket.isConnected()) {
                    try {
                        msgFromServer = bufferedReader.readLine();
                        GameController gameController = new GameController();
                        gameController.afficherMessageRecu(msgFromServer, vBox);
                    } catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();

    }

    public void closeEverything(Socket socket, BufferedReader reader, BufferedWriter writer) {
        try {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
