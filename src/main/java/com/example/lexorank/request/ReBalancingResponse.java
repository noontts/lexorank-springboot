package com.example.lexorank.request;

import com.example.lexorank.entity.RatingMovie;

import java.util.List;

public record ReBalancingResponse (
        String statusCode,
        String error,
        List<RatingMovie> ratingMovieLists
){
}
