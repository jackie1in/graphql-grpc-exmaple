package com.bd.post;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author <a href="mailto:hilin2333@gmail.com">created by silencecorner 2019/7/10 2:58 PM</a>
 */
@EnableJpaAuditing
@SpringBootApplication
public class PostApiApplication {
    public static void main(String[] args){
        SpringApplication.run(PostApiApplication.class,args);
    }
}
