/**
 * 
 */
package com.noxfl.momijitreehouse.crawler.impl;

import com.noxfl.momijitreehouse.crawler.SiteContentType;
import com.noxfl.momijitreehouse.crawler.site.bukalapak.category.BukalapakCategorySiteCrawler;
import com.noxfl.momijitreehouse.crawler.site.tokopedia.category.TokopediaCategorySiteCrawler;
import org.springframework.beans.factory.annotation.Autowired;

import com.noxfl.momijitreehouse.crawler.SiteCrawler;
import com.noxfl.momijitreehouse.crawler.SiteCrawlerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Fernando Nathanael
 *
 */
@Component
public class SiteCrawlerFactoryImpl implements SiteCrawlerFactory {

	private TokopediaCategorySiteCrawler tokopediaCategorySiteCrawler;

	@Autowired
	public void setTokopediaSiteCrawler(TokopediaCategorySiteCrawler tokopediaCategorySiteCrawler) {
		this.tokopediaCategorySiteCrawler = tokopediaCategorySiteCrawler;
	}

	private BukalapakCategorySiteCrawler bukalapakCategorySiteCrawler;

	@Autowired
	public void setBukalapakCategorySiteCrawler(BukalapakCategorySiteCrawler bukalapakCategorySiteCrawler) {
		this.bukalapakCategorySiteCrawler = bukalapakCategorySiteCrawler;
	}

	@Override
	public SiteCrawler getSiteCrawler(SiteContentType siteContentType) {

		switch (siteContentType) {
			case TOKOPEDIA_CATEGORY_JSON -> { return tokopediaCategorySiteCrawler; }
			case BUKALAPAK_CATEGORY_HTML -> { return bukalapakCategorySiteCrawler; }
			default -> throw new IllegalArgumentException("No site crawler for PageType: " + siteContentType);
		}

	}

}
