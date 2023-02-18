/**
 * 
 */
package com.noxfl.momijitreehouse.crawler;

import java.util.List;

import com.noxfl.momijitreehouse.crawler.configuration.CrawlConfiguration;
import com.noxfl.momijitreehouse.model.Product;

/**
 * @author Fernando Nathanael
 *
 */
public interface Crawler {

	public List<Product> fetchProducts(CrawlConfiguration crawlConfiguration);

	public void crawl(CrawlConfiguration crawlConfiguration);

}