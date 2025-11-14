package com.genealogy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@SpringBootApplication
public class GenealogyApplication {
    public static void main(String[] args) {
        SpringApplication.run(GenealogyApplication.class, args);
        log.info("启动成功");
    }
}