package com.example.lexorank.service;

import com.example.lexorank.entity.RatingMovie;
import com.example.lexorank.repository.RatingMovieRepository;
import com.example.lexorank.request.RankRequest;
import com.example.lexorank.request.ReBalancingResponse;
import com.example.lexorank.request.SwitchRankResponse;
import com.example.lexorank.utils.RankUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RatingService {
    private RatingMovieRepository ratingMovieRepository;

    public ReBalancingResponse ratingReBalancing() {
        //Assume maxSize = ZZZZ
        List<RatingMovie> ratingMovies = ratingMovieRepository.findAllByOrderByRankAsc();
        List<String> rankingList = RankUtils.generatedRank(1679615, ratingMovies.size());

        int index = 0;
        for (RatingMovie movie : ratingMovies) {
            movie.setRank(rankingList.get(index));
            index++;
        }
        ratingMovieRepository.saveAll(ratingMovies);
        return new ReBalancingResponse("200", null, ratingMovies);
    }

    public SwitchRankResponse switchRanking(RankRequest request) {
        RatingMovie movieToSwitch = ratingMovieRepository.findByRank(request.rank_current());
        RatingMovie beforeMovieToSwitch = ratingMovieRepository.findByRank(request.first_rank());
        RatingMovie afterMovieToSwitch = ratingMovieRepository.findByRank(request.second_rank());

        String newRank = RankUtils.findBetween(beforeMovieToSwitch.getRank(), afterMovieToSwitch.getRank());

        if (newRank.equals("Trigger ReBalancing")) {
            ratingReBalancing();
            String newRankAfterReBalancing = RankUtils.findBetween(beforeMovieToSwitch.getRank(), afterMovieToSwitch.getRank());
            movieToSwitch.setRank(newRankAfterReBalancing);
        }else {
            movieToSwitch.setRank(newRank);
        }
        ratingMovieRepository.save(movieToSwitch);

        return new SwitchRankResponse("200", null, movieToSwitch);
    }

}
