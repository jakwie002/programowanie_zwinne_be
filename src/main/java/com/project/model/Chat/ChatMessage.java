package com.project.model.Chat;

import com.project.model.Projekt;
import com.project.model.Student;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer messageId;

    @Column(nullable = false, length = 1000)
    private String content;

    private String sender;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "projekt_id")
    private Projekt projekt;

}
