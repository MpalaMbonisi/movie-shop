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
    @Column(name = "title")
    private String title;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;

    @NonNull
    @Column(name = "release_year")
    private int releaseYear;

    @NonNull
    @Column(name = "duration")
    private String duration;

    @NonNull
    @Column(name = "plot")
    private String plot;

    @NonNull
    @Column(name = "actors")
    private String actors;

    @NonNull
    @Column(name = "price")
    private String price;

    @NonNull
    @Column(name = "movie_language")
    private String movieLanguage;

    @NonNull
    @Column(name = "poster")
    private String poster;

    @NonNull
    @Column(name = "trailer")
    private String trailer;
}
