package com.project.service;

import com.project.model.Chat.ChatMessage;
import com.project.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatServiceImpl(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }


    @Override
    public void saveChatMessage(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);

    }

    @Override
    public List<ChatMessage> getChatMessagesForProject(Integer projectId) {
        return chatMessageRepository.findByProjektProjektId(projectId);
    }

    @Override
    public List<ChatMessage> getAllMessages() {
        return chatMessageRepository.findAllByOrderByTimestampAsc();
    }
}