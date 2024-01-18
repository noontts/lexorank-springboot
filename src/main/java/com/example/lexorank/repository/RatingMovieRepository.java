package com.example.lexorank.repository;

import com.example.lexorank.entity.RatingMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingMovieRepository extends JpaRepository<RatingMovie,Long> {
    RatingMovie findByRank(String rank);
    List<RatingMovie> findAllByOrderByRankAsc();
}
