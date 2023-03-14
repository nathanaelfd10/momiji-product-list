/**
 * 
 */
package com.noxfl.momijitreehouse.crawler.impl;

import com.noxfl.momijitreehouse.crawler.tokopedia.category.TokopediaCategorySiteCrawler;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;

import com.noxfl.momijitreehouse.crawler.PageType;
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

	@Override
	public SiteCrawler getSiteCrawler(PageType pageType) {

		switch (pageType) {
			case TOKOPEDIA_CATEGORY -> { return tokopediaCategorySiteCrawler; }
			default -> throw new IllegalArgumentException("No site crawler for PageType: " + pageType.toString());
		}

	}

}
