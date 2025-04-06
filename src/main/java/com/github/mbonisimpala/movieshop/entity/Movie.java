package com.github.mbonisimpala.movieshop.entity;

import javax.persistence.*;
import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NonNull
    @Column(name = "title", nullable = false)
    private String title;

    @NonNull
    @Column(name = "genre", nullable = false)
    private String genre;

    @NonNull
    @Column(name = "post_url", nullable = false)
    private String posterUrl;

    @NonNull
    @Column(name = "trailer_url", nullable = false)
    private String trailerUrl;

    @NonNull
    @Column(name = "price", nullable = false)
    private String price;

    @NonNull
    @Column(name = "movie_url", nullable = false)
    private String movieUrl;
}
