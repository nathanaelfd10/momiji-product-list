/**
 * 
 */
package com.noxfl.momijitreehouse.crawler.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.noxfl.momijitreehouse.crawler.SiteCrawler;
import com.noxfl.momijitreehouse.crawler.connection.ApiFetcher;
import com.noxfl.momijitreehouse.model.Category;
import com.noxfl.momijitreehouse.model.Product;
import com.noxfl.momijitreehouse.model.Site;

/**
 * @author Fernando Nathanael
 *
 */
public class TokopediaSiteCrawlerImpl implements SiteCrawler {

	private ApiFetcher apiFetcher;

	public TokopediaSiteCrawlerImpl(ApiFetcher apiFetcher) {
		this.apiFetcher = apiFetcher;
	}

	public List<Product> fetchProducts(String pageUrl) {

		// TODO Auto-generated method stub

		HashMap<String, String> headers = new HashMap<>();
		headers.put("User-Agent", "python-requests/2.28.1");
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "*/*");
		headers.put("Accept-Encoding", "gzip, deflate");
		headers.put("Accept-Language", "en-US,en;q=0.9,id-ID;q=0.8,id;q=0.7");
		headers.put("Connection", "keep-alive");
		headers.put("Origin", "https://www.tokopedia.com");

		try {
			apiFetcher.fetchPost("json here", headers, "https://gql.tokopedia.com");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public List<Product> fetchProducts(Site site, Category category, int maxPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> fetchProducts(Site site, Category category, int maxPage, String queueName) {
		// TODO Auto-generated method stub
		return null;
	}

}
