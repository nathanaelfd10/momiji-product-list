/**
 * 
 */
package com.noxfl.momijitreehouse.crawler;

import com.noxfl.momijitreehouse.model.schema.message.PageType;

/**
 * @author Fernando Nathanael
 *
 */
public interface SiteCrawlerFactory {

	public SiteCrawler getSiteCrawler(SiteContentType siteContentType);

}