package com.sparta.miniproject_movie_study_01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableScheduling  // 스프링 부트에서 스케줄러가 작동하게 합니다.
@EnableJpaAuditing
@SpringBootApplication
public class MiniprojectMovieStudy01Application {


	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.properties,"
			+ "classpath:aws.yml";


//	public static void main(String[] args) {
//		SpringApplication.run(MiniprojectMovieStudy01Application.class, args);
//	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(MiniprojectMovieStudy01Application.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}

}
