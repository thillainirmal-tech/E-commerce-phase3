package com.example.minor_project_01.config;

import com.example.minor_project_01.repo.OrderRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MoveOrdersDraftToExpireTask {

    private static Logger LOGGER = LoggerFactory.getLogger(MoveOrdersDraftToExpireTask.class);

    @Autowired
    private OrderRepo orderRepo;

    //@Scheduled(fixedDelay = 3000)
    public void markOrdersExpire(){
        LOGGER.info("Starting markOrdersExpire");
        /*
        Fetch ORDERS with status as DRAFT and lastUpdated Time was 5 days ago.
        Mark these order EXPIRE
         */

        LOGGER.info("Exiting markOrdersExpire");
    }
}
