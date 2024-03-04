package org.ynov.jdlv_ig.websocket;

import lombok.extern.slf4j.Slf4j;
import org.ynov.jdlv_ig.utils.LoggerUtil;

import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

public class WebSocketClient implements WebSocket.Listener {
    LoggerUtil logger = new LoggerUtil();
    @Override
    public void onOpen(WebSocket webSocket) {
        WebSocket.Listener.super.onOpen(webSocket);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        return WebSocket.Listener.super.onText(webSocket, data, last);
    }

    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {

        this.logger.log(System.Logger.Level.ERROR, "Oupsy ! Une erreur de connexion est survenue :(");
    }
}
