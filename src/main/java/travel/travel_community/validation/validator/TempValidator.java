package travel.travel_community.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import travel.travel_community.validation.annotation.TempAnnotation;

@Component
@RequiredArgsConstructor
public class TempValidator implements ConstraintValidator<TempAnnotation, Long> {
    @Override
    public void initialize(TempAnnotation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
