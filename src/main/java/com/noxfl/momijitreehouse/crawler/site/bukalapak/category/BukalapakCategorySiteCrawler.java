package com.noxfl.momijitreehouse.crawler.site.bukalapak.category;

import com.noxfl.momijitreehouse.amqp.MessageSender;
import com.noxfl.momijitreehouse.crawler.site.bukalapak.BukalapakSiteCrawler;
import com.noxfl.momijitreehouse.model.schema.message.MomijiMessage;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

@Component
public class BukalapakCategorySiteCrawler extends BukalapakSiteCrawler {

    private final MessageSender messageSender;

    public BukalapakCategorySiteCrawler(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    private void iterate(MomijiMessage momijiMessage) {



    }

    @Override
    public HashMap<String, Object> fetchProducts(MomijiMessage momijiMessage) throws IOException, URISyntaxException {

        String url = momijiMessage.getJob().getCategory().getUrl();

        url = new URIBuilder(url).addParameter("page", String.valueOf(getCurrentPage()))
                .build()
                .toString();

        System.out.println("Crawling page: " + url);

        Document document = Jsoup.connect(url).get();
        List<Element> elements = document.select(".te-product-card");

        for(var element : elements) {

            String productUrl = element.select(".bl-thumbnail__slider > div > a").attr("href");

            momijiMessage.getJob().getContent().setUrl(productUrl);
            momijiMessage.getJob().getContent().setProduct(element.toString());

            messageSender.send(momijiMessage);
        }

        this.nextPage();

        if(getCurrentPage() <= momijiMessage.getJob().getMaxPage())
            fetchProducts(momijiMessage);

        return null;

//        return fetchProducts(momijiMessage);
    }
}
