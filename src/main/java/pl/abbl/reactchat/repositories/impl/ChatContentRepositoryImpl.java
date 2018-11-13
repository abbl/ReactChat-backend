package pl.abbl.reactchat.repositories.impl;

import org.springframework.stereotype.Repository;
import pl.abbl.reactchat.models.ChatMessage;
import pl.abbl.reactchat.models.ChatRoom;
import pl.abbl.reactchat.repositories.ChatContentRepository;

import javax.persistence.EntityManager;
import java.util.List;

import static pl.abbl.reactchat.configs.ReactChatConfiguration.FETCH_RANGE_LIMIT;

/**
 * @inheritDoc
 */
@Repository
public class ChatContentRepositoryImpl implements ChatContentRepository {

    public ChatContentRepositoryImpl(EntityManager entityManager){

    }

    @Override
    public void createChatContentTable(ChatRoom chatRoom) {

    }

    @Override
    public void saveAndFlush(ChatRoom chatRoom, ChatMessage chatMessage) {

    }

    @Override
    public List<ChatMessage> findLastMessagesByRange(ChatRoom chatRoom, int range) {
        if(chatRoom != null && range <= FETCH_RANGE_LIMIT){

        }
        return null;
    }

    @Override
    public List<ChatMessage> findMessagesByIndexRange(ChatRoom chatRoom, int start, int end) {
        return null;
    }
}