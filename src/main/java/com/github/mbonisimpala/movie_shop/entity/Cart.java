package com.github.mbonisimpala.movie_shop.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "course")
public class Cart {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(name = "user_id", nullable = false)
    private long userId;

    @NonNull
    @Column(name = "movie_id", nullable = false)
    private long movieId;

}
