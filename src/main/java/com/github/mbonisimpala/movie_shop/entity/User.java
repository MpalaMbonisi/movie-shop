package com.github.mbonisimpala.movie_shop.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(name = "email", nullable = false)
    private String email;

    @NonNull
    @Column(name = "password", nullable = false)
    private String password;

}
