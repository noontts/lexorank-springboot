package com.example.lexorank.request;

import com.example.lexorank.entity.RatingMovie;

import java.util.List;

public record SwitchRankResponse (
        String statusCode,
        String error,
        RatingMovie movieRankToChange
){
}
