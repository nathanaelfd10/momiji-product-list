/**
 * 
 */
package com.noxfl.momijitreehouse.model;

/**
 * @author Fernando Nathanael
 *
 */
public class TokopediaProduct extends Product {

	/**
	 * @param hashUrl
	 * @param title
	 * @param price
	 * @param priceDiscount
	 * @param soldCount
	 * @param category
	 */
	public TokopediaProduct(String hashUrl, String title, int price, int priceDiscount, int soldCount,
			Category category) {
		super(hashUrl, title, price, priceDiscount, soldCount, category);
	}

}