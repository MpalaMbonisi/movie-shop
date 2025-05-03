package com.github.mbonisimpala.movieshop.entity;

import javax.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "ordered_movies", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"account_id", "movie_id"})
})
public class OrderedMovie {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

}
