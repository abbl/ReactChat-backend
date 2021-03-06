package pl.abbl.reactchat.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.abbl.reactchat.models.ChatMessage;
import pl.abbl.reactchat.models.ChatRoom;
import pl.abbl.reactchat.models.ChatUser;
import pl.abbl.reactchat.services.ContextChangeService;
import pl.abbl.reactchat.services.RoomRightService;
import pl.abbl.reactchat.services.UserService;

import java.security.Principal;
import java.util.Set;

@Service
public class ContextChangeServiceImpl implements ContextChangeService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private SimpUserRegistry simpUserRegistry;
    @Autowired
    private UserService userService;
    @Autowired
    private RoomRightService roomRightService;

    @Async
    @Override
    public void updateUsersOnRoomChange(ChatRoom chatRoom) {
        Set<SimpUser> users = simpUserRegistry.getUsers();

        for(SimpUser simpUser : users){
            ChatUser chatUser = userService.getUserInformationByUsername(simpUser.getName());

            if(chatUser != null){
                if(roomRightService.getUserRight(chatUser.getId(), chatRoom.getId()) != null){
                    messagingTemplate.convertAndSendToUser(simpUser.getName(),"/topic/chatRoomList", chatRoom);
                }
            }
        }
    }

    @Override
    public void updateUsersOnNewMessage(ChatRoom chatRoom, ChatMessage chatMessage) {
        messagingTemplate.convertAndSend("/topic/chatroom/" + chatRoom.getId(), chatMessage);
    }

    @Override
    public void updateUserOnRoomListChange(ChatRoom chatRoom, Principal principal) {
        messagingTemplate.convertAndSendToUser(principal.getName(), "/topic/chatRoomList", chatRoom);
    }
}
