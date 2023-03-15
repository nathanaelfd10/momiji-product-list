package com.noxfl.momijitreehouse.model;

import com.noxfl.momijitreehouse.crawler.ContentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fernando Nathanael
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Content {

    private String name;
    private ContentType contentType;
    private String content;

}
