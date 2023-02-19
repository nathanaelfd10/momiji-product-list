/**
 * 
 */
package com.noxfl.momijitreehouse.crawler;

import java.util.List;

import com.noxfl.momijitreehouse.model.Category;
import com.noxfl.momijitreehouse.model.Product;
import com.noxfl.momijitreehouse.model.Site;

/**
 * @author Fernando Nathanael
 *
 */
public interface SiteCrawler {

	/**
	 * 
	 * Crawls all products inside URL
	 * 
	 * @param pageUrl
	 * @return
	 */
	public List<Product> fetchProducts(String pageUrl);

	/**
	 * 
	 * Crawls products by category. Crawls until end of category if maxPage = 0.
	 * 
	 * @param site
	 * @param category
	 * @param maxPage
	 * @return
	 */
	public List<Product> fetchProducts(Site site, Category category, int maxPage);

	/**
	 * 
	 * Crawls products by category, and sends it to queue every time a whole page is
	 * fetched. Crawls until end of category if maxPage = 0.
	 * 
	 * @param site
	 * @param category
	 * @param maxPage
	 * @param queueName
	 * @return
	 */
	public List<Product> fetchProducts(Site site, Category category, int maxPage, String queueName);

}
