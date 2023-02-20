/**
 * 
 */
package com.noxfl.momijitreehouse.crawler.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.noxfl.momijitreehouse.crawler.SiteCrawler;
import com.noxfl.momijitreehouse.crawler.SiteCrawlerFactory;

/**
 * @author Fernando Nathanael
 *
 */
public class SiteCrawlerFactoryImpl implements SiteCrawlerFactory {

	private TokopediaSiteCrawler tokopediaSiteCrawler;

	@Autowired
	public void setTokopediaSiteCrawler(TokopediaSiteCrawler tokopediaSiteCrawler) {
		this.tokopediaSiteCrawler = tokopediaSiteCrawler;
	}

	@Override
	public SiteCrawler handleUrl(String url) {

		// How efficient would this method be if theoretically there were 50+ site
		// workers?

		if (url.startsWith("https://www.tokopedia.com")) {
			return getSiteCrawler("tokopedia");
		} else {
			throw new IllegalArgumentException("No site crawler found for URL: " + url);
		}

	}

	@Override
	public SiteCrawler getSiteCrawler(String siteCrawlerName) {

		switch (siteCrawlerName.toLowerCase()) {

		case "tokopedia":
			return tokopediaSiteCrawler;
		default:
			throw new IllegalArgumentException("No site crawler for: " + siteCrawlerName);

		}

	}

}
