package com.kreuterkeule.meateemessengerserver.repositories;

import com.kreuterkeule.meateemessengerserver.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public UserEntity findByUsername(String username);

    public UserEntity findByToken(String token);

}
