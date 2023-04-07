package com.noxfl.momijitreehouse.crawler.site.bukalapak;

import com.noxfl.momijitreehouse.crawler.SiteCrawler;
import com.noxfl.momijitreehouse.crawler.site.GenericSiteCrawler;

public abstract class BukalapakSiteCrawler extends GenericSiteCrawler {

    public BukalapakSiteCrawler() {
        this.setPaginationStart(1);
    }

}
