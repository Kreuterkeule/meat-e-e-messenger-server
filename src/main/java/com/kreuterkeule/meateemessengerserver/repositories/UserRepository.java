package com.kreuterkeule.meateemessengerserver.repositories;

import com.kreuterkeule.meateemessengerserver.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public UserEntity findByUsername(String username);

    public UserEntity findByToken(String token);

    @Query(value = "SELECT u FROM UserEntity u WHERE u.isPublic = true AND u.username LIKE %:query%")
    public List<UserEntity> searchPublicUsers(@Param("query") String query);
}
