package com.sparta.miniproject_movie_study_01.repository;


import com.sparta.miniproject_movie_study_01.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
  List<Post> findAllByMember_Id(Long member_Id);

  void deletePostById(Long id);
}
