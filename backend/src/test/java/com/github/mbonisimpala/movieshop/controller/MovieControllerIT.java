package com.github.mbonisimpala.movieshop.controller;


import com.github.mbonisimpala.movieshop.entity.Genre;
import com.github.mbonisimpala.movieshop.entity.Movie;
import com.github.mbonisimpala.movieshop.repository.GenreRepository;
import com.github.mbonisimpala.movieshop.repository.MovieRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovieControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private GenreRepository genreRepository;
    private Genre mockGenre01, mockGenre02;

    @BeforeEach
    void setup(){
        mockGenre01 = new Genre("Action");
        mockGenre02 = new Genre("Crime");
        mockGenre01 = genreRepository.save(mockGenre01);
        mockGenre02 = genreRepository.save(mockGenre02);
    }
    @AfterEach
    void cleanup(){
        movieRepository.deleteAll();
        genreRepository.deleteAll();
    }

    @Test
    public void shouldReturnMovie_whenGettingExistingMovieId() throws Exception{
        // Given
        Movie mockMovie = new Movie("The Invisible Man", mockGenre01, 2020, "124 min",
                "When Cecilia's abusive ex takes his own life and leaves her his fortune, she suspects his death was a hoax. As a series of coincidences turn lethal, Cecilia works to prove that she is being hunted by someone nobody can see.",
                "Elisabeth Moss, Oliver Jackson-Cohen, Harriet Dyer", "5.99", "English",
                "https://m.media-amazon.com/images/M/MV5BYTM3NDJhZWUtZWM1Yy00ODk4LThjNmMtNDg3OGYzMGM2OGYzXkEyXkFqcGc@._V1_SX300.jpg",
                "https://www.youtube.com/watch?v=WO_FJdiY9dA");
        mockMovie = movieRepository.save(mockMovie); // Save and get the persisted entity with ID

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/movie/" + mockMovie.getId());

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("The Invisible Man"))
                .andExpect(jsonPath("$.movieLanguage").value("English"))
                .andExpect(jsonPath("$.trailer").value("https://www.youtube.com/watch?v=WO_FJdiY9dA"));
    }

    @Test
    public void shouldReturnNotFound_whenGettingNonExistingMovieId() throws Exception{
        // Given (Non-existent ID)
        long movieId = 999L;

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/movie/" + movieId);

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message.length()").value(1))
                .andExpect(jsonPath("$.message").value("The movie id '999' does not exist in our records."));
    }

    @Test
    public void shouldReturnMovieList_WhenMoviesAreAvailable() throws Exception{
        // Given there are movies
        List<Movie> movieList = List.of(new Movie[]{
                new Movie("John Wick", mockGenre01, 2014, "101 min",
                        "John Wick is a former hit-man grieving the loss of his true love. When his home is broken into, robbed, and his dog killed, he is forced to return to action to exact revenge."
                        , "Keanu Reeves, Michael Nyqvist, Alfie Allen", "75.00", "English, Russian, Hungarian",
                        "https://m.media-amazon.com/images/M/MV5BMTU2NjA1ODgzMF5BMl5BanBnXkFtZTgwMTM2MTI4MjE@._V1_SX300.jpg"
                        , "https://www.youtube.com/watch?v=C0BMx-qxsP4"),

                new Movie("Inception", mockGenre02, 2010, "148 min",
                        "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster."
                        , "Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page", "9.99", "English, Japanese, French",
                        "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_SX300.jpg"
                        , "https://www.youtube.com/watch?v=YoHD9XEInc0"),

                new Movie("The Invisible Man", mockGenre01, 2020, "124 min",
                        "When Cecilia's abusive ex takes his own life and leaves her his fortune, she suspects his death was a hoax. As a series of coincidences turn lethal, Cecilia works to prove that she is being hunted by someone nobody can see."
                        , "Elisabeth Moss, Oliver Jackson-Cohen, Harriet Dyer", "5.99", "English",
                        "https://m.media-amazon.com/images/M/MV5BYTM3NDJhZWUtZWM1Yy00ODk4LThjNmMtNDg3OGYzMGM2OGYzXkEyXkFqcGc@._V1_SX300.jpg"
                        , "https://www.youtube.com/watch?v=WO_FJdiY9dA")
        });
        movieRepository.saveAll(movieList);

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/movie/all");

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].price").value("75.00"))
                .andExpect(jsonPath("$[1].title").value("Inception"))
                .andExpect(jsonPath("$[2].actors").value("Elisabeth Moss, Oliver Jackson-Cohen, Harriet Dyer"));
    }

    @Test
    public void shouldReturnEmptyList_whenNoMoviesAvailable() throws Exception{
        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/movie/all");

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void shouldReturnMovieList_whenGettingAvailableMovies() throws Exception{
        // Given
        List<Movie> movieList = List.of(new Movie[]{
                new Movie("John Wick", mockGenre01, 2014, "101 min",
                        "John Wick is a former hit-man grieving the loss of his true love. When his home is broken into, robbed, and his dog killed, he is forced to return to action to exact revenge."
                        , "Keanu Reeves, Michael Nyqvist, Alfie Allen", "75.00", "English, Russian, Hungarian",
                        "https://m.media-amazon.com/images/M/MV5BMTU2NjA1ODgzMF5BMl5BanBnXkFtZTgwMTM2MTI4MjE@._V1_SX300.jpg"
                        , "https://www.youtube.com/watch?v=C0BMx-qxsP4"),

                new Movie("Inception", mockGenre02, 2010, "148 min",
                        "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster."
                        , "Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page", "9.99", "English, Japanese, French",
                        "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_SX300.jpg"
                        , "https://www.youtube.com/watch?v=YoHD9XEInc0"),

                new Movie("The Invisible Man", mockGenre01, 2020, "124 min",
                        "When Cecilia's abusive ex takes his own life and leaves her his fortune, she suspects his death was a hoax. As a series of coincidences turn lethal, Cecilia works to prove that she is being hunted by someone nobody can see."
                        , "Elisabeth Moss, Oliver Jackson-Cohen, Harriet Dyer", "5.99", "English",
                        "https://m.media-amazon.com/images/M/MV5BYTM3NDJhZWUtZWM1Yy00ODk4LThjNmMtNDg3OGYzMGM2OGYzXkEyXkFqcGc@._V1_SX300.jpg"
                        , "https://www.youtube.com/watch?v=WO_FJdiY9dA")
        });

        movieRepository.saveAll(movieList);

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/movie/genre/" + mockGenre01.getId());

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].title").value("The Invisible Man"))
                .andExpect(jsonPath("$[0].duration").value("101 min"));
    }

    @Test
    public void shouldReturnNotFound_whenGettingNonExistingGenre() throws Exception{
        // Given (non-existing genre Id)
        long genreId = 999L;

        // When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/movie/genre/" + genreId);

        // Perform & verify
        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message.length()").value(1))
                .andExpect(jsonPath("$.message").value("The genre with id '999' does not exist in our records."));
    }

}
