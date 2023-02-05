package com.github.polyrocketmatt.sierra.lib.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Reference tag to refer to a certain paper, author or other source.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.ANNOTATION_TYPE,
        ElementType.TYPE,
        ElementType.FIELD,
        ElementType.METHOD,
        ElementType.CONSTRUCTOR,
        ElementType.LOCAL_VARIABLE
})
public @interface Reference {

    String reference();

}
