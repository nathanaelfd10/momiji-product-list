package com.noxfl.momijitreehouse.crawler.tokopedia.category;

import com.github.slugify.Slugify;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.noxfl.momijitreehouse.amqp.MessageSender;
import com.noxfl.momijitreehouse.crawler.ContentType;
import com.noxfl.momijitreehouse.crawler.connection.ApiFetcher;
import com.noxfl.momijitreehouse.crawler.tokopedia.TokopediaSiteCrawler;
import com.noxfl.momijitreehouse.crawler.tokopedia.graphql.schema.SearchProductQuery;
import com.noxfl.momijitreehouse.model.*;
import com.noxfl.momijitreehouse.util.StringUtils;
import net.minidev.json.JSONStyle;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TokopediaCategorySiteCrawler extends TokopediaSiteCrawler {

    private MessageSender messageSender;

    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public TokopediaCategorySiteCrawler(ApiFetcher apiFetcher) {
        super(apiFetcher);
        // TODO Auto-generated constructor stub
    }

    private String fillStringWithPageNumber(String str, int pageNumber) {
        HashMap<String, String> params = new HashMap<>();

        params.put("page", String.valueOf(pageNumber));

        return StringUtils.fillString(str, params);
    }

    //TODO: Reusable function, move somewhere else
    private List<Content> splitJsonContent(String contentName, String jsonString, String splitPath) {
        final ContentType contentType = ContentType.JSON;

        net.minidev.json.JSONObject[] contents =
                JsonPath.using(Configuration.defaultConfiguration())
                        .parse(jsonString)
                        .read(splitPath, net.minidev.json.JSONObject[].class);

        return Arrays.stream(contents)
                .map(json -> new Content(contentName, contentType, json.toJSONString(JSONStyle.NO_COMPRESS)))
                .toList();
    }
    @Override
    public List<TokopediaProduct> fetchProducts(MomijiMessage momijiMessage, int minPage, int maxPage) {

        Job job = momijiMessage.getJob();

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

        for (int i = minPage; i < maxPage; i++) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            JSONObject gqlQueryParams = new JSONObject();
            gqlQueryParams.put("params", buildVariablesParam(category, i));
//            gqlQueryParams.put("adParams", buildVariablesAdParam(i));

            JSONObject payload = new JSONObject();
            payload.put("operationName", SearchProductQuery.OPERATION_NAME);
            payload.put("query", SearchProductQuery.QUERY);
            payload.put("variables", gqlQueryParams);

            System.out.println(payload);

            try {
                String response = apiFetcher.fetchPost(payload.toString(), headers, TOKOPEDIA_API_ENDPOINT).toString();
                String contentName = "TokopediaProductCard";
                ContentType contentType = ContentType.JSON;

                List<Content> products = splitJsonContent(contentName, response, "$.data.CategoryProducts.data[*]");

                for(var product : products) {

                    List<Content> contents = new ArrayList<>();
                    contents.add(new Content("url", contentType, "https://www.tokopedia.com"));
                    contents.add(new Content(contentName, contentType, product.getContent()));

                    momijiMessage.getJob().setContents(contents);

                    messageSender.send(new JSONObject(momijiMessage).toString());

                }

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
        params.put("sc", "784");
        params.put("identifier", breadcrumbAsUrlParam);
        params.put("start", String.valueOf(start));
        params.put("st", "product");
        params.put("device", "desktop");
        params.put("safe_search", "false");
        params.put("related", "true");
        params.put("ob", "");

        String output = buildParams(params);

        return output.startsWith("?") ? output.substring(1, output.length()) : output;
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

    /**
     * Builds category breadcrumb in the form of String that can be used in
     * GraphQLquery params
     *
     * @param category
     * @return
     */
    public String buildCategoryBreadcrumbAsUrlParam(Category category) {

        Slugify slug = Slugify.builder().lowerCase(true).build();

        List<String> categoriesAsParams = category.getCategoryBreadcrumb().stream()
                .map(slug::slugify)
                .collect(Collectors.toList());

        return String.join("_", categoriesAsParams);
    }

}
