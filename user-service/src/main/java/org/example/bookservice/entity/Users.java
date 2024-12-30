package org.example.bookservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.bookservice.dto.UserRequest;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @Column(unique = true)
    private String userId;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Books> borrowedBooks = new ArrayList<>();
    @CreationTimestamp
    private LocalDateTime createdAt;

    public static Users from(UserRequest request){
        Users user = new Users();
        user.setUserId(UUID.randomUUID().toString());
        user.setName(request.getName());
        return user;
    }
}
