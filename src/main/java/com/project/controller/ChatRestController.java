package com.project.controller;

import com.project.model.Chat.ChatMessage;
import com.project.repository.ChatMessageRepository;
import com.project.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ChatRestController {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatService chatService;

    public ChatRestController(ChatMessageRepository chatMessageRepository, ChatService chatService) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatService = chatService;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        chatMessage.setTimestamp(LocalDateTime.now());
        chatMessageRepository.save(chatMessage);

        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }


    @GetMapping("/api/chat")
    public List<ChatMessage> getAllMessages() {
        return chatService.getAllMessages();
    }
}
