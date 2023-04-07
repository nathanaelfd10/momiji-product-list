/**
 * 
 */
package com.noxfl.momijitreehouse.crawler.site.tokopedia;

import com.noxfl.momijitreehouse.crawler.SiteCrawler;
import com.noxfl.momijitreehouse.crawler.connection.ApiFetcher;
import com.noxfl.momijitreehouse.crawler.site.GenericSiteCrawler;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * @author Fernando Nathanael
 *
 */
public abstract class TokopediaSiteCrawler extends GenericSiteCrawler {

	public static final String TOKOPEDIA_API_ENDPOINT = "https://gql.tokopedia.com";

	@Value("${crawler.politeness.timeout.tokopedia}") 	// Default 1000ms
	public static final int GLOBAL_POLITENESS_TIMEOUT_TOKOPEDIA = 2000;

	protected final ApiFetcher apiFetcher;

	public TokopediaSiteCrawler(ApiFetcher apiFetcher) {
		this.apiFetcher = apiFetcher;
		System.out.printf("GLOBAL_POLITENESS_TIMEOUT_TOKOPEDIA set to: %sms%n", GLOBAL_POLITENESS_TIMEOUT_TOKOPEDIA);
	}

	/**
	 * Build GraphQL query parameters
	 * 
	 * @param params
	 * @return
	 */
	public String buildParams(HashMap<String, String> params) {
		URIBuilder uri = new URIBuilder();

		for (Entry<String, String> param : params.entrySet()) {
			uri.addParameter(param.getKey(), param.getValue());
		}

		return uri.toString();
	}

}
