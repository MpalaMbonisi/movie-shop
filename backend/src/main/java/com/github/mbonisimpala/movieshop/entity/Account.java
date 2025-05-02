package com.github.mbonisimpala.movieshop.entity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "account", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"})
})
public class Account {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(name = "email", nullable = false)
    @NotBlank(message = "email cannot be blank")
    private String email;

    @NonNull
    @Column(name = "password", nullable = false)
    @NotBlank(message = "password cannot be blank")
    private String password;

}
