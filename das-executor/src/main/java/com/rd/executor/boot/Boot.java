package com.rd.executor.boot;

import com.rd.executor.rule.RuleExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

public class Boot {

    private Boot() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Boot.class);

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("das-executor-context.xml");
        LOGGER.info("#############################################################################");
        LOGGER.info("#############################################################################");
        LOGGER.info("####                       DAS Started                                  #####");
        LOGGER.info("#############################################################################");
        LOGGER.info("#############################################################################");
//        HiveTableImportingExecutor hiveTableImportingExecutor = (HiveTableImportingExecutor) context.getBean("hiveTableImportingExecutor");
//        hiveTableImportingExecutor.execute();

        LOGGER.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        LOGGER.info("++++++++++++++++++++ Available report types +++++++++++++++++++++++++++++++++");
        LOGGER.info("-------- 1. Recommended products by visited products ------------------------");
        LOGGER.info("-------- 2. Recommended products by search criteria -------------------------");
        LOGGER.info("--------  Please specify your report type by entering the number ? ---------- ");
        String input = sc.nextLine();

        RuleExecutor ruleExecutor = (RuleExecutor) context.getBean("ruleExecutor");
        ruleExecutor.executeRule(input);

    }

}
