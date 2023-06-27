package com.project.repository;

import com.project.model.Chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    List<ChatMessage> findByProjektProjektId(Integer projectId);
    List<ChatMessage> findAllByOrderByTimestampAsc();

}
