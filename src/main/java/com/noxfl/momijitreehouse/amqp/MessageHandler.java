package com.noxfl.momijitreehouse.amqp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noxfl.momijitreehouse.crawler.SiteCrawlerFactory;
import com.noxfl.momijitreehouse.model.Job;
import com.noxfl.momijitreehouse.model.MomijiMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Fernando Nathanael
 *
 */
@Component
public class MessageHandler {

    private SiteCrawlerFactory siteCrawlerFactory;

    @Autowired
    public void setSiteCrawlerFactory(SiteCrawlerFactory siteCrawlerFactory) {
        this.siteCrawlerFactory = siteCrawlerFactory;
    }

    public void handle(String message) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        MomijiMessage momijiMessage = objectMapper.readValue(message, MomijiMessage.class);

        Job job = momijiMessage.getJob();

        System.out.println("Received job: " + momijiMessage.getJob().getName());

        siteCrawlerFactory.getSiteCrawler(momijiMessage.getJob().getPageType()).fetchProducts(momijiMessage);
    }


}
