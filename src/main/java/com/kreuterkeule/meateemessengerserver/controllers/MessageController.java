package com.kreuterkeule.meateemessengerserver.controllers;

import com.kreuterkeule.meateemessengerserver.dto.SendDto;
import com.kreuterkeule.meateemessengerserver.entities.MessageEntity;
import com.kreuterkeule.meateemessengerserver.entities.UserEntity;
import com.kreuterkeule.meateemessengerserver.repositories.MessageRepository;
import com.kreuterkeule.meateemessengerserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/send")
    public ResponseEntity<MessageEntity> send(@RequestBody SendDto sendDto) {

        UserEntity client = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (client == null) {
            return new ResponseEntity<>(new MessageEntity("bad_request",
                    "bad_request",
                    "bad_request",
                    "bob_request"), HttpStatus.BAD_REQUEST);
        }
        String fromToken = client.getToken();
        MessageEntity message = messageRepository.save(new MessageEntity(sendDto.getText(),
                sendDto.getAESKeyEnc(),
                fromToken,
                sendDto.getToToken()));
        return new ResponseEntity<>(message, HttpStatus.OK); // send text message (auto(from), to, time, encrypted(text), encrypted(key))
    }

    @GetMapping("receive")
    public ResponseEntity<List<MessageEntity>> receive() {
        UserEntity client = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        String token = client.getToken();
        List<MessageEntity> messages = messageRepository.findByToToken(token);
        messageRepository.deleteAll(messages);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

}
