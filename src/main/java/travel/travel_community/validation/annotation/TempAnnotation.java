package travel.travel_community.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import travel.travel_community.validation.validator.TempValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TempValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TempAnnotation {
    String message() default "임시 예외처리 어노테이션입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}