/**
 * 
 */
package com.noxfl.momijitreehouse.crawler;

import com.noxfl.momijitreehouse.model.Job;

/**
 * @author Fernando Nathanael
 *
 */
public interface JobHandler {

	public SiteCrawler getFetcher(Job job);

}