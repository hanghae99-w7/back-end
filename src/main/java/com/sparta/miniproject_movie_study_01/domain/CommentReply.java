package com.sparta.miniproject_movie_study_01.domain;




import com.sparta.miniproject_movie_study_01.controller.request.CommentReplyRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert // 디폴트가 null일때 나머지만 insert
@Entity
public class CommentReply extends Timestamped{

    // 기본키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 외래키
    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    // 외래키
    // 디폴트로 pk값을 가져온다.
    @JoinColumn(name = "post_id")
    @ColumnDefault("0") //default 0
    // fetch = FetchType.LAZY 지연참조
    // 다른내용을 항상 참조할 필요는 없다.
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @JoinColumn(name = "movieUpComming_id")
    @ColumnDefault("0") //default 0
    @ManyToOne(fetch = FetchType.LAZY)
    private MovieUpComming movieUpComming;

    @JoinColumn(name = "comment_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @Column(nullable = false)
    private String content;

    @Column(name = "likes_count")
    @ColumnDefault("0") //default 0
    private Integer likes_count;

    public void updatelike_count(Integer postlike_count){
        this.likes_count = postlike_count;

    }
    public void update(CommentReplyRequestDto commentReplyRequestDto) {
        this.content = commentReplyRequestDto.getContent();
    }

    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }


}
