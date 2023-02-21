package com.noxfl.momijitreehouse.crawler.impl;

import java.util.List;

import com.noxfl.momijitreehouse.crawler.connection.ApiFetcher;
import com.noxfl.momijitreehouse.model.Category;
import com.noxfl.momijitreehouse.model.Site;
import com.noxfl.momijitreehouse.model.TokopediaProduct;

public class TokopediaCategorySiteCrawler extends TokopediaSiteCrawler {

	public TokopediaCategorySiteCrawler(ApiFetcher apiFetcher) {
		super(apiFetcher);
		// TODO Auto-generated constructor stub
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
