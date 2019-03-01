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
    }

    @OnWebSocketClose
    public void onClose(int status, String reason) {
        System.out.println("Close: statusCode =" + status + ", reason =" + reason);
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        try {
            session.getRemote().sendString(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


