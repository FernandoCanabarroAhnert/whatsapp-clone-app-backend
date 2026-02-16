package com.fernandocanabarro.whatsapp_clone_app_backend.user.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fernandocanabarro.whatsapp_clone_app_backend.chat.entities.Chat;
import com.fernandocanabarro.whatsapp_clone_app_backend.shared.entities.BaseAuditingEntity;
import com.fernandocanabarro.whatsapp_clone_app_backend.user.constants.UserConstants;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
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
@Table(name = "users")
@NamedQuery(name = UserConstants.FIND_USER_BY_EMAIL, query = "SELECT u FROM User u WHERE u.email = :email")
@NamedQuery(name = UserConstants.FIND_ALL_USERS_EXCEPT_SELF, query = "SELECT u FROM User u WHERE u.id != :publicId")
@NamedQuery(name = UserConstants.FIND_USER_BY_PUBLIC_ID, query = "SELECT u FROM User u WHERE u.id = :publicId")
public class User extends BaseAuditingEntity {

    private static final int LAST_ACTIVE_INTERVAL = 5;

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime lastSeen;
    @OneToMany(mappedBy = "sender")
    private List<Chat> chatsAsSender = new ArrayList<>();
    @OneToMany(mappedBy = "receiver")
    private List<Chat> chatsAsReceiver = new ArrayList<>();

    @Transient
    public boolean isUserOnline() {
        return this.lastSeen != null && this.lastSeen.isAfter(LocalDateTime.now().minusMinutes(LAST_ACTIVE_INTERVAL));
    }

}
