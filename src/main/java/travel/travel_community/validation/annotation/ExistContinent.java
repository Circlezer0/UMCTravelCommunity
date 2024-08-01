package travel.travel_community.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import travel.travel_community.validation.validator.CountryExistValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CountryExistValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistContinent {
    String message() default "해당하는 지역이 존재하지 않습니다";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
