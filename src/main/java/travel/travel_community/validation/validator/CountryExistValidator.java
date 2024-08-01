package travel.travel_community.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import travel.travel_community.apiPayload.code.status.ErrorStatus;
import travel.travel_community.service.TravelPostCategoryService;
import travel.travel_community.validation.annotation.ExistCountry;

@Component
@RequiredArgsConstructor
public class CountryExistValidator implements ConstraintValidator<ExistCountry, Long> {
    private final TravelPostCategoryService travelPostCategoryService;

    @Override
    public void initialize(ExistCountry constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if(CommonValidator.isNullId(value, context)){
            return false;
        }
        boolean isValid = travelPostCategoryService.isValidCountry(value);
        if(!isValid){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    ErrorStatus.COUNTRY_NOT_FOUND.toString()
            ).addConstraintViolation();
        }
        return isValid;
    }
}
