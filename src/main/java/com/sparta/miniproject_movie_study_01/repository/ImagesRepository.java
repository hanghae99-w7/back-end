package com.sparta.miniproject_movie_study_01.repository;



import com.sparta.miniproject_movie_study_01.domain.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<Images, Long> {
}
