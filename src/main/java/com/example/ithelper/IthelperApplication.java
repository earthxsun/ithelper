package com.example.ithelper;

import com.example.ithelper.common.utils.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class IthelperApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(IthelperApplication.class, args);
        SpringContextUtil.setApplicationContext(context);
    }

}
