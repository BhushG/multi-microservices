package com.javabrains.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rating {
    private String userId;
    private String movieId;
    private float rating;
}
