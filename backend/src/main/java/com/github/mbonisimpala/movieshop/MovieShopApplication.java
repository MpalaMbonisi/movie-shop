package com.github.mbonisimpala.movieshop;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.github.mbonisimpala.movieshop.entity.Genre;
import com.github.mbonisimpala.movieshop.entity.Movie;
import com.github.mbonisimpala.movieshop.repository.GenreRepository;
import com.github.mbonisimpala.movieshop.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.*;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class MovieShopApplication implements CommandLineRunner {

	@Autowired
	MovieRepository movieRepository;

	@Autowired
	GenreRepository genreRepository;

	public static void main(String[] args) {
		SpringApplication.run(MovieShopApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		// Save all genres in database after running app
		Iterable<Genre> genres = List.of(new Genre[]{
				new Genre("Drama"),
				new Genre("Crime"),
				new Genre("Action"),
				new Genre("Animation"),
				new Genre("Sci-Fi")
		});
		genreRepository.saveAll(genres);

		// Save all the movies in the 'movieList.json' file
		try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("movieData/moviesList.json")))) {
			Gson gson = new Gson();
			// Read the entire JSON array from the file
			JsonArray moviesArray = gson.fromJson(br, JsonArray.class);

			for (int i = 0; i < moviesArray.size(); i++) {
				JsonObject movieJson = moviesArray.get(i).getAsJsonObject();

				// Extract values from the JsonObject and map to Movie constructor
				String title = movieJson.get("Title").getAsString();
				int genreId = movieJson.get("Genre").getAsInt();
				String year = movieJson.get("Year").getAsString();
				String runtime = movieJson.get("Runtime").getAsString();
				String plot = movieJson.get("Plot").getAsString();
				String actors = movieJson.get("Actors").getAsString();
				String price = movieJson.get("Price").getAsString();
				String language = movieJson.get("Language").getAsString();
				String poster = movieJson.get("Poster").getAsString();
				String trailer = movieJson.get("Trailer").getAsString();

				// Get the genre from the repository
				Optional<Genre> genreOptional = genreRepository.findById((long) genreId);

				if (genreOptional.isPresent()) {
					Genre genre = genreOptional.get();

					// Create and save the Movie object
					movieRepository.save(new Movie(
							title,
							genre,
							Integer.parseInt(year),
							runtime,
							plot,
							actors,
							price,
							language,
							poster,
							trailer
					));
				} else {
					// Log or handle the missing genre case
					System.err.println("Genre not found for ID: " + genreId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Bean
	public BCryptPasswordEncoder byCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

}
