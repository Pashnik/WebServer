package chat;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatService {

    private static ChatService chatService;
    private static Set<ChatWebSocket> sockets;

    private ChatService() {
    }

    public static ChatService getInstance() {
        if (chatService == null) {
            chatService = new ChatService();
            sockets = Collections.newSetFromMap(new ConcurrentHashMap<>());
        }
        return chatService;
    }

    public void addSocket(ChatWebSocket socket) {
        sockets.add(socket);
    }

    public void removeSocket(ChatWebSocket socket) {
        sockets.remove(socket);
    }

    public void removeAll() {
        sockets.clear();
    }

    public void sendMessages(String data) {
        for (ChatWebSocket socket : sockets) {
            try {
                socket.sendMessage(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
