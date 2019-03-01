package chat;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

@WebSocket
public class ChatWebSocket {

    private Session session;

    @OnWebSocketConnect
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("Connection is opened!");
        ChatService.getInstance().addSocket(this);
    }

    @OnWebSocketClose
    public void onClose(int status, String reason) {
        System.out.println("Close: statusCode =" + " " + status + ", reason =" + " " + reason);
        ChatService.getInstance().removeSocket(this);
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        ChatService.getInstance().sendMessages(data);
    }

    public void sendMessage(String message) throws IOException {
        session.getRemote().sendString(message);
    }

}


