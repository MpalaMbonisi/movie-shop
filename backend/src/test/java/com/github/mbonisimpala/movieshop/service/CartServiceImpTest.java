package com.github.mbonisimpala.movieshop.service;

import com.github.mbonisimpala.movieshop.entity.Account;
import com.github.mbonisimpala.movieshop.entity.CartItem;
import com.github.mbonisimpala.movieshop.entity.Genre;
import com.github.mbonisimpala.movieshop.entity.Movie;
import com.github.mbonisimpala.movieshop.exception.AccountNotFoundException;
import com.github.mbonisimpala.movieshop.exception.MovieNotFoundException;
import com.github.mbonisimpala.movieshop.repository.CartRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceImpTest {

    @Mock
    CartRepository cartRepository;
    @InjectMocks
    CartServiceImp cartService;
    @Mock
    MovieServiceImp movieService;
    @Mock
    AccountServiceImp accountService;

    @Test
    public void getAllCartItems_ExistingId_ShouldReturnList(){
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

        List<CartItem> cartItemsList = List.of(new CartItem[]{
                new CartItem(mockAccount, movieList.get(0)),
                new CartItem(mockAccount, movieList.get(1)),
                new CartItem(mockAccount, movieList.get(2))
        });

        when(cartRepository.findByAccountId(accountId)).thenReturn(cartItemsList);

        // Act
        List<CartItem> results = cartService.getAllCartItems(accountId);

        // Assert
        Assertions.assertEquals(3, results.size(), "List size should be 3.");
        Assertions.assertEquals(cartItemsList, results, "List of movies should match.");
    }

    @Test
    public void getAllCartItems_NoCartItems_ShouldReturnEmptyList(){
        // Arrange
        long accountId = 1L;

        when(cartRepository.findByAccountId(accountId)).thenReturn(List.of());

        // Act
        List<CartItem> results = cartService.getAllCartItems(accountId);

        // Assert
        Assertions.assertTrue(results.isEmpty(), "List should be empty.");
    }

    @Test
    public void getAllCartItems_NonExistingAccountId_ShouldReturnEmptyList(){
        // Arrange
        long accountId = 999L; // Non-existing ID

        when(cartRepository.findByAccountId(accountId)).thenReturn(List.of());

        // Act
        List<CartItem> results = cartService.getAllCartItems(accountId);

        // Assert
        Assertions.assertTrue(results.isEmpty(), "List should be empty.");
    }

    @Test
    public void saveCartItem_ExistingIds_ShouldReturnCartItem(){
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


        CartItem cartItem = new CartItem(mockAccount, mockMovie);

        when(accountService.getAccount(accountId)).thenReturn(mockAccount);
        when(movieService.getMovie(movieId)).thenReturn(mockMovie);
        when(cartRepository.save(any(CartItem.class))).thenReturn(cartItem);

        // Act
        CartItem result = cartService.saveCartItem(accountId, movieId);

        // Assert
        Assertions.assertAll("cartItem properties",
                () -> Assertions.assertEquals("The Invisible Man", result.getMovie().getTitle(), "Movie title should match."),
                () -> Assertions.assertEquals("5.99", result.getMovie().getPrice(), "Movie price should match."),
                () -> Assertions.assertEquals("mpalambonisi63@gmail.com", result.getAccount().getEmail(), "Account email should match."),
                () -> Assertions.assertEquals("0000", result.getAccount().getPassword(), "Account password should match.")
        );
    }
    @Test
    public void saveCartItem_NonExistingAccountId_ShouldThrowException(){
        // Arrange
        long accountId = 999L; // Non-existing ID
        long movieId = 1L;

        when(accountService.getAccount(accountId)).thenThrow(new AccountNotFoundException(Long.toString(accountId)));

        // Act & Assert
        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            cartService.saveCartItem(accountId, movieId);
        }, "Should throw AccountNotFoundException.");
    }

    @Test
    public void saveCartItem_NonExistingMovieId_ShouldThrowException(){
        // Arrange
        long accountId = 1L;
        long movieId = 999L; // Non-existent ID

        when(movieService.getMovie(movieId)).thenThrow(new MovieNotFoundException(movieId));

        // Act & Assert
        Assertions.assertThrows(MovieNotFoundException.class, () -> {
            cartService.saveCartItem(accountId, movieId);
        }, "Should throw MovieNotFoundException.");
    }

    @Test
    public void deleteCartItem_ExistingIds_ShouldCallRepositoryDelete(){
        // Arrange
        long accountId = 1L;
        long movieId = 1L;

        // Act
        cartService.deleteCartItem(accountId, movieId);

        // Assert
        verify(cartRepository, times(1)).deleteByAccountIdAndMovieId(accountId, movieId);
    }

    @Test
    public void deleteCartItem_NonExistingMovieId_ShouldCallRepositoryDelete(){
        // Arrange
        long accountId = 1L;
        long movieId = 999L; // Non-existing ID

        // Act
        cartService.deleteCartItem(accountId, movieId);

        // Assert
        verify(cartRepository, times(1)).deleteByAccountIdAndMovieId(accountId, movieId);
    }

    @Test
    public void deleteCartItem_NonExistingAccountId_ShouldCallRepositoryDelete(){
        // Arrange
        long accountId = 999L; // Non-existing ID
        long movieId = 1L;

        // Act
        cartService.deleteCartItem(accountId, movieId);

        // Assert
        verify(cartRepository, times(1)).deleteByAccountIdAndMovieId(accountId, movieId);
    }

    @Test
    public void deleteAllCartItems_ExistingAccountId_ShouldCallRepositoryDelete(){
        // Arrange
        long accountId = 1L;

        // Act
        cartService.deleteAllCartItems(accountId);

        // Assert
        verify(cartRepository, times(1)).deleteByAccountId(accountId);
    }

    @Test
    public void deleteAllCartItems_NonExistingAccountId_ShouldCallRepositoryDelete(){
        // Arrange
        long accountId = 999L; // mock of non-existing ID

        // Act
        cartService.deleteAllCartItems(accountId);

        // Assert
        verify(cartRepository, times(1)).deleteByAccountId(accountId);
    }
}
