package com.noxfl.momijitreehouse;

import com.noxfl.momijitreehouse.crawler.SiteCrawlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Fernando Nathanael
 *
 */
public class TreeHouseRunner implements CommandLineRunner {

    private ConfigurableApplicationContext context;

    public void setContext(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @Autowired
    private SiteCrawlerFactory siteCrawlerFactory;

    public void setSiteCrawlerFactory(SiteCrawlerFactory siteCrawlerFactory) {
        this.siteCrawlerFactory = siteCrawlerFactory;
    }

    @Override
    public void run(String... args) throws Exception {
        int sleep = 900000;
        String message = String.format("Running for %s", sleep);
        System.out.println(message);
        Thread.sleep(sleep);
        System.out.println(message);
    }
}
