package org.ynov.jdlv_ig.websocket;

import javafx.application.Platform;
import javafx.scene.layout.VBox;
import org.ynov.jdlv_ig.service.TchatService;

import java.io.*;
import java.net.Socket;

public class SocketClient {
    /**
     * Socket pour permettre la connexion avec le backend/
     */
    private static Socket socket;
    /**
     * BufferReader qui permet de recevoir les bytes envoyés par le backend.
     */
    private static BufferedReader bufferedReader;
    /**
     * BufferWriter qui permet d'écrire les bytes qui vont être envoyés vers le backend.
     */
    private static BufferedWriter bufferedWriter;
    /**
     * Login de l'utilisateur
     */
    private String username;
    private Thread messageThread;

    /**
     * Constructeur qui initialise la connexion avec le back et écoute ce qui est reçu et envoie le nom de l'utilisateur
     * lors de cette initialisation.
     * @param socketConnectee
     * @param username
     */
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

    /**
     * Méthode pour envoyer les messages écrits dans le tchat.
     * @param text
     */
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

    /**
     * Méthode qui écoute les messages reçus depuis le backend.
     * @param vBox
     */
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

    /**
     * Méthode pour fermer tous les threads ouverts dans cette classe.
     */
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
