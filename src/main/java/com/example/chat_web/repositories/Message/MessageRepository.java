package com.example.chat_web.repositories.Message;

import com.example.chat_web.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(
            value = "SELECT m.* FROM messages m JOIN conversations c ON m.conversation_id = c.id " +
                    "WHERE (c.user_1 = ?1 AND c.user_2 = ?2) OR (c.user_1 = ?2 AND c.user_2 = ?1) " +
                    "ORDER BY m.create_at ASC",
            nativeQuery = true)
    List<Message> getMessageByUsername(String user1, String user2);

    @Query(
            value = "SELECT * " +
                    "FROM ( " +
                    "    SELECT DISTINCT ON (m.conversation_id) * " +
                    "    FROM messages m " +
                    "    WHERE m.conversation_id IN (?1) " +
                    "    ORDER BY m.conversation_id, m.create_at DESC " +
                    ") subquery " +
                    "ORDER BY subquery.create_at DESC;",
            nativeQuery = true)
    List<Message> findByConversationId(List<Long> conversationId);
}
