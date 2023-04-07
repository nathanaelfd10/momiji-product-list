/**
 * 
 */
package com.noxfl.momijitreehouse.crawler;

import com.noxfl.momijitreehouse.model.schema.message.MomijiMessage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * @author Fernando Nathanael
 *
 */
public interface SiteCrawler {

	/**
	 * Crawls products by category. Crawls until end of category if maxPage = 0.
	 *
	 * @param momijiMessage
	 * @param minPage
	 * @param maxPage
	 * @return
	 */
	public HashMap<String, Object> fetchProducts(MomijiMessage momijiMessage) throws IOException, URISyntaxException;

}
