package com.fernandocanabarro.whatsapp_clone_app_backend.chat.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fernandocanabarro.whatsapp_clone_app_backend.chat.constants.ChatConstants;
import com.fernandocanabarro.whatsapp_clone_app_backend.chat.entities.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, String> {

    @Query(name = ChatConstants.FIND_CHAT_BY_SENDER_ID)
    List<Chat> findChatsByUserId(@Param("senderId") String userId);

    @Query(name = ChatConstants.FIND_CHAT_BY_SENDER_ID_AND_RECEIVER_ID)
    Optional<Chat> findChatBySenderIdAndReceiverId(String senderId, String receiverId);

}
