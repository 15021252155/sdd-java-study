package cn.com.sdd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ThreadStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreadStudyApplication.class, args);
    }

}
