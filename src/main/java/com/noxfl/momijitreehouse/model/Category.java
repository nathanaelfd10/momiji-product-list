/**
 * 
 */
package com.noxfl.momijitreehouse.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fernando Nathanael
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category {

	private String id;
	private String url;
	private List<String> categoryBreadcrumb;

}
