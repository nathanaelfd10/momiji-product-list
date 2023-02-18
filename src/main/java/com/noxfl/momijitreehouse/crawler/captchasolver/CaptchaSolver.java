/**
 * 
 */
package com.noxfl.momijitreehouse.crawler.captchasolver;

import com.microsoft.playwright.Page;

/**
 * @author Fernando Nathanael
 *
 */
public interface CaptchaSolver {

	public void solve(Page page);
	
}
