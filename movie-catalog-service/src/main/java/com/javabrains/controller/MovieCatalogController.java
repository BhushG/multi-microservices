package com.javabrains.controller;

import com.javabrains.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    private RestTemplate getRestTemplate() {
        return  new RestTemplate();
    }

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId){

        RatingInfo ratingInfo = restTemplate.getForObject("http://localhost:8083/ratings/"+userId, RatingInfo.class);

        List<CatalogItem> catalogItems = ratingInfo.getRatings().stream().map(rating -> {
            String movieId = rating.getMovieId();
            Movie movie = restTemplate.getForObject("http://localhost:8082/movies/"+movieId, Movie.class);
            return  new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
        }).collect(Collectors.toList());
        return catalogItems;
    }
}
