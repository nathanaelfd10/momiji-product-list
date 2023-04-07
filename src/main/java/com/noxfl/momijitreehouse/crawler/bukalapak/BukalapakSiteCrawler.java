package com.noxfl.momijitreehouse.crawler.bukalapak;

import com.noxfl.momijitreehouse.crawler.SiteCrawler;
import com.noxfl.momijitreehouse.crawler.bukalapak.category.BukalapakCategorySiteCrawler;
import com.noxfl.momijitreehouse.model.schema.message.MomijiMessage;
import org.w3c.dom.Document;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

public abstract class BukalapakSiteCrawler implements SiteCrawler {

    private int paginationStart;

    private int page;

    private String pageUrl;

    public BukalapakSiteCrawler() {
        this.paginationStart = 1;
        this.page = this.paginationStart;
    }

    protected void nextPage() {
        this.page++;
    }

    protected void previousPage() {
        this.page--;
    }

    protected int getCurrentPage() {
        return page;
    }


    protected void setPaginationStart(int paginationStart){
        this.paginationStart = paginationStart;
    }



}
