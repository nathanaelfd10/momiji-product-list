/**
 * 
 */
package com.noxfl.momijitreehouse.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Fernando Nathanael
 *
 */

@AllArgsConstructor
@Getter
@Setter
public class Category {

	private String id;
	private String siteUrl;
	private List<String> categoryBreadcrumb;

}
