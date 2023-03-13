/**
 * 
 */
package com.noxfl.momijitreehouse.crawler;

import java.util.List;

import com.noxfl.momijitreehouse.model.Category;
import com.noxfl.momijitreehouse.model.Job;
import com.noxfl.momijitreehouse.model.Site;

/**
 * @author Fernando Nathanael
 *
 */
public interface SiteCrawler<ResultObj> {

	/**
	 *
	 * Crawls products by category. Crawls until end of category if maxPage = 0.
	 *
	 * @param site
	 * @param category
	 * @param maxPage
	 * @return
	 */
	public List<ResultObj> fetchProducts(Job job, int minPage, int maxPage);

}
