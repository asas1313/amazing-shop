package com.amazing.shop.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({
        ElementType.TYPE,
        ElementType.ANNOTATION_TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = com.amazing.shop.constraint.FieldMatchValidator.class)
@Documented
public @interface FieldMatch {
    String message() default "{constraints.field-match}";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};
    String first();
    String second();

    @Target({
            ElementType.TYPE,
            ElementType.ANNOTATION_TYPE
    })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List{
        com.amazing.shop.constraint.FieldMatch[] value();
    }
}
