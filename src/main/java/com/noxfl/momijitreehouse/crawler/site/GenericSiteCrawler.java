package com.noxfl.momijitreehouse.crawler.site;

import com.noxfl.momijitreehouse.crawler.SiteCrawler;

public abstract class GenericSiteCrawler implements SiteCrawler {

    private int paginationStart;

    private int page;

    private String pageUrl;

    public GenericSiteCrawler() {
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
