/**
 * 
 */
package com.noxfl.momijitreehouse.crawler;

/**
 * @author Fernando Nathanael
 *
 */
public interface SiteCrawlerFactory {

	public SiteCrawler handleUrl(String url);

	public SiteCrawler getSiteCrawler(String siteCrawlerName);

}