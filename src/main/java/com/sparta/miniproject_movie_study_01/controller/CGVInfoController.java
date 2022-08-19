package com.sparta.miniproject_movie_study_01.controller;




import com.sparta.miniproject_movie_study_01.controller.response.ResponseDto;
import com.sparta.miniproject_movie_study_01.service.CgvInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CGVInfoController {
    private final CgvInfoService cgvInfoService;

    //영화 검색.
    @RequestMapping(value = "/api/movie/search", method = {RequestMethod.GET})
    public ResponseDto<?> movieSearch(@RequestParam String query) {

        log.info(query);

        String result = cgvInfoService.movieSearch(query);

        log.info(result);

        return cgvInfoService.moiveItem(result);

    }

    @RequestMapping(value = "/api/movie/nowrank", method = {RequestMethod.GET})
    public ResponseDto<?> movieNowRank(@RequestParam String nowDate) throws ParseException {

        log.info(nowDate);

        // 1. 날짜 표시 format
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        // 2. 오늘날짜 Data 클래스로 구하기(기준날짜가 오늘이 아니면 생략가능)
        Date today = new Date();

        // 3. 오늘날짜 format에 맞춰서 String 으로 변경(기준날짜가 오늘이 아니면 생략가능)
        String date =  formatter.format(today);

        // 4. 기준이 되는 날짜(format에 맞춘)
        Date setDate = formatter.parse(date);

        // 5. 한국 날짜 기준 Calendar 클래스 선언
        Calendar cal = new GregorianCalendar(Locale.KOREA);

        // 6. 선언된 Calendar 클래스에 기준 날짜 설정
        cal.setTime(setDate);

        // 7. 하루전으로 날짜 설정
        cal.add(Calendar.DATE, -1);

        nowDate = formatter.format(cal.getTime());

        String result = cgvInfoService.movieNowRank(nowDate);

        return cgvInfoService.moiveNowRankItem(result);
    }

    //CGV 상영 예정 영화 크롤링
    @RequestMapping(value = "/api/movie/upcomming", method = {RequestMethod.GET})
    public ResponseDto<?> movieUpComming() {
        return cgvInfoService.movieUpComming();
    }
    
    //CGV 상영 중인 영화 크롤링
    @RequestMapping(value = "/api/movie/now", method = {RequestMethod.GET})
    public ResponseDto<?> movieNow() {
        return cgvInfoService.movieNow();
    }


}
