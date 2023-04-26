package com.kreuterkeule.meateemessengerserver.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;


@Entity
@Table(name = "messages")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 4096)
    private String text; // encrypted via message key, cause text can be long
    private String messageKey; // encrypted via public key, cause size drastically increases when encrypted via rsa
    private String fromToken;
    private String toToken;
    @CreationTimestamp
    private Instant sendTime;

    /*
    *   the server can only see who sends messages to whom and when cause that is a must to transfer data, but those data
    *   isn't logged and will be deleted after the message as reached the receiver
    */

    public MessageEntity() {
    }

    public MessageEntity(String text, String messageKey, String fromToken, String toToken) {
        this.text = text;
        this.messageKey = messageKey;
        this.fromToken = fromToken;
        this.toToken = toToken;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFromToken() {
        return fromToken;
    }

    public void setFromToken(String fromToken) {
        this.fromToken = fromToken;
    }

    public String getToToken() {
        return toToken;
    }

    public void setToToken(String toToken) {
        this.toToken = toToken;
    }

    public Instant getSendTime() {
        return sendTime;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }
}
