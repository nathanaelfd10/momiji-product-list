/**
 * 
 */
package com.noxfl.momijitreehouse.crawler.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import com.noxfl.momijitreehouse.crawler.SiteCrawler;
import com.noxfl.momijitreehouse.crawler.connection.ApiFetcher;
import com.noxfl.momijitreehouse.model.Category;
import com.noxfl.momijitreehouse.model.Product;
import com.noxfl.momijitreehouse.model.Site;
import com.noxfl.momijitreehouse.model.TokopediaProduct;
import com.noxfl.momijitreehouse.util.StringUtils;

/**
 * @author Fernando Nathanael
 *
 */
public class TokopediaSiteCrawler implements SiteCrawler<TokopediaProduct> {

	// Default 1000ms
	@Value("${crawler.politeness.timeout.tokopedia}")
	public static final int GLOBAL_POLITENESS_TIMEOUT_TOKOPEDIA = 1000;

	private ApiFetcher apiFetcher;

	public TokopediaSiteCrawler(ApiFetcher apiFetcher) {
		this.apiFetcher = apiFetcher;
	}

	/**
	 * Build GraphQL query parameters
	 * 
	 * @param params
	 * @return
	 */
	public String buildQueryParams(HashMap<String, String> params) {
		URIBuilder uri = new URIBuilder();

		for (Entry<String, String> param : params.entrySet()) {
			uri.addParameter(param.getKey(), param.getValue());
		}

		String uriAsString = uri.toString();

		return uriAsString;
	}

	/**
	 * Builds category breadcrumb in the form of String that can be used in
	 * GraphQLquery params
	 * 
	 * @param category
	 * @return
	 */
	public String buildCategoryAsParam(Category category) {
		List<String> categoryTrees = category.getCategoryBreadcrumb();
		List<String> categoriesAsParams = categoryTrees.stream().map(cat -> StringUtils.slugify(cat)).toList();
		String categoriesAsParam = String.join("_", categoriesAsParams);

		return categoriesAsParam;
	}

	/**
	 * Builds GraphQL query payload
	 * 
	 * @param category
	 * @param page
	 * @return
	 */
	private String createQuery(Category category, int page) {
		int rows = 60;
		int start = 61;

		String categoriesAsParam = buildCategoryAsParam(category);

		HashMap<String, String> params = new HashMap<>();

		params.put("page", String.valueOf(page));
		params.put("rows", String.valueOf(rows));
		params.put("source", "directory");
		params.put("identifier", categoriesAsParam);
		params.put("start", String.valueOf(start));
		params.put("device", "desktop");
		params.put("safe_search", "false");
		params.put("related", "true");
		params.put("ob", "");

		String paramsExample = "page=%s&ob=&identifier=buku_arsitektur-desain_buku-bangunan&sc=784&user_id=15221553&rows=60&start=61&source=directory&device=desktop&page=2&related=true&st=product&safe_search=false";

		String queryParams = params.toString();

		// TODO Under construction

		return queryParams;
	}

	public List<Product> extractProducts() {

		// TODO Under construction

		return null;
	}

	public List<TokopediaProduct> fetchProducts(String pageUrl) {

		// TODO Auto-generated method stub

		HashMap<String, String> headers = new HashMap<>();
		headers.put("User-Agent", "python-requests/2.28.1");
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "*/*");
		headers.put("Accept-Encoding", "gzip, deflate");
		headers.put("Accept-Language", "en-US,en;q=0.9,id-ID;q=0.8,id;q=0.7");
		headers.put("Connection", "keep-alive");
		headers.put("Origin", "https://www.tokopedia.com");

		JSONObject response;

		try {
			response = apiFetcher.fetchPost("json here", headers, "https://gql.tokopedia.com");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public List<TokopediaProduct> fetchProducts(Site site, Category category, int maxPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TokopediaProduct> fetchProducts(Site site, Category category, int maxPage, String queueName) {
		// TODO Auto-generated method stub
		return null;
	}

}
