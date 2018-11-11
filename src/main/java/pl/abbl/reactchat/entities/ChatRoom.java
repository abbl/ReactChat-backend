package pl.abbl.reactchat.entities;

import lombok.Data;
import pl.abbl.reactchat.definitions.enums.ChatRoomStatus;
import pl.abbl.reactchat.definitions.enums.ChatRoomType;

import javax.persistence.*;

@Data
@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int ownerId;
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private ChatRoomType type;
    @Enumerated(EnumType.STRING)
    private ChatRoomStatus status;

    public ChatRoom(){
    }

    public ChatRoom(String name, String description, ChatRoomType type){
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public ChatRoom(int ownerId, String name, String description, ChatRoomStatus status){
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
        this.status = status;
    }
}