package travel.travel_community.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import travel.travel_community.apiPayload.code.status.ErrorStatus;
import travel.travel_community.service.TravelPostCategoryService;
import travel.travel_community.service.UserService;
import travel.travel_community.validation.annotation.ExistCountry;
import travel.travel_community.validation.annotation.ExistUser;

@Component
@RequiredArgsConstructor
public class UserExistValidator implements ConstraintValidator<ExistUser, String> {
    private final UserService userService;

    @Override
    public void initialize(ExistUser constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String userid, ConstraintValidatorContext context) {
        if(CommonValidator.isNullId(userid, context)){
            return false;
        }
        boolean isValid = userService.isValidUser(userid);
        if(!isValid){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    ErrorStatus.USER_NOT_FOUND.toString()
            ).addConstraintViolation();
        }
        return isValid;
    }
}
