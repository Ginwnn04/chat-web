package com.example.chat_web.repositories.Conversation;

import com.example.chat_web.entities.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    @Query(
            value = "SELECT c FROM Conversation c WHERE (c.user1 = ?1 AND c.user2 = ?2) OR (c.user1 = ?2 AND c.user2 = ?1)",
            nativeQuery = true)
    Optional<Conversation> isExistConversation(String user1, String user2);
}
