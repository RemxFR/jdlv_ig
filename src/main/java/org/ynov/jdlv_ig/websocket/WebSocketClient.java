package org.ynov.jdlv_ig.websocket;

import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

public class WebSocketClient implements WebSocket.Listener {
    @Override
    public void onOpen(WebSocket webSocket) {
        WebSocket.Listener.super.onOpen(webSocket);
        System.out.println("ça y est la connexion est ouverte ! :D");
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        System.out.println("OnText méthode est appelée :)");
        return WebSocket.Listener.super.onText(webSocket, data, last);
    }

    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        System.out.println("C'est bon, on ferme la connexion !");
        return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        System.out.println("Oupsy ! Une erreur de connexion est survenue :(");
    }
}
