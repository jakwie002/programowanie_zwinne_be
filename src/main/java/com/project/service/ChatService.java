package com.project.service;

import com.project.model.Chat.ChatMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChatService {
    public void saveChatMessage(ChatMessage chatMessage);

    public List<ChatMessage> getChatMessagesForProject(Integer projectId);

    public List<ChatMessage> getAllMessages();


}
