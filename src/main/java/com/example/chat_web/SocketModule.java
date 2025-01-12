package com.example.chat_web;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.example.chat_web.entities.Conversation;
import com.example.chat_web.entities.Users;
import com.example.chat_web.repositories.Conversation.ConversationRepository;
import com.example.chat_web.repositories.Message.MessageRepository;
import com.example.chat_web.services.Conservation.ConservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class SocketModule {


    private final SocketIOServer server;
    private final SocketService socketService;
    private final MessageRepository messageRepository;
    private Map<String, String> users = new HashMap<>();
    private final ConversationRepository conversationRepository;
    public SocketModule(SocketIOServer server, SocketService socketService, ConversationRepository conversationRepository, MessageRepository messageRepository) {
        this.conversationRepository = conversationRepository;
        this.server = server;
        this.socketService = socketService;
        this.messageRepository = messageRepository;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("send_message", Message.class, onChatReceived());
//        server.addEventListener("load_room", String.class, onLoadRoom());
    }


//    private DataListener<String> onLoadRoom() {
//        return (client, data, ackSender) -> {
//            String[] listSession = data.split(", ");
//            List<String> listUername = users.entrySet().stream().filter(elm -> {
//                for (String session : listSession) {
//                    if (elm.getValue().equals(session)) {
//                        return true;
//                    }
//                }
//                return false;
//            }).map(Map.Entry::getKey).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
//            System.out.println(listUername.toString());
//            Conversation conversation = conservationService.isExistConversation(listUername.get(0), listUername.get(1));
//            if (conversation == null) {
//                conversation = conservationService.createConversation(listUername.get(0), listUername.get(1));
//            }
//        };
//    }

    private DataListener<Message> onChatReceived() {
        return (senderClient, data, ackSender) -> {
            log.info(data.getSender() + " " + data.getContent() + " " + data.getRoom());
            System.out.println(data.getSender() + " " + data.getContent() + " " + data.getRoom() + " " + data.getCreate_at());
            com.example.chat_web.entities.Message message = com.example.chat_web.entities.Message.builder()
                    .content(data.getContent())
                    .sender(Users.builder().username(data.getSender()).build())
                    .conversation(Conversation.builder().id(Long.parseLong(data.getRoom())).build())
                    .createAt(new Timestamp(data.getCreate_at().getTime()))
                    .build();
            messageRepository.save(message);
            socketService.sendMessage(data.getSender(), data.getRoom(),"get_message", senderClient, data.getContent());
        };
    }


    private ConnectListener onConnected() {
        return (client) -> {
            String username = client.getHandshakeData().getSingleUrlParam("username");
            System.out.println(username);
            log.info(client.getHandshakeData().getUrl());
            client.joinRoom("notification");
            log.info(client.getAllRooms().toString());
            server.getRoomOperations("notification").getClients().forEach(socketIOClient -> {
                log.info(socketIOClient.getSessionId().toString());
            });
            users.put(username, client.getSessionId().toString());
            log.info("Socket ID[{}]  Connected to socket", client.getSessionId().toString());
            log.info(server.getAllClients().toString());
            log.info(client.getAllRooms().toString());
            System.out.println(users.toString());


            conversationRepository.findListConversation(username).forEach(conversation -> {
                client.joinRoom(conversation.getId().toString());
            });


            // Send noti to all user in room notification
            server.getRoomOperations("notification").sendEvent("notification", users.toString());

        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            log.info("Client[{}] - Disconnected from socket", client.getSessionId().toString());
            users.values().remove(client.getSessionId().toString());
            server.getRoomOperations("notification").sendEvent("notification", users.toString());
        };
    }

}