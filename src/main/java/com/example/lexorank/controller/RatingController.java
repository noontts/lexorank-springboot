package com.example.lexorank.controller;

import com.example.lexorank.request.RankRequest;
import com.example.lexorank.request.ReBalancingResponse;
import com.example.lexorank.request.SwitchRankResponse;
import com.example.lexorank.service.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/rating")
@AllArgsConstructor
public class RatingController {

    private RatingService ratingService;

    @PostMapping
    private ReBalancingResponse reBalancing() {
        ReBalancingResponse response;
        try {
            response = ratingService.ratingReBalancing();
        } catch (Exception exception) {
            return new ReBalancingResponse("500", exception.getMessage(), null);
        }
        return response;
    }

    @PostMapping("/move-rank")
    private SwitchRankResponse moveRank(@RequestBody RankRequest request) {
        SwitchRankResponse response;
        try {
            response = ratingService.switchRanking(request);
        } catch (Exception exception) {
            return new SwitchRankResponse("500", exception.getMessage(), null);
        }
        return response;
    }
}
