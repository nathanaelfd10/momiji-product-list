/**
 * 
 */
package com.noxfl.momijitreehouse.crawler.connection;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONObject;

/**
 * @author Fernando Nathanael
 *
 */
public interface ApiFetcher {

	/**
	 * 
	 * Send a GET request to an end point, then returns its response in the form of
	 * JSONObject.
	 * 
	 * @param headers
	 * @param targetUrl
	 * @return
	 * @throws IOException
	 */
	public JSONObject fetchGet(HashMap<String, String> headers, String targetUrl) throws IOException;

	/**
	 * Send a POST request to an end point, then returns its response in the form of
	 * JSONObject.
	 * 
	 * @param body
	 * @param headers
	 * @param targetUrl
	 * @return
	 * @throws IOException
	 */
	public JSONObject fetchPost(String body, HashMap<String, String> headers, String targetUrl) throws IOException;

}