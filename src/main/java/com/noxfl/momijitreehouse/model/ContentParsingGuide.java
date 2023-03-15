package com.noxfl.momijitreehouse.model;

import com.noxfl.momijitreehouse.model.Field;
import com.noxfl.momijitreehouse.crawler.ContentType;
import lombok.*;

import java.util.List;

/**
 * @author Fernando Nathanael
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ContentParsingGuide {

    private String source;
    private ContentType contentType;
    private List<Field> fields;

}
