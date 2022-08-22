package com.sparta.miniproject_movie_study_01.controller;

import com.sparta.miniproject_movie_study_01.controller.request.PostRequestDto;
import com.sparta.miniproject_movie_study_01.controller.response.ResponseDto;
import com.sparta.miniproject_movie_study_01.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class PostController {

  private final PostService postService;

  // 영화 상세 페이지 조회
  @RequestMapping(value = "/api/post/movieupcomming/{id}", method = RequestMethod.GET)
  public ResponseDto<?> getmovieUpComming(@PathVariable Long id) {
    return postService.getmovieUpComming(id);
  }

  // 메인 페이지 전체 조회
  @RequestMapping(value = "/api/post/movieupcomming", method = RequestMethod.GET)
  public ResponseDto<?> getAllmovieUpComming() {
    return postService.getAllmovieUpComming();
  }


}
