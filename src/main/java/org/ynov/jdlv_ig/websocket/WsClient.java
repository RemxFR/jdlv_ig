package org.ynov.jdlv_ig.websocket;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;


public class WsClient {

    private final String URL_WEB_SOCKER_SERVEUR = "ws://127.0.0.1:8080/tchat";

    public WsClient() {

    }

    public void connectToSocket() {
//        WebSocket ws = HttpClient
//                .newHttpClient()
//                .newWebSocketBuilder()
//                .buildAsync(URI.create(URL_WEB_SOCKER_SERVEUR), new WebSocketClient())
//                .join();
//        ws.sendText("essayons !", true);
    }
}
