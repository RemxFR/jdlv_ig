package org.ynov.jdlv_ig.websocket;

import javafx.scene.layout.VBox;
import org.ynov.jdlv_ig.service.TchatService;

import java.io.*;
import java.net.Socket;

public class SocketClient {

    private static Socket socket;
    private static BufferedReader bufferedReader;
    private static BufferedWriter bufferedWriter;
    private String username;
    private Thread messageThread;

    public SocketClient(Socket socketConnectee, String username) {
        try {
            socket = socketConnectee;
            this.username = username;
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.sendMessage(username);
        } catch (IOException e) {
            closeEverything();
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
            closeEverything();
        }
    }

    public void listenForMessage(VBox vBox) {
        this.messageThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromServer;
                while (socket.isConnected()) {
                    try {
                        msgFromServer = bufferedReader.readLine();
                        TchatService.afficherMessageRecu(msgFromServer, vBox);
                    } catch (IOException e) {
                        closeEverything();
                    }
                }
            }
        });
        this.messageThread.start();
    }

    public static void closeEverything() {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
