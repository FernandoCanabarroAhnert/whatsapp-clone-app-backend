package com.fernandocanabarro.whatsapp_clone_app_backend.message.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fernandocanabarro.whatsapp_clone_app_backend.message.constants.MessageConstants;
import com.fernandocanabarro.whatsapp_clone_app_backend.message.entities.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query(name = MessageConstants.FIND_MESSAGES_BY_CHAT_ID)
    List<Message> findMessagesByChatId(String chatId);
    @Query(name = MessageConstants.SET_MESSAGES_TO_SEEN_BY_CHAT)
    @Modifying
    void setMessagesToSeenByChat(String newState, String chatId);
}
