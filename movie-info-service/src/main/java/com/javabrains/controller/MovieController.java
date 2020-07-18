package com.javabrains.controller;

import com.javabrains.model.Movie;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {

    List<Movie> movies = Arrays.asList(new Movie("1", "First Movie", "desc1"),
                                        new Movie("2", "Second Movie", "desc2"),
                                        new Movie("3", "Third Movie", "desc3")
            );


    @RequestMapping("/{movieId}")
    public Optional<Movie> getMovieInfo(@PathVariable  String movieId) {
        return movies.stream().filter(m -> m.getMovieId().equalsIgnoreCase(movieId)).findFirst();
    }
}
