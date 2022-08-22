package com.sparta.miniproject_movie_study_01.controller;




import com.sparta.miniproject_movie_study_01.controller.response.ResponseDto;
import com.sparta.miniproject_movie_study_01.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class LikesController {

    private final LikesService likesService;

    // 영화 게시글 좋아요 추가/제거
    @RequestMapping(value = "/api/auth/movieupcomming/addlikes/{id}", method = RequestMethod.POST)
    public ResponseDto<?> addLikesMovieUpComming(@PathVariable Long id,
                                       HttpServletRequest request) {
        return likesService.addLikesMovieUpComming(id, request);
    }


    //comment 좋아요 추가
    @RequestMapping(value = "/api/auth/addlikescomment/{id}", method = RequestMethod.POST)
    public ResponseDto<?> addLikesComment(@PathVariable Long id,
                                       HttpServletRequest request) {
        return likesService.addLikesComment(id, request);
    }

    //comment 좋아요 삭제
    @RequestMapping(value = "/api/auth/deletelikescomment/{id}", method = RequestMethod.DELETE)
    public ResponseDto<?> deleteLikesComment(@PathVariable Long id,
                                          HttpServletRequest request) {
        return likesService.deleteLikesComment(id, request);
    }

}
