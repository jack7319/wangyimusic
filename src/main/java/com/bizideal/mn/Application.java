package com.bizideal.mn;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sound.midi.Soundbank;
import java.io.IOException;

@ServletComponentScan
@EnableScheduling
@EnableTransactionManagement
@MapperScan("com.bizideal.mn.mapper")
@SpringBootApplication
public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        SpringApplication.run(Application.class, args);
        logger.info("application start success.");
    }

}
