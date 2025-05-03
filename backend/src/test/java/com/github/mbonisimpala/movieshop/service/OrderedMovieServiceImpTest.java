package com.github.mbonisimpala.movieshop.service;

import com.github.mbonisimpala.movieshop.entity.Account;
import com.github.mbonisimpala.movieshop.entity.Genre;
import com.github.mbonisimpala.movieshop.entity.Movie;
import com.github.mbonisimpala.movieshop.entity.OrderedMovie;
import com.github.mbonisimpala.movieshop.exception.AccountNotFoundException;
import com.github.mbonisimpala.movieshop.exception.MovieNotFoundException;
import com.github.mbonisimpala.movieshop.repository.OrderedMovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderedMovieServiceImpTest {

    @Mock
    OrderedMovieRepository orderedMovieRepository;
    @InjectMocks
    OrderMovieServiceImp orderedMovieService;
    @Mock
    MovieServiceImp movieService;
    @Mock
    AccountServiceImp accountService;

    @Test
    public void saveOrderedMovie_ExistingId_ShouldReturnOrderedMovie(){
        // Arrange
        long accountId = 1L;
        Account mockAccount = new Account("mpalambonisi63@gmail.com", "0000");

        Genre mockGenre = new Genre("Action");

        long movieId = 1L;
        Movie mockMovie = new Movie("The Invisible Man", mockGenre, 2020, "124 min",
                "When Cecilia's abusive ex takes his own life and leaves her his fortune, she suspects his death was a hoax. As a series of coincidences turn lethal, Cecilia works to prove that she is being hunted by someone nobody can see."
                , "Elisabeth Moss, Oliver Jackson-Cohen, Harriet Dyer", "5.99", "English",
                "https://m.media-amazon.com/images/M/MV5BYTM3NDJhZWUtZWM1Yy00ODk4LThjNmMtNDg3OGYzMGM2OGYzXkEyXkFqcGc@._V1_SX300.jpg"
                , "https://www.youtube.com/watch?v=WO_FJdiY9dA");

        OrderedMovie orderMovie = new OrderedMovie(mockAccount, mockMovie);

        when(accountService.getAccount(accountId)).thenReturn(mockAccount);
        when(movieService.getMovie(movieId)).thenReturn(mockMovie);
        when(orderedMovieRepository.save(any(OrderedMovie.class))).thenReturn(orderMovie);

        // Act
        OrderedMovie result = orderedMovieService.saveOrderedMovie(accountId, movieId);

        // Assert
        Assertions.assertAll("cartItem properties",
                () -> Assertions.assertEquals("The Invisible Man", result.getMovie().getTitle(), "Movie title should match."),
                () -> Assertions.assertEquals("5.99", result.getMovie().getPrice(), "Movie price should match."),
                () -> Assertions.assertEquals("mpalambonisi63@gmail.com", result.getAccount().getEmail(), "Account email should match."),
                () -> Assertions.assertEquals("0000", result.getAccount().getPassword(), "Account password should match.")
        );
    }

    @Test
    public void saveOrderedMovie_NonExistingAccountId_ShouldThrowException(){
        // Arrange
        long accountId = 999L; // Non-existing ID
        long movieId = 1L;

        when(accountService.getAccount(accountId)).thenThrow(new AccountNotFoundException(Long.toString(accountId)));

        // Act & Assert
        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            orderedMovieService.saveOrderedMovie(accountId, movieId);
        }, "Should throw AccountNotFoundException.");
    }

    @Test
    public void saveOrderedMovie_NonExistingMovieId_ShouldThrowException(){
        // Arrange
        long accountId = 1L;
        long movieId = 999L; // Non-existing ID

        when(movieService.getMovie(movieId)).thenThrow(new MovieNotFoundException(movieId));

        // Act & Assert
        Assertions.assertThrows(MovieNotFoundException.class, () -> {
            orderedMovieService.saveOrderedMovie(accountId, movieId);
        }, "Should throw MovieNotFoundException.");
    }

    @Test
    public void getAllOrderedMovies_ExistingId_ShouldReturnList(){
        // Arrange
        long accountId = 1L;
        Account mockAccount = new Account("mpalambonisi63@gmail.com", "0000");

        Genre mockGenre = new Genre("Action");

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

        List<OrderedMovie> orderedMovieList = List.of(new OrderedMovie[]{
                new OrderedMovie(mockAccount, movieList.get(0)),
                new OrderedMovie(mockAccount, movieList.get(1)),
                new OrderedMovie(mockAccount, movieList.get(2))
        });

        when(orderedMovieRepository.findByAccountId(accountId)).thenReturn(orderedMovieList);

        // Act
        List<OrderedMovie> results = orderedMovieService.getAllOrderedMovies(accountId);

        // Assert
        Assertions.assertEquals(3, results.size(), "List size should match.");
        Assertions.assertEquals(orderedMovieList, results, "List should match.");
    }

    @Test
    public void getAllOrderedMovies_NoOrderedMovies_ShouldReturnEmptyList(){
        // Arrange
        long accountId = 1L;

        when(orderedMovieRepository.findByAccountId(accountId)).thenReturn(List.of());

        // Act
        List<OrderedMovie> results = orderedMovieService.getAllOrderedMovies(accountId);

        // Assert
        Assertions.assertTrue(results.isEmpty(), "List should be empty.");
    }

    @Test
    public void getAllOrderedMovies_NonExistingAccountId_ShouldReturnEmptyList(){
        // Arrange
        long accountId = 999L; // Non-existing mock id

        when(orderedMovieRepository.findByAccountId(accountId)).thenReturn(List.of());

        // Act
        List<OrderedMovie> results = orderedMovieService.getAllOrderedMovies(accountId);

        // Assert
        Assertions.assertTrue(results.isEmpty(), "List should be empty.");
    }

}
