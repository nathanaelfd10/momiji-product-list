/**
 * 
 */
package com.noxfl.momijitreehouse.crawler.connection;

import com.noxfl.momijitreehouse.crawler.SiteCrawler;

/**
 * @author Fernando Nathanael
 *
 */
public interface SiteCrawlerFactory {

	public SiteCrawler handleUrl(String url);

	public SiteCrawler getSiteCrawler(String siteCrawlerName);

}
