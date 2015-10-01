package com.rd.executor.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Boot {

    private Boot() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Boot.class);

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("das-executor-context.xml");
        LOGGER.info("#############################################################################");
        LOGGER.info("#############################################################################");
        LOGGER.info("####                       DAS Started                                  #####");
        LOGGER.info("#############################################################################");
        LOGGER.info("#############################################################################");


    }

}
