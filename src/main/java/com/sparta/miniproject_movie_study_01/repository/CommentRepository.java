package com.sparta.miniproject_movie_study_01.repository;


import com.sparta.miniproject_movie_study_01.domain.Comment;
import com.sparta.miniproject_movie_study_01.domain.MovieUpComming;
import com.sparta.miniproject_movie_study_01.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findAllByPost(Post post);
  List<Comment> findAllByMovieUpComming(MovieUpComming movieUpComming);
  List<Comment> findAllByMember_Id(Long member_Id);

  Integer countAllByMovieUpComming(MovieUpComming movieUpComming);


    //List<Comment> findByPost_Id(Long post_id);
}
