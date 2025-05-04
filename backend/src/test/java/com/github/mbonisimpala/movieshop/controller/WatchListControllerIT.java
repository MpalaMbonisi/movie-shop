package com.github.mbonisimpala.movieshop.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.github.mbonisimpala.movieshop.entity.Account;
import com.github.mbonisimpala.movieshop.entity.Genre;
import com.github.mbonisimpala.movieshop.entity.Movie;
import com.github.mbonisimpala.movieshop.repository.*;
import com.github.mbonisimpala.movieshop.security.SecurityConstants;
import com.github.mbonisimpala.movieshop.service.WatchListService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WatchListControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WatchListRepository watchListRepository;
    @Autowired
    private WatchListService watchListService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private MovieRepository movieRepository;
    private String token;
    private Account testAccount;
    private Movie movie01, movie02, movie03;

    @BeforeEach
    void setup(){
        // create a test account
        testAccount = new Account("testuser@gmail.com", "0000");
        accountRepository.save(testAccount); // add a test account to record

        // provide JWT for the test account
        token = JWT.create()
                .withSubject("testuser@gmail.com")
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));

        // create mock genre
        Genre mockGenre = new Genre("Action");
        mockGenre = genreRepository.save(mockGenre);

        // add movies
        List<Movie> movieList = List.of(new Movie[]{
                new Movie("John Wick", mockGenre, 2014, "101 min",
                        "John Wick is a former hit-man grieving the loss of his true love. When his home is broken into, robbed, and his dog killed, he is forced to return to action to exact revenge."
                        , "Keanu Reeves, Michael Nyqvist, Alfie Allen", "75.00", "English, Russian, Hungarian",
                        "https://m.media-amazon.com/images/M/MV5BMTU2NjA1ODgzMF5BMl5BanBnXkFtZTgwMTM2MTI4MjE@._V1_SX300.jpg"
                        , "https://www.youtube.com/watch?v=C0BMx-qxsP4"),

                new Movie("Inception", mockGenre, 2010, "148 min",
                        "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster."
                        , "Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page", "9.99", "English, Japanese, French",
                        "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_SX300.jpg"
                        , "https://www.youtube.com/watch?v=YoHD9XEInc0"),

                new Movie("The Invisible Man", mockGenre, 2020, "124 min",
                        "When Cecilia's abusive ex takes his own life and leaves her his fortune, she suspects his death was a hoax. As a series of coincidences turn lethal, Cecilia works to prove that she is being hunted by someone nobody can see."
                        , "Elisabeth Moss, Oliver Jackson-Cohen, Harriet Dyer", "5.99", "English",
                        "https://m.media-amazon.com/images/M/MV5BYTM3NDJhZWUtZWM1Yy00ODk4LThjNmMtNDg3OGYzMGM2OGYzXkEyXkFqcGc@._V1_SX300.jpg"
                        , "https://www.youtube.com/watch?v=WO_FJdiY9dA")
        });
        movie01 = movieRepository.save(movieList.get(0));
        movie02 = movieRepository.save(movieList.get(1));
        movie03 = movieRepository.save(movieList.get(2));

    }

    @AfterEach
    void cleanup(){
        // keep this order to avoid SQLExceptions
        watchListRepository.deleteAll();
        movieRepository.deleteAll();
        genreRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void shouldReturnWatchListMovies_whenGettingExistingAccount() throws Exception{
        // Given an existing account with watchlist
        watchListService.saveWatchListItem(testAccount.getId(), movie01.getId());
        watchListService.saveWatchListItem(testAccount.getId(), movie02.getId());
        watchListService.saveWatchListItem(testAccount.getId(), movie03.getId());

        // When
        String url = "/watchlist/account/" + testAccount.getId() + "/all";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(url)
                .header("Authorization", "Bearer " + token);

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    public void shouldReturnEmptyList_whenGettingExistingAccount() throws Exception{
        // Given an existing account with no movies on watchlist
        // When
        String url = "/watchlist/account/" + testAccount.getId() + "/all";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(url)
                .header("Authorization", "Bearer " + token);

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void shouldReturnWatchlistMovie_whenSavingExistingMovieIdAndAccountId() throws Exception{
        // Given existing Account ID and movie ID
        long accountId = testAccount.getId();
        long movieId = movie01.getId();

        // When
        String url = "/watchlist/account/" + accountId + "/movie/" + movieId;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url)
                .header("Authorization", "Bearer " + token);

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.account.id").value(accountId))
                .andExpect(jsonPath("$.movie.id").value(movieId));
    }

    @Test
    public void shouldNoContent_whenDeletingExistingMovieIdAndAccountId() throws Exception{
        // Given existing Account ID and movie ID
        long accountId = testAccount.getId();
        long movieId = movie01.getId();

        // When
        String url = "/watchlist/account/" + accountId + "/movie/" + movieId;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(url)
                .header("Authorization", "Bearer " + token);

        // Perform & verify
        mockMvc.perform(request).andExpect(status().isNoContent());
    }

}
