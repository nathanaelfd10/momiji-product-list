/**
 * 
 */
package com.noxfl.momijitreehouse.model.schema.message;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Fernando Nathanael
 *
 */
public enum ContentType {
	@JsonProperty("JSON")
	JSON,
	@JsonProperty("HTML")
	HTML
}
