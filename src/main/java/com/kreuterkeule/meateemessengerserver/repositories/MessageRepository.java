package com.kreuterkeule.meateemessengerserver.repositories;

import com.kreuterkeule.meateemessengerserver.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    public List<MessageEntity> findByToToken(String token);

}
