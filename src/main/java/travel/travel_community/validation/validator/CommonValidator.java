package travel.travel_community.validation.validator;

import jakarta.validation.ConstraintValidatorContext;

public class CommonValidator {

    public static boolean isNullId(Long value, ConstraintValidatorContext context){
        if(value == null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "널이어서는 안됩니다"
            ).addConstraintViolation();
            return true;
        }
        return false;
    }
}
