package com.sparta.miniproject_movie_study_01.repository;



import com.sparta.miniproject_movie_study_01.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes,Long> {

    Integer countAllByPost(Post post);
    Integer countAllByMovieUpComming(MovieUpComming movieUpComming);
    Integer countAllByComment(Comment comment);
    Integer countAllByCommentReply(CommentReply commentReply);

    void deleteByMember_IdAndPost_Id(Long memberId,Long post_id);
    void deleteByMember_IdAndMovieUpComming_Id(Long memberId,Long movieupcomming_id);
    void deleteByPost(Post post);
    void deleteByMember_IdAndComment_Id(Long memberId,Long comment_id);
    void deleteByMember_IdAndCommentReply_Id(Long memberId,Long commentReply_id);
    boolean existsByMember_IdAndPost_Id(Long member_id,Long post_id);
    boolean existsByMember_IdAndMovieUpComming_id(Long member_id,Long movieupcomming_id);
    boolean existsByMember_IdAndComment_Id(Long member_id,Long comment_id);
    boolean existsByMember_IdAndCommentReply_Id(Long member_id,Long commentReply_id);


}
