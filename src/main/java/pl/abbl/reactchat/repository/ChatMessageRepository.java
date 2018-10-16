package pl.abbl.reactchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.abbl.reactchat.entity.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    @Query("SELECT m FROM ChatMessage m WHERE m.roomId = :roomId")
    public List<ChatMessage> findAllByRoomId(@Param("roomId") int roomId);
}