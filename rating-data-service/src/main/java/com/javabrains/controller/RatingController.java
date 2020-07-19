package com.javabrains.controller;

import com.javabrains.model.Rating;
import com.javabrains.model.RatingInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("ratings")
public class RatingController {

    List<Rating> ratings = Arrays.asList(new Rating("1", "1", 1.5f),
            new Rating("1", "2", 5f),
            new Rating("1", "3", 2f),
            new Rating("2", "1", 4f),
            new Rating("2", "2", 2f),
            new Rating("3", "1", 4.5f));


    @RequestMapping("/{userId}")
    public RatingInfo getRating(@PathVariable String userId) {
        List<Rating> userRatings = ratings.stream().filter(r -> r.getUserId().equalsIgnoreCase(userId)).collect(Collectors.toList());

        return new RatingInfo(userRatings, userRatings.size());
    }
}
