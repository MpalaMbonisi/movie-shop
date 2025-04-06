package com.github.mbonisimpala.movieshop.entity;

import javax.persistence.*;
import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "wishlist")
public class WishList {

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
