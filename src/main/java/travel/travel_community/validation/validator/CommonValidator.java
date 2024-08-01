package travel.travel_community.validation.validator;

import jakarta.validation.ConstraintValidatorContext;

public class CommonValidator {

    public static boolean isNullId(Long value, ConstraintValidatorContext context){
        if(value == null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "널이어서는 안됩니다."
            ).addConstraintViolation();
            return true;
        }
        if(value <= 0){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "1 이상의 값이어야 합니다."
            ).addConstraintViolation();
            return true;
        }
        return false;
    }

    public static boolean isNullId(String  value, ConstraintValidatorContext context){
        if(value == null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "널이어서는 안됩니다."
            ).addConstraintViolation();
            return true;
        }
        return false;
    }
}
