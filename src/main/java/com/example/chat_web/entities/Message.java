package com.example.chat_web.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.mapping.Join;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "messages")
@Setter
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender")
    private Users sender;


    @Column(name = "content")
    private String content;

    @Column(name = "create_at")
    private Timestamp createAt;


    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

}
