package com.noxfl.momijitreehouse.crawler.site.tokopedia.category;

import com.github.slugify.Slugify;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.noxfl.momijitreehouse.amqp.MessageSender;
import com.noxfl.momijitreehouse.crawler.connection.ApiFetcher;
import com.noxfl.momijitreehouse.crawler.site.tokopedia.TokopediaSiteCrawler;
import com.noxfl.momijitreehouse.crawler.site.tokopedia.graphql.schema.SearchProductQuery;
import com.noxfl.momijitreehouse.model.schema.message.*;
import com.noxfl.momijitreehouse.util.StringUtils;
import com.noxfl.momijitreehouse.util.UriUtils;
import net.minidev.json.JSONStyle;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Fernando Nathanael
 *
 */
@Component
public class TokopediaCategorySiteCrawler extends TokopediaSiteCrawler {

    final HashMap<String, String> headers = new HashMap<>();

    private MessageSender messageSender;

    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public TokopediaCategorySiteCrawler(ApiFetcher apiFetcher) {
        super(apiFetcher);

        headers.put("User-Agent", "python-requests/2.28.1");
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "*/*");
        headers.put("Accept-Encoding", "gzip, deflate");
        headers.put("Accept-Language", "en-US,en;q=0.9,id-ID;q=0.8,id;q=0.7");
        headers.put("Connection", "keep-alive");
        headers.put("Origin", "https://www.tokopedia.com");
    }

    //TODO: Reusable function, move somewhere else
    private List<String> splitProductCards(String contentName, String jsonString, String splitPath) {
        final ContentType contentType = ContentType.JSON;

        net.minidev.json.JSONObject[] contents =
                JsonPath.using(Configuration.defaultConfiguration())
                        .parse(jsonString)
                        .read(splitPath, net.minidev.json.JSONObject[].class);

        return Arrays.stream(contents)
                .map(json -> json.toJSONString(JSONStyle.MAX_COMPRESS))
                .toList();
    }

    @Override
    public HashMap<String, Object> fetchProducts(MomijiMessage momijiMessage) throws IOException, URISyntaxException {

        Job job = momijiMessage.getJob();

        Category category = job.getCategory();

        int minPage = job.getMinPage();
        int maxPage = job.getMaxPage();

        for (int i = minPage; i <= maxPage; i++) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            JSONObject gqlQueryParams = new JSONObject();
            gqlQueryParams.put("params", buildVariablesParam(job.getCategory().getUrl(), i));
//            gqlQueryParams.put("adParams", buildVariablesAdParam(i));

            JSONObject payload = new JSONObject();
            payload.put("operationName", SearchProductQuery.OPERATION_NAME);
            payload.put("query", SearchProductQuery.QUERY);
            payload.put("variables", gqlQueryParams);

            try {
                String response = apiFetcher.fetchPost(payload.toString(), headers, TOKOPEDIA_API_ENDPOINT).toString();
                String contentName = "ProductContent";
                ContentType contentType = ContentType.JSON;

                List<String> productCards = splitProductCards(contentName, response, "$.data.CategoryProducts.data[*]");

                for(String card : productCards) {

                    List<Content> contents = new ArrayList<>();

                    String url = JsonPath.using(Configuration.defaultConfiguration())
                            .parse(card)
                            .read("$.url");

                    url = UriUtils.clearParameter(url).toString();

                    momijiMessage.getJob().getContent().setUrl(url);
                    momijiMessage.getJob().getContent().setProduct(card);

                    messageSender.send(new JSONObject(momijiMessage).toString());

                }

            } catch (IOException | URISyntaxException e){
                throw new RuntimeException(e);
            }

        }

        return null;
    }

    private String fillStringWithPageNumber(String str, int pageNumber) {
        HashMap<String, String> params = new HashMap<>();

        params.put("page", String.valueOf(pageNumber));

        return StringUtils.fillString(str, params);
    }

    /**
     * Builds GraphQL query payload
     *
     * @param category
     * @param page
     * @return GraphQL Query
     */
    private String buildVariablesParam(String url, int page) throws IOException, URISyntaxException {
        int rows = 60;
        int start = 61;

        String categoryIdentifier = buildCategoryIdentifier(url);
        int categoryId = getCategoryId(categoryIdentifier);

        HashMap<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("rows", String.valueOf(rows));
        params.put("source", "directory");
        params.put("sc", String.valueOf(categoryId));
        params.put("identifier", categoryIdentifier);
        params.put("start", String.valueOf(start));
        params.put("st", "product");
        params.put("device", "desktop");
        params.put("safe_search", "false");
        params.put("related", "true");
        params.put("ob", "");

        String output = buildParams(params);

        return output.startsWith("?") ? output.substring(1, output.length()) : output;
    }

    private int getCategoryId(String categoryIdentifier) throws IOException {

        JSONObject variables = new JSONObject();
        variables.put("identifier", categoryIdentifier);
        variables.put("intermediary", true);
        variables.put("safeSearch", true);

        JSONObject payload = new JSONObject();
        payload.put("operationName", "CategoryDetailQuery");
        payload.put("variables", variables);
        payload.put("query", """
                query CategoryDetailQuery($identifier: String!, $intermediary: Boolean!, $safeSearch: Boolean!) {
                 CategoryDetailQuery: CategoryDetailQueryV3(identifier: $identifier, intermediary: $intermediary, safeSearch: $safeSearch) {
                 header {
                 code
                 serverProcessTime
                 message
                 __typename
                 }
                 data {
                 id
                 name
                 useDiscoPage
                 discoIdentifier
                 url
                 redirectionURL
                 child {
                 id
                 name
                 url
                 thumbnailImage
                 isAdult
                 applinks
                 __typename
                 }
                 __typename
                 }
                 __typename
                 }
                }
                """);
        String response = apiFetcher.fetchPost(payload.toString(), headers, TOKOPEDIA_API_ENDPOINT).toString();

        return JsonPath.using(Configuration.defaultConfiguration()).parse(response).read("$.data.CategoryDetailQuery.data.id", Integer.class);
    }

    private String buildVariablesAdParam(int page) {

        HashMap<String, String> params = new HashMap<>();

        params.put("page", String.valueOf(page));
		params.put("dep_id", "734");
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

    private String buildCategoryIdentifier(String url) throws URISyntaxException {
        List<String> paths = new URIBuilder(url).clearParameters().getPathSegments();
        if(paths.get(0).equalsIgnoreCase("p")) {
            paths.remove(0);
        }

        return String.join("_", paths);
    }

    private String buildCategoryIdentifier(Category category) {

        Slugify slug = Slugify.builder().lowerCase(true).build();

        List<String> categoriesAsParams = category.getCategoryBreadcrumb().stream()
                .map(slug::slugify)
                .collect(Collectors.toList());

        return String.join("_", categoriesAsParams);
    }

}
