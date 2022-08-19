package com.sparta.miniproject_movie_study_01.service;

import com.google.gson.Gson;

import com.sparta.miniproject_movie_study_01.controller.response.*;
import com.sparta.miniproject_movie_study_01.domain.Comment;
import com.sparta.miniproject_movie_study_01.domain.Member;
import com.sparta.miniproject_movie_study_01.domain.MovieUpComming;
import com.sparta.miniproject_movie_study_01.repository.CommentRepository;
import com.sparta.miniproject_movie_study_01.repository.MemberRepository;
import com.sparta.miniproject_movie_study_01.repository.MovieUpCommingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CgvInfoService {

    private final MovieUpCommingRepository movieUpCommingRepository;

    private final CommentRepository commentRepository;

    private final MemberRepository memberRepository;

    private static Logger logger = LoggerFactory.getLogger(CgvInfoService.class);


    // 영화 검색.
    @Transactional
    public String movieSearch(String query) {

        log.info(query);

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", "R19VlCx5JhDdzA7fADa8");
        headers.add("X-Naver-Client-Secret", "Q0WeoiHdj0");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity =
                rest.exchange("https://openapi.naver.com/v1/search/movie.json?query=" + query , HttpMethod.GET, requestEntity, String.class);
        String response = responseEntity.getBody();

        return response;

    }

    // 영화 검색 결과 반환
    @Transactional
    public ResponseDto<?> moiveItem(String result) {

        log.info(result);

        JSONObject rjson = new JSONObject(result);
        JSONArray items = rjson.getJSONArray("items");
        List<CgvSearchResponseDto> itemDtoList = new ArrayList<>();

        for(int i = 0; i<items.length();i++){
            JSONObject itemlist = items.getJSONObject(i);
            CgvSearchResponseDto itemDto = new CgvSearchResponseDto(itemlist);
            itemDtoList.add(itemDto);

        }

        return ResponseDto.success(itemDtoList);

    }


    // 상영 중인 영화
    @Transactional
    public String movieNowRank(String nowDate) {

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity
                = rest.exchange("http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=e6d12eea02933de00f8f46edf3473465&targetDt="+nowDate, HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();

        return response;

    }

    // 상영 중인 영화 데이터 반환
    @Transactional
    public ResponseDto<?> moiveNowRankItem(String result) {

        JSONObject rjson = new JSONObject(result);

        JSONArray items = rjson.getJSONObject("boxOfficeResult")
                .getJSONArray("dailyBoxOfficeList");

        List<MovieNowRankResponseDto> itemDtoList = new ArrayList<>();

        for(int i = 0; i<items.length();i++){
            JSONObject itemlist = items.getJSONObject(i);


            MovieNowRankResponseDto itemDto = new MovieNowRankResponseDto(itemlist);

            itemDtoList.add(itemDto);

        }
        return ResponseDto.success(itemDtoList);
    }

    //
    @Transactional
    public ResponseDto<?> movieUpComming() {

        // 더미데이터임. 나중에 반드시 지우기.
        Member member1 = Member.builder()
                .id(1L)
                .email("test123@naver.com")
                .nickname("테스트닉네임")
                .password("가나다라마바사")
                .build();

        memberRepository.save(member1);

        logger.info("크롤리잉" + new Date());
        Document doc;
        String gson = "";
        List<CGVInfoDto> list = new ArrayList<CGVInfoDto>();
        List<MovieUpComming> movieUpCommingList = new ArrayList<>();

        try {

            doc = Jsoup.connect("http://www.cgv.co.kr/movies/pre-movies.aspx").get();
            /* Elements */
            Elements ranks = doc.select(".rank");
            /* logger.info("rank" + ranks); */

            Elements imgs = doc.select(".thumb-image > img");
            /* logger.info("imgs" + imgs); */

            Elements movieAges = doc.select(".ico-grade");
            /* logger.info("ico-grade" + movieAges); */

            Elements movieTitles = doc.select("div.box-contents strong.title");
            /* logger.info("titles" + movieTitles); */

            Elements movieRates = doc.select(".percent span");
            /* logger.info("percents" + movieRates); */

            Elements movieOpenDates = doc.select(".txt-info strong");
            /* logger.info("percents" + movieOpenDates); */

            Elements likes = doc.select("div.egg-gage > .percent");
            /* logger.info("counts" + likes); */
            //List<CGVInfoDto> list = new ArrayList<CGVInfoDto>();

            for(int i = 3; i < movieTitles.size(); i++) {

                //String rank = ranks.get(i).text();
                String img = imgs.get(i).attr("src");
                String movieAge = movieAges.get(i).text();
                String movieTitle = movieTitles.get(i).text();
                String movieRate = movieRates.get(i).text();
                String movieOpenDate = movieOpenDates.get(i).text();
                String like = likes.get(i).text();
                int seq = i;

                // 내 로직의 키포인트 중 하나.

                // 영화예정테이블에 해당 이미지 유알엘이 있으면
                // 저장을 안하고 유알엘이 없으면 저장함.

               if (movieUpCommingRepository.existsByImg(img)){



               } else {
                   CGVInfoDto cgvInfoDto = new CGVInfoDto(img, movieAge, movieTitle, movieRate, movieOpenDate);
                   MovieUpComming movieUpComming = new MovieUpComming(cgvInfoDto);
                   movieUpCommingRepository.save(movieUpComming);
                   movieUpCommingList.add(movieUpComming);

                   // 더미데이터임. 나중에 반드시 지우기.
                   Comment comment = Comment.builder()
                           .member(member1)
                           .movieUpComming(movieUpComming)
                           .content("테스트입니다.")
                           .build();
                   commentRepository.save(comment);
               }

            }




        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResponseDto.success(movieUpCommingList);

    }

    // 상영 중인 영화 크롤링 데이터 가져오기
    @Transactional
    public ResponseDto<?> movieNow() {

        logger.info("크롤리잉" + new Date());
        Document doc;
        String gson = "";

        List<CGVInfoDto> list = new ArrayList<CGVInfoDto>();

        try {

            doc = Jsoup.connect("http://www.cgv.co.kr/movies/?lt=1&ft=1").get();
            /* Elements */
            Elements ranks = doc.select(".rank");
            /* logger.info("rank" + ranks); */

            Elements imgs = doc.select(".thumb-image > img");
            /* logger.info("imgs" + imgs); */

            Elements movieAges = doc.select(".ico-grade");
            /* logger.info("ico-grade" + movieAges); */

            Elements movieTitles = doc.select("div.box-contents strong.title");
            /* logger.info("titles" + movieTitles); */

            Elements movieRates = doc.select(".percent span");
            /* logger.info("percents" + movieRates); */

            Elements movieOpenDates = doc.select(".txt-info strong");
            /* logger.info("percents" + movieOpenDates); */

            Elements likes = doc.select("div.egg-gage > .percent");
            /* logger.info("counts" + likes); */
//

            for(int i = 0; i < movieTitles.size(); i++) {

                //String rank = ranks.get(i).text();
                String img = imgs.get(i).attr("src");
                String movieAge = movieAges.get(i).text();
                String movieTitle = movieTitles.get(i).text();
                String movieRate = movieRates.get(i).text();
                String movieOpenDate = movieOpenDates.get(i).text();
                String like = likes.get(i).text();
                int seq = i;
                CGVInfoDto cgvInfoDto = new CGVInfoDto(img, movieAge, movieTitle, movieRate, movieOpenDate,like, seq);

                logger.info(cgvInfoDto.toString());
                list.add(cgvInfoDto);
            }
            gson = new Gson().toJson(list);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //return gson;
        return ResponseDto.success(list);

    }


}
