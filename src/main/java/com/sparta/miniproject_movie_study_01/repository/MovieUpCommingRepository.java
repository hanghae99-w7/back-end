package com.sparta.miniproject_movie_study_01.repository;

import com.sparta.miniproject_movie_study_01.domain.MovieUpComming;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieUpCommingRepository extends JpaRepository<MovieUpComming,Long> {

    boolean existsByImg(String img);

}
