package com.fernandocanabarro.whatsapp_clone_app_backend.chat.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.NamedQuery;

import com.fernandocanabarro.whatsapp_clone_app_backend.chat.constants.ChatConstants;
import com.fernandocanabarro.whatsapp_clone_app_backend.message.entities.Message;
import com.fernandocanabarro.whatsapp_clone_app_backend.message.enums.MessageState;
import com.fernandocanabarro.whatsapp_clone_app_backend.message.enums.MessageType;
import com.fernandocanabarro.whatsapp_clone_app_backend.shared.entities.BaseAuditingEntity;
import com.fernandocanabarro.whatsapp_clone_app_backend.user.entities.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chats")
@NamedQuery(name = ChatConstants.FIND_CHAT_BY_SENDER_ID, 
    query = "SELECT DISTINCT c FROM Chat c WHERE c.sender.id = :senderId OR c.receiver.id = :senderId ORDER BY c.createdDate DESC")
@NamedQuery(name = ChatConstants.FIND_CHAT_BY_SENDER_ID_AND_RECEIVER_ID, 
    query = "SELECT DISTINCT c FROM Chat c WHERE (c.sender.id = :senderId AND c.receiver.id = :receiverId) OR (c.sender.id = :receiverId AND c.receiver.id = :senderId)")
public class Chat extends BaseAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;
    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER)
    @OrderBy("createdDate DESC")
    private List<Message> messages = new ArrayList<>();

    @Transient
    public String getChatName(String senderId) {
        if (this.receiver.getId().equals(senderId)) {
            return this.sender.getFirstName() + " " + this.sender.getLastName();
        }
        return this.receiver.getFirstName() + " " + this.receiver.getLastName();
    }

    @Transient
    public long getUnreadMessages(String senderId) {
        return messages.stream()
            .filter(message -> message.getReceiverId().equals(senderId))
            .filter(message -> message.getState() == MessageState.SENT)
            .count();
    }

    @Transient
    public String getLastMessage() {
        if (this.messages != null && !this.messages.isEmpty()) {
            Message lastMessage = this.messages.getFirst();
            if (lastMessage.getType() != MessageType.TEXT) {
                return "Attachment";
            }
            return lastMessage.getContent();
        }
        return null;
    }

    @Transient
    public LocalDateTime getLastMessageTime() {
        if (this.messages != null && !this.messages.isEmpty()) {
            return this.messages.getFirst().getCreatedDate();
        }
        return null;
    }

}
