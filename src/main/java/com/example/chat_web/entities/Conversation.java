package com.example.chat_web.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "conversations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_1")
    private Users user1;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_2")
    private Users user2;


}
