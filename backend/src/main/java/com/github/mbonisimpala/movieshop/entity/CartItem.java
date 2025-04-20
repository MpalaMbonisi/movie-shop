package com.github.mbonisimpala.movieshop.entity;

import javax.persistence.*;
import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "cart", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"account_id", "movie_id"})
})
public class CartItem {

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
