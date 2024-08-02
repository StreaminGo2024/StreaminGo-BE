package com.project.demo.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class VideoControlHandler extends TextWebSocketHandler {
    private Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private String currentStatus = "PAUSE"; // Default status

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        // Enviar el estado actual del video a los nuevos clientes
        session.sendMessage(new TextMessage("STATUS:" + currentStatus));
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // Enviar el mensaje recibido a todos los clientes
        String msg = message.getPayload();
        if (msg.startsWith("CONTROL:")) {
            currentStatus = msg.substring(8); // Obtener el comando de control
            broadcastMessage("STATUS:" + currentStatus);
        }
    }

    private void broadcastMessage(String message) throws IOException {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }
}
