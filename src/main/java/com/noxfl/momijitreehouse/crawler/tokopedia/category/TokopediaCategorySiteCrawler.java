package com.noxfl.momijitreehouse.crawler.tokopedia.category;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.noxfl.momijitreehouse.crawler.connection.ApiFetcher;
import com.noxfl.momijitreehouse.crawler.tokopedia.TokopediaSiteCrawler;
import com.noxfl.momijitreehouse.crawler.tokopedia.graphql.schema.SearchProductQuery;
import com.noxfl.momijitreehouse.model.Category;
import com.noxfl.momijitreehouse.model.Job;
import com.noxfl.momijitreehouse.model.TokopediaProduct;
import com.noxfl.momijitreehouse.util.StringUtils;
import org.json.JSONObject;

public class TokopediaCategorySiteCrawler extends TokopediaSiteCrawler {

	public TokopediaCategorySiteCrawler(ApiFetcher apiFetcher) {
		super(apiFetcher);
		// TODO Auto-generated constructor stub
	}

	public String fillStringWithPageNumber(String str, int pageNumber) {
		HashMap<String, String> params = new HashMap<>();

		params.put("page", String.valueOf(pageNumber));

		return StringUtils.fillString(str, params);
	}

	@Override
	public List<TokopediaProduct> fetchProducts(Job job, int minPage, int maxPage) {

		HashMap<String, String> headers = new HashMap<>();
		headers.put("User-Agent", "python-requests/2.28.1");
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "*/*");
		headers.put("Accept-Encoding", "gzip, deflate");
		headers.put("Accept-Language", "en-US,en;q=0.9,id-ID;q=0.8,id;q=0.7");
		headers.put("Connection", "keep-alive");
		headers.put("Origin", "https://www.tokopedia.com");

		Category category = job.getCategory();

		String formattedBreadcrumb = buildCategoryBreadcrumbAsUrlParam(category);

		for(int i=minPage; i<maxPage; i++) {

			JSONObject gqlQueryParams = new JSONObject();
			gqlQueryParams.put("params", buildVariablesParam(category, i));
			gqlQueryParams.put("adParams", buildVariablesAdParam(i));

			JSONObject payload = new JSONObject();
			payload.put("operationName", SearchProductQuery.OPERATION_NAME);
			payload.put("query", SearchProductQuery.QUERY);
			payload.put("variables", gqlQueryParams);

			try {
				apiFetcher.fetchPost(payload.toString(), headers, TOKOPEDIA_API_ENDPOINT);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		return null;
	}

	/**
	 * Builds GraphQL query payload
	 *
	 * @param category
	 * @param page
	 * @return GraphQL Query
	 */
	private String buildVariablesParam(Category category, int page) {
		int rows = 60;
		int start = 61;

		String breadcrumbAsUrlParam = buildCategoryBreadcrumbAsUrlParam(category);

		HashMap<String, String> params = new HashMap<>();
		params.put("page", String.valueOf(page));
		params.put("rows", String.valueOf(rows));
		params.put("source", "directory");
		params.put("identifier", breadcrumbAsUrlParam);
		params.put("start", String.valueOf(start));
		params.put("device", "desktop");
		params.put("safe_search", "false");
		params.put("related", "true");
		params.put("ob", "");

		return buildParams(params);
	}

	private String buildVariablesAdParam(int page) {

		HashMap<String, String> params = new HashMap<>();

		params.put("page", String.valueOf(page));
//		params.put("dep_id", "734");
		params.put("ob", "");
		params.put("ep", "product");
		params.put("item", "15");
		params.put("src", "directory");
		params.put("device", "desktop");
		params.put("user_id", "0");
		params.put("minimum_item", "15");
		params.put("start", "61");
		params.put("no_autofill_range", "5.14");

		return buildParams(params);
	}

	/**
	 * Builds category breadcrumb in the form of String that can be used in
	 * GraphQLquery params
	 *
	 * @param category
	 * @return
	 */
	public String buildCategoryBreadcrumbAsUrlParam(Category category) {
		List<String> categoryTrees = category.getCategoryBreadcrumb();
		List<String> categoriesAsParams = categoryTrees.stream().map(cat -> StringUtils.slugify(cat))
				.collect(Collectors.toList());

		return String.join("_", categoriesAsParams);
	}

}
