package com.fernandocanabarro.whatsapp_clone_app_backend.user.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fernandocanabarro.whatsapp_clone_app_backend.user.constants.UserConstants;
import com.fernandocanabarro.whatsapp_clone_app_backend.user.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query(name = UserConstants.FIND_USER_BY_EMAIL)
    Optional<User> findByEmail(String email);

    @Query(name = UserConstants.FIND_USER_BY_PUBLIC_ID)
    Optional<User> findUserByPublicId(String publicId);

}
