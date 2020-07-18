package com.javabrains.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RatingInfo {
    private List<Rating> ratings;
    private Integer count;
}
